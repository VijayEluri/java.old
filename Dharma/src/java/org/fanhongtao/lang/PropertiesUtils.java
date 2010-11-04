package org.fanhongtao.lang;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Fan Hongtao
 * @created 2010-10-29
 */
public class PropertiesUtils
{
    public static Properties readFromFile(String fileName)
        throws IOException
    {
        Properties properties = new Properties();
        InputStream inStream = new BufferedInputStream(new FileInputStream(fileName));
        properties.load(inStream);
        inStream.close();
        return properties;
    }
    
    public static Properties readFromFileQuite(String fileName)
    {
        Properties properties = null;
        try
        {
            properties = readFromFile(fileName);
        }
        catch (IOException e)
        {
            properties = new Properties();
        }
        return properties;
    }
    
    public static void writeToFile(String fileName, Properties properties, String comments)
        throws IOException
    {
        OutputStream outStream = new BufferedOutputStream(new FileOutputStream(fileName));
        properties.store(outStream, comments);
        outStream.close();
    }
    
    public static void writeToFileQuite(String fileName, Properties properties, String comments)
    {
        try
        {
            writeToFile(fileName, properties, comments);
        }
        catch (IOException e)
        {
        }
    }
}
