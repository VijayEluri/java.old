package org.fanhongtao.net.frame;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

import org.fanhongtao.net.frame.handler.IHandler;


/**
 * @author Dharma
 * @created 2009-5-2
 */
public class Processer
{
    private static HashMap<SelectionKey, IHandler> handlerMap = new HashMap<SelectionKey, IHandler>();

    public static void registerHandler(SelectionKey key, IHandler handler)
    {
        synchronized (handlerMap)
        {
            handlerMap.put(key, handler);
        }
    }

    public static void process(Request req)
    {
        SelectionKey key = req.getKey();
        IHandler handler = handlerMap.get(key);
        if (handler != null)
        {
            handler.onMessage(req);
        }
    }
}
