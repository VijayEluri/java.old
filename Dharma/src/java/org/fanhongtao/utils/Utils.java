package org.fanhongtao.utils;

public class Utils
{
    public static void sleep(long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            // do nothing
        }
    }
}
