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
     * ���������ӵ��¼�
     * @param clientKey
     */
    public void onAccept(SelectionKey clientKey);

    /**
     * ������Ϣ
     * @param req ������Ϣ
     */
    public void onMessage(Request req);

    /**
     * �������ӶϿ����¼�
     * @param clientKey
     */
    public void onClose(SelectionKey clientKey);
}
