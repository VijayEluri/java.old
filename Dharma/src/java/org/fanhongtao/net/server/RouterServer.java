package org.fanhongtao.net.server;

import java.io.IOException;

import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.LogUtils;
import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.net.frame.NetUtils;
import org.fanhongtao.net.frame.Request;
import org.fanhongtao.net.frame.aio.ChannelWriter;
import org.fanhongtao.net.frame.aio.Server;
import org.fanhongtao.net.frame.handler.HandlerAdapter;
import org.fanhongtao.net.frame.handler.IHandler;


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
    public static void main(String[] args) throws IOException
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
    public void onMessage(Request req)
    {
        MsgInfo msg = req.getMsgInfo();
        byte[] data = msg.getMsg();
        String str = StringUtils.toHexString(data);
        System.out.println(str);

        if ((data.length == 1) && (data[0] == 0x03))
        {
            NetUtils.closeKey(req.getKey());
            return;
        }

        MsgInfo retMsg = MsgInfo.getResponseMsg(msg);
        retMsg.setMsg(msg.getMsg());
        Request res = new Request(req.getKey(), retMsg);

        ChannelWriter.send(res);
    }
}
