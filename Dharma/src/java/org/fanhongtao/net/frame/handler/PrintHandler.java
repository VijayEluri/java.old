package org.fanhongtao.net.frame.handler;

import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.RunLogger;
import org.fanhongtao.net.frame.nio.Connection;

/**
 * 将接收到的消息记录到日志
 * @author Dharma
 * @created 2009-5-2
 */
public class PrintHandler extends HandlerAdapter
{
    @Override
    public void onMessage(Connection connection)
    {
        byte[] msg = connection.getBuffer().getData();
        String str = StringUtils.toHexString(msg);
        RunLogger.info(str);
    }
}
