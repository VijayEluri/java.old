package org.fanhongtao.net.frame.handler;

import java.nio.channels.SelectionKey;

import org.fanhongtao.net.frame.Request;


/**
 * @author Dharma
 * @created 2009-5-2
 */
public interface IHandler
{
    /**
     * 处理建立连接的事件
     * @param clientKey
     */
    public void onAccept(SelectionKey clientKey);

    /**
     * 处理消息
     * @param req 请求消息
     */
    public void onMessage(Request req);

    /**
     * 处理连接断开的事件
     * @param clientKey
     */
    public void onClose(SelectionKey clientKey);
}
