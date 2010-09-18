package org.fanhongtao.net.frame;

import java.nio.channels.SelectionKey;

/**
 * 
 * @author Dharma
 * @created 2009-5-2
 */
public class Request
{
    private SelectionKey key;

    private MsgInfo msg;

    public Request(SelectionKey key, MsgInfo msg)
    {
        this.key = key;
        this.msg = msg;
    }

    public SelectionKey getKey()
    {
        return key;
    }

    public MsgInfo getMsgInfo()
    {
        return msg;
    }
}
