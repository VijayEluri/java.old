package org.fanhongtao.net.frame.handler;

import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.RunLogger;
import org.fanhongtao.net.frame.Request;


/**
 * 将接收到的消息记录到日志
 * @author Dharma
 * @created 2009-5-2
 */
public class PrintHandler extends HandlerAdapter
{
    @Override
    public void onMessage(Request req)
    {
        String str = StringUtils.toHexString(req.getMsgInfo().getMsg());
        RunLogger.info(str);
    }
}
