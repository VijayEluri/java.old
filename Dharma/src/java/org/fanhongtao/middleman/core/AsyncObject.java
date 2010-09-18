package org.fanhongtao.middleman.core;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.eclipse.swt.widgets.Display;
import org.fanhongtao.net.frame.MsgInfo;


/**
 * 
 * @author Dharma
 * @created 2008-11-26
 */
public class AsyncObject
{
    // private static Logger logger = Logger.getLogger(AsyncObject.class);

    /**
     * 缓存大小
     */
    private final static int MAX_LENGTH = 64 * 1024;

    /**
     * 读消息的缓存
     */
    protected ByteBuffer readBuffer = ByteBuffer.allocate(MAX_LENGTH);

    /**
     * 写消息的缓存
     */
    protected ByteBuffer writeBuffer = ByteBuffer.allocate(MAX_LENGTH);

    /**
     * 对应的界面
     */
    protected IMessageWindow logWindow = null;

    /**
     * 是否需要退出
     */
    private boolean quit = false;

    public boolean isQuit()
    {
        return quit;
    }

    public void setQuit(boolean quit)
    {
        this.quit = quit;
    }

    /**
     * 记录从channel中读取到的消息
     * @param channel
     */
    protected void logReceivedMessage(SocketChannel channel)
    {
        if (logWindow != null)
        {
            final MsgInfo msgInfo = new MsgInfo();
            msgInfo.setMsg(readBuffer.array(), 0, readBuffer.limit());
            Socket socket = channel.socket();
            msgInfo.setSrcIP(socket.getRemoteSocketAddress().toString());
            msgInfo.setSrcPort(socket.getPort());
            msgInfo.setDestIP(socket.getLocalSocketAddress().toString());
            msgInfo.setDestPort(socket.getLocalPort());
            Display.getDefault().syncExec(new Runnable()
            {
                @Override
                public void run()
                {
                    logWindow.addMessage(msgInfo);
                }

            });
        }
    }

    /**
     * 记录写入channel中的消息
     */
    protected void logWritedMessage(SocketChannel channel)
    {
        if (logWindow != null)
        {
            final MsgInfo msgInfo = new MsgInfo();
            msgInfo.setMsg(writeBuffer.array(), 0, writeBuffer.limit());
            Socket socket = channel.socket();
            msgInfo.setSrcIP(socket.getLocalSocketAddress().toString());
            msgInfo.setSrcPort(socket.getLocalPort());
            msgInfo.setDestIP(socket.getRemoteSocketAddress().toString());
            msgInfo.setSrcPort(socket.getPort());
            Display.getDefault().syncExec(new Runnable()
            {
                @Override
                public void run()
                {
                    logWindow.addMessage(msgInfo);
                }

            });
        }
    }

}
