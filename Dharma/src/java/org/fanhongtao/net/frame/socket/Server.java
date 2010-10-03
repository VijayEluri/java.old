package org.fanhongtao.net.frame.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    // 监听端口
    private int port;

    private ServerSocket s;

    public Server(int port) throws IOException
    {
        this.port = port;
        s = new ServerSocket(port);
    }

    public void run()
    {
        try
        {
            Socket client = s.accept();
            Connection conn = new Connection(client);
            conn.run();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
