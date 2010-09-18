package org.fanhongtao.net.frame.handler;

import java.nio.channels.SelectionKey;

import org.fanhongtao.net.frame.Request;


/**
 * @author Dharma
 * @created 2009-5-9
 */
public class HandlerAdapter implements IHandler
{

    @Override
    public void onAccept(SelectionKey clientKey)
    {
    }

    @Override
    public void onMessage(Request req)
    {
    }

    @Override
    public void onClose(SelectionKey clientKey)
    {
    }
}
