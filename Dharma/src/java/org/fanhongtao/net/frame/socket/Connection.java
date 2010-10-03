package org.fanhongtao.net.frame.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.fanhongtao.net.frame.IConnection;

public class Connection implements IConnection
{
    private Socket socket;
    
    private InputStream in;
    
    private OutputStream out;
    
    public Connection(Socket socket) throws IOException
    {
        this.socket = socket;
    }
    
    public void run()
        throws IOException
    {
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }
}
