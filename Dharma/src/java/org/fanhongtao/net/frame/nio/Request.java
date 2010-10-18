package org.fanhongtao.net.frame.nio;

import org.fanhongtao.net.frame.MsgInfo;

/**
 * 
 * @author Dharma
 * @created 2009-5-2
 */
public class Request
{
    private Connection connection;
    
    private MsgInfo msg;
    
    public Request(Connection connection, MsgInfo msg)
    {
        this.connection = connection;
        this.msg = msg;
    }
    
    public Connection getConnection()
    {
        return connection;
    }
    
    public MsgInfo getMsgInfo()
    {
        return msg;
    }
}
