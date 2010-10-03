package org.fanhongtao.net.frame.aio;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.fanhongtao.log.RunLogger;
import org.fanhongtao.net.frame.MessageHandler;
import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.net.frame.NetUtils;
import org.fanhongtao.net.frame.Request;
import org.fanhongtao.thread.ExRunnable;


/**
 * 实现读取Socket的线程<br>
 * 这里采用静态队列来对读线程进行调度：当有Socket事件时，将对应的Socket放入pool中，再由各个线程去竞争。
 * 
 * @author Dharma
 * @created 2009-5-2
 */
public class ChannelReader extends ExRunnable
{
    private static BlockingQueue<SelectionKey> pool = new LinkedBlockingQueue<SelectionKey>();

    @Override
    public void run()
    {
        while (!isStoped())
        {
            try
            {
                // 线程从队列中获取任务并执行
                SelectionKey key = pool.take();
                readMessage(key);
            }
            catch (Exception e)
            {
                RunLogger.warn("Failed to read. ", e);
            }
        }
    }

    /**
     * 处理连接数据读取
     * @param key SelectionKey
     * @throws IOException 
     */
    private void readMessage(SelectionKey key)
    {
        // 读取客户端数据
        SocketChannel sc = (SocketChannel) key.channel();

        byte[] clientData = null;
        try
        {
            clientData = readRequest(sc);
        }
        catch (IOException e)
        {
            NetUtils.closeKey(key);
            RunLogger.info("Catch IOException while reading");
            return;
        }
        finally
        {
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
        }

        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setMsg(clientData);
        Socket socket = sc.socket();
        msgInfo.setSrcIP(socket.getRemoteSocketAddress().toString());
        msgInfo.setSrcPort(socket.getPort());
        msgInfo.setDestIP(socket.getLocalSocketAddress().toString());
        msgInfo.setDestPort(socket.getLocalPort());

        RunLogger.debug("Read message: " + msgInfo);

        Request request = new Request(key, msgInfo);

        MessageHandler.process(request);// 提交给处理线程进行处理
    }

    /**
     * 读取客户端发出请求数据
     * @param sc 套接通道
     */
    private static int BUFFER_SIZE = 1024;

    private byte[] readRequest(SocketChannel sc) throws IOException
    {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int off = 0;
        int r = 0;
        byte[] data = new byte[BUFFER_SIZE * 10];

        while (true)
        {
            buffer.clear();
            r = sc.read(buffer);
            if (r == 0)
                break;
            if ((off + r) > data.length)
            {
                data = grow(data, BUFFER_SIZE * 10);
            }
            byte[] buf = buffer.array();
            System.arraycopy(buf, 0, data, off, r);
            off += r;
        }
        byte[] req = new byte[off];
        System.arraycopy(data, 0, req, 0, off);
        return req;
    }

    /**
     * 数组扩容
     * @param src byte[] 源数组数据
     * @param size int 扩容的增加量
     * @return byte[] 扩容后的数组
     */
    private byte[] grow(byte[] src, int size)
    {
        byte[] tmp = new byte[src.length + size];
        System.arraycopy(src, 0, tmp, 0, src.length);
        return tmp;
    }

    /**
     * 处理客户请求,管理用户的联结池,并唤醒队列中的线程进行处理
     */
    public static void processRequest(SelectionKey key)
    {
        synchronized (pool)
        {
            key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));

            try
            {
                pool.put(key);
            }
            catch (InterruptedException e)
            {
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                RunLogger.warn("Failed to put key into pool.", e);
            }
        }
    }

}
