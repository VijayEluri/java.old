package org.fanhongtao.net.frame.handler;

import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.net.frame.NetUtils;
import org.fanhongtao.net.frame.Request;
import org.fanhongtao.net.frame.aio.ChannelWriter;


/**
 * ʵ�ֽ���Ϣԭ�����ظ��ͻ���
 * @author Dharma
 * @created 2009-5-2
 */
public class EchoHandler extends HandlerAdapter
{

    @Override
    public void onMessage(Request req)
    {
        MsgInfo msg = req.getMsgInfo();
        byte[] data = msg.getMsg();
        if ((data.length == 1) && (data[0] == 0x03)) // Ctrl-C �ж�����
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
