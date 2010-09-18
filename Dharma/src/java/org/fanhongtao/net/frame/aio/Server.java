package org.fanhongtao.net.frame.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.fanhongtao.log.RunLogger;
import org.fanhongtao.net.frame.MessageHandler;
import org.fanhongtao.net.frame.Processer;
import org.fanhongtao.net.frame.handler.IHandler;


/**
 * @author Dharma
 * @created 2009-5-2
 */
public class Server
{
    /** 服务端口 */
    private int port = 0;

    /** 响应Socket事件（建立连接、接收到消息、断开连接）的处理器 */
    private IHandler handler = null;

    private ServerSocketChannel serverChannel = null;

    private Selector selector = null;

    private Timer timer;

    public Server(int port, IHandler handler)
    {
        this.port = port;
        this.handler = handler;
    }

    public void init() throws IOException
    {
        bind();
        initTimer();
        initThread();
        RunLogger.info("Server has been set up ......");
    }

    private void bind() throws IOException
    {
        RunLogger.info("Try to start server with port " + port);

        // 生成一个侦听端
        selector = Selector.open(); // 生成一个信号监视器

        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false); // 将侦听端设为异步方式

        // 侦听端绑定到一个端口
        serverChannel.socket().bind(new InetSocketAddress(port));

        // 设置侦听端所选的异步信号OP_ACCEPT, 这样才能够新的连接
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        return;
    }

    private void initTimer()
    {
        timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                // synchronized (connMap)
                // {
                // Set<Entry<SocketChannel, Connection>> set =
                // connMap.entrySet();
                // Iterator<Entry<SocketChannel, Connection>> iter =
                // set.iterator();
                // while (iter.hasNext())
                // {
                // Entry<SocketChannel, Connection> entry = iter.next();
                // Connection conn = entry.getValue();
                // if (!conn.isExist())
                // {
                // iter.remove();
                // }
                // }
                // }
            }

        };
        timer.schedule(task, 1000, 1000);
    }

    /**
     * 创建工作线程。
     */
    private void initThread()
    {
        MessageHandler handler = new MessageHandler();
        Thread thread = new Thread(handler, "handler");
        thread.start();

        for (int i = 1; i < 4; i++)
        {
            String name = "Reader-" + i;
            ChannelReader reader = new ChannelReader();
            reader.setName(name);
            thread = new Thread(reader, name);
            thread.start();

            name = "Writer-" + i;
            ChannelWriter writer = new ChannelWriter();
            writer.setName(name);
            thread = new Thread(writer, name);
            thread.start();
        }

    }

    public void run()
    {
        RunLogger.debug("Server is on services ...");
        try
        {
            int count = 0;
            while (true)
            {
                if (selector.select(1000) != 0)
                {
                    // logger.debug("event occur");
                    processIOEvent(selector);
                }
                else
                {
                    // 没有指定的I/O事件发生
                    count++;
                    if (count > 10)
                    {
                        // logger.debug("time out.....");
                        count = 0;
                    }
                }
            }
            // serverChannel.close();
            // logger.info("echo server has been shutdown ......");
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
                onAccept(key);
            }
            if (key.isReadable()) // 某socket有数据可以读取
            {
                // logger.debug("Receive date from client: " + key);
                ChannelReader.processRequest(key);
                // ChannelReader.read(key);
            }
            iterator.remove();
        }
    }

    /**
     * 获得一个客户端的链接
     * @param client
     * @throws IOException 
     */
    private void onAccept(SelectionKey key) throws IOException
    {
        // 侦听端信号触发
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        // 接受一个新的连接
        SocketChannel client = server.accept();
        client.configureBlocking(false);

        // 设置该socket的异步信号OP_READ
        // 当socket中有数据时，会触发 processIOEvent，并满足 key.isReadable() 条件
        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);

        RunLogger.info("Accept a client , port is " + client.socket().getPort());
        try
        {
            Processer.registerHandler(clientKey, handler);
            handler.onAccept(clientKey);
        }
        catch (Exception e)
        {
            RunLogger.warn("Failed to register handler");
        }
    }

}
