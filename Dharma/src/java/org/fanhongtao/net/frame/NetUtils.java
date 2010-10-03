package org.fanhongtao.net.frame;

import java.io.IOException;
import java.nio.channels.SelectionKey;

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
            key.cancel();
            key.channel().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
