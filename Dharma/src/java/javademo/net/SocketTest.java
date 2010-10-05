package javademo.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketTest
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            Socket s = new Socket("time-A.timefreq.bldrdoc.gov", 13);
            try
            {
                InputStream inStream = s.getInputStream();
                Scanner in = new Scanner(inStream);
                while (in.hasNextLine())
                {
                    String line = in.nextLine();
                    System.out.println(line);
                    ;
                }
            }
            finally
            {
                s.close();
            }
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
