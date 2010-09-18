package org.fanhongtao.net.server;

import java.io.IOException;

import org.fanhongtao.log.LogUtils;
import org.fanhongtao.net.frame.aio.Server;
import org.fanhongtao.net.frame.handler.PrintHandler;


public class PrintServer
{
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        LogUtils.initBasicLog();
        Server server = new Server(3456, new PrintHandler());
        server.init();
        server.run();
    }
}
