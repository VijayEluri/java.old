package org.fanhongtao.net.server;

import java.io.IOException;

import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.LogUtils;
import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.net.frame.handler.HandlerAdapter;
import org.fanhongtao.net.frame.handler.IHandler;
import org.fanhongtao.net.frame.nio.ChannelWriter;
import org.fanhongtao.net.frame.nio.Connection;
import org.fanhongtao.net.frame.nio.NetUtils;
import org.fanhongtao.net.frame.nio.Request;
import org.fanhongtao.net.frame.nio.Server;

public class RouterServer extends Server
{
    
    public RouterServer(int port, IHandler handler)
    {
        super(port, handler);
    }
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args)
        throws IOException
    {
        LogUtils.initBasicLog();
        RouterServer server = new RouterServer(3456, new RouterHandler());
        server.init();
        server.run();
    }
}

class RouterHandler extends HandlerAdapter
{
    
    @Override
    public void onMessage(Connection connection)
    {
        byte[] data = connection.getBuffer().getData();
        String str = StringUtils.toHexString(data);
        System.out.println(str);
        
        if ((data.length == 1) && (data[0] == 0x03))
        {
            NetUtils.closeKey(connection.getKey());
            return;
        }
        
        MsgInfo retMsg = MsgInfo.getResponseMsg(connection.getSendDirection());
        retMsg.setMsg(data);
        Request res = new Request(connection, retMsg);
        
        ChannelWriter.send(res);
    }
}
