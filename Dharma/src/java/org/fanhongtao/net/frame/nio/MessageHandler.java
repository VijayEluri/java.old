package org.fanhongtao.net.frame.nio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.fanhongtao.log.RunLogger;
import org.fanhongtao.net.frame.handler.IHandler;
import org.fanhongtao.thread.ExRunnable;

/**
 * @author Dharma
 * @created 2009-5-2
 */
public class MessageHandler extends ExRunnable
{
    private static BlockingQueue<Connection> connList = new LinkedBlockingQueue<Connection>();
    
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Connection connection = connList.take();
                IHandler handler = connection.getHandler();
                handler.onMessage(connection);
            }
            catch (Exception e)
            {
                RunLogger.warn("Unexpected exception while handle request.", e);
            }
        }
    }
    
    public static void process(Connection connectionInfo)
    {
        try
        {
            connList.put(connectionInfo);
        }
        catch (InterruptedException e)
        {
            RunLogger.warn("Failed to put request into handler queue.", e);
        }
    }
}
