package org.fanhongtao.net.frame.nio;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.fanhongtao.net.frame.Data;
import org.fanhongtao.net.frame.MsgDirection;
import org.fanhongtao.net.frame.handler.IHandler;

/**
 * @author Fan Hongtao
 * @created 2010-10-4
 */
public class Connection
{
    private SelectionKey key;
    
    private InetSocketAddress localAddress;
    
    private InetSocketAddress remoteAddress;
    
    private Data buffer;
    
    private IHandler handler;
    
    private MsgDirection receiveDirection;
    
    private MsgDirection sendDirection;
    
    public Connection(SelectionKey key, IHandler handler)
    {
        this.key = key;
        this.handler = handler;
        
        SocketChannel sc = (SocketChannel) key.channel();
        Socket socket = sc.socket();
        this.localAddress = (InetSocketAddress) socket.getLocalSocketAddress();
        this.remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
        
        buffer = new Data();
    }
    
    public SelectionKey getKey()
    {
        return key;
    }
    
    public void setKey(SelectionKey key)
    {
        this.key = key;
    }
    
    public InetSocketAddress getLocalAddress()
    {
        return localAddress;
    }
    
    public InetSocketAddress getRemoteAddress()
    {
        return remoteAddress;
    }
    
    public Data getBuffer()
    {
        return buffer;
    }
    
    public IHandler getHandler()
    {
        return handler;
    }
    
    public MsgDirection getReceiveDirection()
    {
        return receiveDirection;
    }
    
    public MsgDirection getSendDirection()
    {
        return sendDirection;
    }
}
