package org.fanhongtao.middleman.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.fanhongtao.middleman.core.AsyncObject;
import org.fanhongtao.middleman.core.IMessageWindow;


/**
 * 只接收消息，不给客户端回复任何消息。
 * 
 * @author Dharma
 * @created 2008-10-29
 */
public class MuteServer extends AsyncObject implements Runnable
{
    private static Logger logger = Logger.getLogger(MuteServer.class);

    /** 服务端口 */
    private int port = 0;

    private ServerSocketChannel serverChannel = null;

    Selector selector = null;

    public MuteServer(int port, IMessageWindow logWindow)
    {
        this.port = port;
        this.logWindow = logWindow;
    }

    public boolean bind()
    {
        logger.info("Try to start server with port " + port);

        // 生成一个侦听端
        try
        {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false); // 将侦听端设为异步方式
            // 侦听端绑定到一个端口
            serverChannel.socket().bind(new InetSocketAddress(port));

        }
        catch (IOException e)
        {
            e.printStackTrace();
            logger.warn("Failed to start server. " + e.getLocalizedMessage());
            return false;
        }
        logger.info("echo server has been set up ......");

        return true;
    }

    public void run()
    {
        logger.debug("Try to accept client...");
        try
        {
            selector = Selector.open(); // 生成一个信号监视器

            // 设置侦听端所选的异步信号OP_ACCEPT, 这样才能够新的连接
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            int count = 0;
            while (!isQuit())
            {
                if (selector.select(1000) != 0)
                {
                    logger.debug("event occur");
                    processIOEvent(selector);
                }
                else
                {
                    // 没有指定的I/O事件发生
                    count++;
                    if (count > 10)
                    {
                        logger.debug("time out.....");
                        count = 0;
                    }
                }
            }
            serverChannel.close();
            logger.info("echo server has been shutdown ......");
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
            if (key.isAcceptable())
            {
                // 侦听端信号触发
                ServerSocketChannel server = (ServerSocketChannel) key.channel();

                // 接受一个新的连接
                SocketChannel client = server.accept();
                client.configureBlocking(false);

                // 设置该socket的异步信号OP_READ
                // 当socket中有数据时，会触发 processIOEvent
                // 通过 key.isReadable() 判断，最终调用 receiveDataFromClient()
                client.register(selector, SelectionKey.OP_READ);
                onAccept(client);
                logger.info("Accept a client , port is " + client.socket().getPort());
            }
            if (key.isReadable())
            {
                logger.debug("Receive date from client");
                // 某socket可读信号
                receiveDataFromClient(key);
            }
            iterator.remove();
        }
    }

    /**
     * 获得一个客户端的链接
     * @param client
     */
    protected void onAccept(SocketChannel client)
    {
    }

    /**
     * 处理来自客户端的消息
     * 
     * @param key 代表对应的客户端
     * 
     */
    public void receiveDataFromClient(SelectionKey key)
    {
        // 由key获取指定socketchannel的引用
        SocketChannel clientChannel = (SocketChannel) key.channel();
        readBuffer.clear();

        try
        {
            // 读取数据到readBuffer
            while (clientChannel.read(readBuffer) > 0)
            {
                ; // do nothing
            }
            // 确保 readBuffer 可读
            readBuffer.flip();

            // 读入字节为0, 则认为是客户端已经断连
            if (readBuffer.limit() == 0)
            {
                throw new IOException("Read 0 bytes from socket.");
            }

            // 将接收到的消息记录下来
            logReceivedMessage(clientChannel);

            /* 
             * // 将 readBuffer 内容拷入 writeBuffer
             
            writeBuffer.clear();
            writeBuffer.put(readBuffer);
            writeBuffer.flip();

            // 将数据返回给客户端
            while (writeBuffer.hasRemaining())
            {
                clientChannel.write(writeBuffer);
            }
            */
        }
        catch (IOException e)
        {
            e.printStackTrace();

            logger.info("close a client , port is " + clientChannel.socket().getPort());
            key.cancel();
            try
            {
                key.channel().close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure(); // 使用缺省配置

        int port = 8088;
        if (args.length == 1)
        {
            port = Integer.parseInt(args[0]);
        }
        MuteServer server = new MuteServer(port, null);
        if (server.bind())
        {
            new Thread(server).start();
        }
    }
}
