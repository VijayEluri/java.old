package org.fanhongtao.net.frame.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.RunLogger;
import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.thread.ExRunnable;

/**
 * @author Dharma
 * @created 2009-5-2
 */
public class ChannelWriter extends ExRunnable
{
    private static BlockingQueue<Request> pool = new LinkedBlockingQueue<Request>();
    
    @Override
    public void run()
    {
        while (true)
        {
            Request req = null;
            try
            {
                req = pool.take();
                if (req == null)
                {
                    continue;
                }
                writeMessage(req);
            }
            catch (Exception e)
            {
                RunLogger.warn("Failed to write. ", e);
            }
        }
    }
    
    private void writeMessage(Request req)
    {
        // SelectionKey key = req.getKey();
        Connection connection = req.getConnection();
        SelectionKey key = connection.getKey();
        SocketChannel sc = (SocketChannel)key.channel();
        MsgInfo msgInfo = req.getMsgInfo();
        byte[] data = msgInfo.getMsg();
        RunLogger.debug("Write to " + connection.getRemoteAddress() + ", \n" + StringUtils.toHexString(data));
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data, 0, data.length);
        buffer.flip();
        try
        {
            sc.write(buffer);
        }
        catch (IOException e)
        {
            RunLogger.warn("Failed to write. ", e);
            NetUtils.closeKey(key);
        }
    }
    
    /**
     * 处理客户请求,管理用户的连接池,并唤醒队列中的线程进行处理
     */
    public static void send(Request req)
    {
        synchronized (pool)
        {
            pool.add(req);
            // pool.notifyAll();
        }
    }
    
}
