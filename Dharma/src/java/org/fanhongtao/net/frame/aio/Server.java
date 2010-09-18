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
    /** ����˿� */
    private int port = 0;

    /** ��ӦSocket�¼����������ӡ����յ���Ϣ���Ͽ����ӣ��Ĵ����� */
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

        // ����һ��������
        selector = Selector.open(); // ����һ���źż�����

        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false); // ����������Ϊ�첽��ʽ

        // �����˰󶨵�һ���˿�
        serverChannel.socket().bind(new InetSocketAddress(port));

        // ������������ѡ���첽�ź�OP_ACCEPT, �������ܹ��µ�����
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
     * ���������̡߳�
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
                    // û��ָ����I/O�¼�����
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
     * ����IO�¼�
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
            if (key.isReadable()) // ĳsocket�����ݿ��Զ�ȡ
            {
                // logger.debug("Receive date from client: " + key);
                ChannelReader.processRequest(key);
                // ChannelReader.read(key);
            }
            iterator.remove();
        }
    }

    /**
     * ���һ���ͻ��˵�����
     * @param client
     * @throws IOException 
     */
    private void onAccept(SelectionKey key) throws IOException
    {
        // �������źŴ���
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        // ����һ���µ�����
        SocketChannel client = server.accept();
        client.configureBlocking(false);

        // ���ø�socket���첽�ź�OP_READ
        // ��socket��������ʱ���ᴥ�� processIOEvent�������� key.isReadable() ����
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
