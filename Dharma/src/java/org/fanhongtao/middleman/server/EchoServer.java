package org.fanhongtao.middleman.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.fanhongtao.middleman.core.IMessageWindow;


/**
 * 将接收到的消息原样返回给客户端
 *  
 * @author Dharma
 * @created 2008-11-26
 */
public class EchoServer extends MuteServer
{
    private static Logger logger = Logger.getLogger(EchoServer.class);

    public EchoServer(int port, IMessageWindow logWindow)
    {
        super(port, logWindow);
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

            // 将 readBuffer 内容拷入 writeBuffer
            writeBuffer.clear();
            writeBuffer.put(readBuffer);
            writeBuffer.flip();

            // 记录下发送给客户端的消息
            logWritedMessage(clientChannel);

            // 将数据返回给客户端
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
        EchoServer server = new EchoServer(port, null);
        if (server.bind())
        {
            new Thread(server).start();
        }

    }

}
