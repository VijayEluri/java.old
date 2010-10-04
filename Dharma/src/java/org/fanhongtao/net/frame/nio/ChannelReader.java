package org.fanhongtao.net.frame.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.fanhongtao.log.RunLogger;
import org.fanhongtao.net.frame.Data;
import org.fanhongtao.thread.ExRunnable;

/**
 * 实现读取Socket的线程，负责从 key 中读取数据<br>
 * 读取数据后，是否能够组成一个完整的消息，如果可以，则从<br>
 * 这里采用静态队列来对读线程进行调度：当有Socket事件时，将对应的Socket放入pool中，再由各个线程去竞争。
 * 
 * @author Dharma
 * @created 2009-5-2
 */
public class ChannelReader extends ExRunnable
{
    private static BlockingQueue<SelectionKey> pool = new LinkedBlockingQueue<SelectionKey>();
    
    private static int BUFFER_SIZE = 1024;
    
    @Override
    public void run()
    {
        while (!isStoped())
        {
            SelectionKey key = null;
            try
            {
                // 线程从队列中获取任务并执行
                key = pool.take();
                readMessage(key);
            }
            catch (Exception e)
            {
                RunLogger.warn("Failed to read from key [" + key + "]", e);
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
        Data data = new Data();
        boolean closeKey = false;
        try
        {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (true)
            {
                buffer.clear();
                int num = sc.read(buffer);
                if (num == 0)
                {
                    break;
                }
                else if (num == -1)
                {
                    closeKey = true;
                    break;
                }
                byte[] buf = buffer.array();
                data.add(buf, 0, num);
            }
        }
        catch (IOException e)
        {
            RunLogger.info("Catch IOException while reading", e);
            closeKey = true;
        }
        finally
        {
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            if (closeKey)
            {
                NetUtils.closeKey(key);
                return;
            }
        }
        
        Connection conn = (Connection) key.attachment();
        conn.getBuffer().add(data.getData());
        MessageHandler.process(conn);// 提交给处理线程进行处理
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
