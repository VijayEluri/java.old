package javademo.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * This program implements a simple server that listens to port 8189 and echoes
 * back all client input.
 * 
 */
public class EchoServer
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            // establish server socket
            ServerSocket s = new ServerSocket(8189);
            
            // wait for client connection
            java.net.Socket incoming = s.accept();
            try
            {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();
                
                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true/* autoFlash */);
                
                out.println("Hello! Enter BYE to exit.");
                
                // echo client input
                boolean done = false;
                while (!done && in.hasNextLine())
                {
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if (line.trim().equals("BYE"))
                        done = true;
                }
            }
            finally
            {
                incoming.close();
            }
            System.out.println("Server Exit.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
