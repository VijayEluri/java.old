package javademo.lang;
import java.io.IOException;
import java.util.Properties;

public class SystemInfo
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Properties sysProps = System.getProperties();
        try
        {
            sysProps.store(System.out, "System Properties");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
