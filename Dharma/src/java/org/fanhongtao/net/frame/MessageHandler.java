package org.fanhongtao.net.frame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.fanhongtao.log.RunLogger;
import org.fanhongtao.thread.ExRunnable;


/**
 * @author Dharma
 * @created 2009-5-2
 */
public class MessageHandler extends ExRunnable
{
    private static BlockingQueue<Request> reqList = new LinkedBlockingQueue<Request>();

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Request req = reqList.take();
                Processer.process(req);
            }
            catch (Exception e)
            {
                RunLogger.warn("Unexpected exception while handle request.", e);
            }
        }
    }

    public static void process(Request req)
    {
        try
        {
            reqList.put(req);
        }
        catch (InterruptedException e)
        {
            RunLogger.warn("Failed to put request into handler queue.", e);
        }
    }
}
