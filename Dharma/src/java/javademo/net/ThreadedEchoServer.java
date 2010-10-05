package javademo.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This program implements a multithreaded server that listens to port 8189 and
 * echoes back all client input.
 */
public class ThreadedEchoServer
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            int i = 1;
            ServerSocket s = new ServerSocket(8189);
            
            while (true)
            {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming, i);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

/**
 * This class handles the client input for one server socket connection.
 */
class ThreadedEchoHandler implements Runnable
{
    
    public ThreadedEchoHandler(Socket i, int c)
    {
        incoming = i;
        counter = c;
    }
    
    @Override
    public void run()
    {
        try
        {
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Thread " + counter + " exit.");
    }
    
    private Socket incoming;
    
    private int counter;
}
