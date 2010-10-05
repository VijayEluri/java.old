package algorithm.bsf.maze;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Env
{
    private static String fileName;
    
    private static Properties prop;// = new Properties();
    
    private Env()
    {
    }
    
    public synchronized static Properties getInstance()
    {
        if (prop == null)
        {
            prop = new Properties();
            InputStream in;
            try
            {
                if (fileName == null)
                {
                    in = Env.class.getResourceAsStream("maze.txt");
                }
                else
                {
                    // Env env = new Env();
                    // in = env.getClass().getClassLoader().getResourceAsStream(fileName);
                    in = Env.class.getClassLoader().getResourceAsStream(fileName);
                }
                prop.load(in);
                in.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return prop;
    }
    
    /**
     * 获取配置项key对应的整数值
     * @param key 配置项
     * @return key对应的值
     */
    public static int getInteger(String key)
    {
        String value = getInstance().getProperty(key);
        return Integer.valueOf(value);
    }
    
    public static void setInputFileName(String fileName)
    {
        Env.fileName = fileName;
    }
    
}
