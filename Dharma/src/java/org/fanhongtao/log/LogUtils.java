package org.fanhongtao.log;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public final class LogUtils
{
    /**
     * 按最简方式初始化日志系统
     */
    public static void initBasicLog()
    {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
        // changeRootLayout("%r [%t] [%F:%L] %p %c %x - %m%n");
        changeRootLayout("%d{yyyy-MM-dd HH:mm:ss} [%t] [%F:%L] %p - %m%n");
    }

    /**
     * 修改Root Logger对应的Layout。
     * 适用于写小程序时将Logger当println来使用。
     * @param pattern 
     */
    @SuppressWarnings("unchecked")
    public static void changeRootLayout(String pattern)
    {
        Enumeration enmu = Logger.getRootLogger().getAllAppenders();
        while (enmu.hasMoreElements())
        {
            Appender a = (Appender) enmu.nextElement();
            a.setLayout(new PatternLayout(pattern));
        }
    }
}
