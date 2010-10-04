package org.fanhongtao.net.server;

import java.io.IOException;

import org.fanhongtao.log.LogUtils;
import org.fanhongtao.net.frame.handler.EchoHandler;
import org.fanhongtao.net.frame.nio.Server;


/**
 * 
 * @author Dharma
 * @created 2009-5-9
 */
public class EchoServer
{
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        LogUtils.initBasicLog();
        Server server = new Server(3456, new EchoHandler());
        server.init();
        server.run();
    }

}
