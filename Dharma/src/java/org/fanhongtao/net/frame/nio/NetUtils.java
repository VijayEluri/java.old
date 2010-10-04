package org.fanhongtao.net.frame.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import org.fanhongtao.log.RunLogger;

/**
 * @author Dharma
 * @created 2009-5-2
 */
public class NetUtils
{
    /**
     * 关闭 key 对应的连接
     * @param key
     */
    public static void closeKey(SelectionKey key)
    {
        try
        {
            Connection conn = (Connection) key.attachment();
            RunLogger.info("Disconnect a client, " + conn.getRemoteAddress());
            key.cancel();
            key.channel().close();
        }
        catch (IOException e)
        {
            RunLogger.error("Failed to cancel key: " + key, e);
        }
    }
}
