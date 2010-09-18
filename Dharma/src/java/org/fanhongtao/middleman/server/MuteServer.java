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
 * ֻ������Ϣ�������ͻ��˻ظ��κ���Ϣ��
 * 
 * @author Dharma
 * @created 2008-10-29
 */
public class MuteServer extends AsyncObject implements Runnable
{
    private static Logger logger = Logger.getLogger(MuteServer.class);

    /** ����˿� */
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

        // ����һ��������
        try
        {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false); // ����������Ϊ�첽��ʽ
            // �����˰󶨵�һ���˿�
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
            selector = Selector.open(); // ����һ���źż�����

            // ������������ѡ���첽�ź�OP_ACCEPT, �������ܹ��µ�����
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
                    // û��ָ����I/O�¼�����
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
                // �������źŴ���
                ServerSocketChannel server = (ServerSocketChannel) key.channel();

                // ����һ���µ�����
                SocketChannel client = server.accept();
                client.configureBlocking(false);

                // ���ø�socket���첽�ź�OP_READ
                // ��socket��������ʱ���ᴥ�� processIOEvent
                // ͨ�� key.isReadable() �жϣ����յ��� receiveDataFromClient()
                client.register(selector, SelectionKey.OP_READ);
                onAccept(client);
                logger.info("Accept a client , port is " + client.socket().getPort());
            }
            if (key.isReadable())
            {
                logger.debug("Receive date from client");
                // ĳsocket�ɶ��ź�
                receiveDataFromClient(key);
            }
            iterator.remove();
        }
    }

    /**
     * ���һ���ͻ��˵�����
     * @param client
     */
    protected void onAccept(SocketChannel client)
    {
    }

    /**
     * �������Կͻ��˵���Ϣ
     * 
     * @param key �����Ӧ�Ŀͻ���
     * 
     */
    public void receiveDataFromClient(SelectionKey key)
    {
        // ��key��ȡָ��socketchannel������
        SocketChannel clientChannel = (SocketChannel) key.channel();
        readBuffer.clear();

        try
        {
            // ��ȡ���ݵ�readBuffer
            while (clientChannel.read(readBuffer) > 0)
            {
                ; // do nothing
            }
            // ȷ�� readBuffer �ɶ�
            readBuffer.flip();

            // �����ֽ�Ϊ0, ����Ϊ�ǿͻ����Ѿ�����
            if (readBuffer.limit() == 0)
            {
                throw new IOException("Read 0 bytes from socket.");
            }

            // �����յ�����Ϣ��¼����
            logReceivedMessage(clientChannel);

            /* 
             * // �� readBuffer ���ݿ��� writeBuffer
             
            writeBuffer.clear();
            writeBuffer.put(readBuffer);
            writeBuffer.flip();

            // �����ݷ��ظ��ͻ���
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
        BasicConfigurator.configure(); // ʹ��ȱʡ����

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
