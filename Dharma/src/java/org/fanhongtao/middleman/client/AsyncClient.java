package org.fanhongtao.middleman.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.fanhongtao.middleman.core.AsyncObject;


/**
 * 实现异步的客户端
 * @author Dharma
 * @created 2008-11-26
 */
class AsyncClient extends AsyncObject implements Runnable
{
    private static Logger logger = Logger.getLogger(AsyncClient.class);

    /**
     * 与服务器之间的链接
     */
    private SocketChannel clientChannel;

    /**
     * 服务器IP
     */
    private String host;

    /**
     * 服务器端口
     */
    private int port;

    /**
     * 待发送消息的队列
     */
    private ArrayList<byte[]> sendList = new ArrayList<byte[]>();

    public AsyncClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void run()
    {
        logger.info("Try to connect to " + host + " : " + port);
        try
        {
            // 生成一个socketchannel, 并连接到 Server
            InetSocketAddress addr = new InetSocketAddress(host, port);
            clientChannel = SocketChannel.open(addr);
            logger.info("connection has been established, local port is " + clientChannel.socket().getLocalPort());
            clientChannel.configureBlocking(false); // 设置为非阻塞方式

            Selector selector = Selector.open(); // 生成一个信号监视器
            clientChannel.register(selector, SelectionKey.OP_READ);

            while (!isQuit())
            {
                if (selector.select(1000) != 0)
                {
                    logger.trace("event occur");
                    processIOEvent(selector);
                }
                else
                {
                    logger.trace("time out");
                }
                sendMessage();
            }

            logger.info("Disconnect with " + host + ":" + port);
            clientChannel.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 将发送队列中的消息发送出去
     * @throws IOException 
     */
    private void sendMessage() throws IOException
    {
        synchronized (sendList)
        {
            if (sendList.size() > 0)
            {
                // 每次只发送一个消息，以防止产生的消息太多
                byte[] msg = sendList.remove(0);
                writeBuffer.clear();
                writeBuffer.put(msg);
                writeBuffer.flip();
                logWritedMessage(clientChannel);

                // 发送消息
                while (writeBuffer.hasRemaining())
                {
                    clientChannel.write(writeBuffer);
                }
                writeBuffer.clear();
            }
        }

    }

    public void sendMessage(byte[] bytes)
    {
        synchronized (sendList)
        {
            sendList.add(bytes);
        }
    }

    /**
     * 处理IO事件
     * 
     * @param selector
     * @throws IOException
     */
    private void processIOEvent(Selector selector) throws IOException
    {
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext())
        {
            SelectionKey key = iterator.next();
            if (key.isReadable())
            {
                logger.trace("Receive date from server");
                receiveFromServer();
            }
            iterator.remove();
        }
    }

    /**
     * 读取server端发回的数据，并显示
     * 
     * @throws IOException
     */
    public void receiveFromServer() throws IOException
    {
        readBuffer.clear();
        int count = clientChannel.read(readBuffer);
        readBuffer.flip();

        logReceivedMessage(clientChannel);

        byte[] temp = new byte[readBuffer.limit()];
        readBuffer.get(temp);
        logger.info("reply is " + count + " bytes long, and content is: " + new String(temp));
    }

    public static void main(String args[])
    {
        String host = "127.0.0.1";
        int port = 3456;
        BasicConfigurator.configure();

        if (args.length == 1)
        {
            host = args[0];
        }
        else if (args.length > 1)
        {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        new AsyncClient(host, port).run();
    }
}
