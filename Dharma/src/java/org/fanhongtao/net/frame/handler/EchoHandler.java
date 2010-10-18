package org.fanhongtao.net.frame.handler;

import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.net.frame.nio.ChannelWriter;
import org.fanhongtao.net.frame.nio.Connection;
import org.fanhongtao.net.frame.nio.NetUtils;
import org.fanhongtao.net.frame.nio.Request;

/**
 * 实现将消息原样返回给客户端
 * @author Dharma
 * @created 2009-5-2
 */
public class EchoHandler extends HandlerAdapter
{
    
    @Override
    public void onMessage(Connection connection)
    {
        byte[] data = connection.getBuffer().getData();
        if ((data.length == 1) && (data[0] == 0x03)) // Ctrl-C 中断连接
        {
            NetUtils.closeKey(connection.getKey());
            return;
        }
        String str = new String(data);
        if (str.startsWith("quit"))
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
