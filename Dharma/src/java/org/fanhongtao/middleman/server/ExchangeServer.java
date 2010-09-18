package org.fanhongtao.middleman.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.fanhongtao.middleman.core.IMessageWindow;


public class ExchangeServer extends MuteServer
{
    private static Logger logger = Logger.getLogger(ExchangeServer.class);

    private String destIP;

    private int destPort;

    private HashMap<SocketChannel, SocketChannel> exchangeMap = new HashMap<SocketChannel, SocketChannel>();

    public ExchangeServer(int port, IMessageWindow logWindow)
    {
        super(port, logWindow);
        destIP = "localhost";
        destPort = 8080;
    }

    @Override
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

            // �� readBuffer ���ݿ��� writeBuffer
            writeBuffer.clear();
            writeBuffer.put(readBuffer);
            writeBuffer.flip();

            // ��¼�·��͸��ͻ��˵���Ϣ
            logWritedMessage(clientChannel);

            // �����ݷ��ظ��ͻ���
            while (writeBuffer.hasRemaining())
            {
                clientChannel.write(writeBuffer);
            }
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

}
