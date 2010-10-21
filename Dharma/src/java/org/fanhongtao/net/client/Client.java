package org.fanhongtao.net.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.LogUtils;

/**
 * @author Fan Hongtao
 * @created 2010-10-21
 */
public class Client
{
    private static Logger log = Logger.getLogger(Client.class.getName());
    
    public static final int MAX_LENGTH = 64 * 1024;
    
    private ByteBuffer readBuffer = ByteBuffer.allocate(MAX_LENGTH);
    
    private ByteBuffer writeBuffer = ByteBuffer.allocate(MAX_LENGTH);
    
    private SocketChannel channel;
    
    private Selector selector;
    
    private String server;
    
    private int port;
    
    private int msgInterval;
    
    private int exitInterval;
    
    public Client(String server, int port, int msgInterval, int exitInterval)
    {
        this.server = server;
        this.port = port;
        this.msgInterval = msgInterval;
        this.exitInterval = exitInterval;
    }
    
    public void connect()
        throws IOException
    {
        log.info("Try to connect to " + server + " : " + port);
        InetSocketAddress addr = new InetSocketAddress(server, port);
        channel = SocketChannel.open(addr);
        channel.configureBlocking(false);
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        log.info("Connect successfully. Local port: " + channel.socket().getLocalPort());
    }
    
    public void disconnect()
        throws IOException
    {
        log.info("disconnect to " + server + " : " + port);
        channel.close();
    }
    
    public void sendMessage(String msgFile)
        throws IOException
    {
        log.info("Process file [" + msgFile + "]");
        BufferedReader fin = new BufferedReader(new FileReader(msgFile));
        // send message(s) to server
        while (true)
        {
            if (selector.select(msgInterval) != 0)
            {
                processIOEvent(selector);
            }
            byte[] msg = getOneMsg(fin);
            if (msg.length == 0)
            {
                break;
            }
            
            log.debug("Send to server: \n" + StringUtils.toHexString(msg));
            writeBuffer.put(msg);
            writeBuffer.flip();
            while (writeBuffer.remaining() != 0)
            {
                channel.write(writeBuffer);
            }
            writeBuffer.clear();
        }
        
        // wait last response
        long currentTime;
        long exitTime = System.currentTimeMillis() + exitInterval;
        while ((currentTime = System.currentTimeMillis()) < exitTime)
        {
            if (selector.select(exitTime - currentTime) != 0)
            {
                processIOEvent(selector);
            }
        }
        
        log.info("Process file [" + msgFile + "] finished.");
    }
    
    private void processIOEvent(Selector selector)
        throws IOException
    {
        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
        while (iter.hasNext())
        {
            SelectionKey key = iter.next();
            if (key.isReadable())
            {
                receiveFromServer();
            }
            iter.remove();
        }
    }
    
    private void receiveFromServer()
        throws IOException
    {
        int count = channel.read(readBuffer);
        readBuffer.flip();
        byte[] temp = new byte[readBuffer.limit()];
        readBuffer.get(temp);
        log.debug("read " + count + " bytes from server, content\n" + StringUtils.toHexString(temp));
        readBuffer.clear();
    }
    
    @SuppressWarnings("deprecation")
    protected void log(String logInfo)
    {
        Date date = new Date();
        System.out.println(date.toLocaleString() + ", " + logInfo);
    }
    
    private byte[] getOneMsg(BufferedReader fin)
        throws IOException
    {
        // read a message from file
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = fin.readLine()) != null)
        {
            line.trim();
            if (line.startsWith("#MsgEnd"))
            {
                break;
            }
            if (line.startsWith("#"))
            {
                log.info(line.substring(1).trim());
                continue;
            }
            if (line.length() == 0)
            {
                continue;
            }
            buffer.append(line).append(' ');
        }
        
        // translate message into bytes
        String[] hexs = buffer.toString().split("[ \t]+");
        byte[] data = new byte[hexs.length];
        for (int i = 0; i < hexs.length; i++)
        {
            data[i] = (byte)Integer.parseInt(hexs[i], 16);
        }
        
        return data;
    }
    
    public static void main(String[] args)
    {
        if (args.length < 3)
        {
            System.out.println("Usage: Client ip port msgFile [msgInterval] [exitInterval]");
            return;
        }
        
        LogUtils.initBasicLog();
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String msgFile = args[2];
        int msgInterval = (args.length > 3) ? Integer.parseInt(args[3]) : 1000;
        int exitInterval = (args.length > 4) ? Integer.parseInt(args[4]) : 10000;
        Client client = new Client(ip, port, msgInterval, exitInterval);
        
        try
        {
            client.connect();
            client.sendMessage(msgFile);
            client.disconnect();
        }
        catch (IOException e)
        {
            log.error("Failed to process", e);
        }
    }
}
