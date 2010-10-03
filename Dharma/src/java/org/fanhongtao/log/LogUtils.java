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
     * Initiate log4j in the a simply way.
     */
    public static void initBasicLog()
    {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
        // changeRootLayout("%r [%t] [%F:%L] %p %c %x - %m%n");
        changeRootLayout("%d{yyyy-MM-dd HH:mm:ss} [%t] [%F:%L] %p - %m%n");
    }
    
    /**
     * Change the Layout of <i>Root Logger</i>.
     * @param pattern New pattern
     */
    public static void changeRootLayout(String pattern)
    {
        Enumeration<?> enmu = Logger.getRootLogger().getAllAppenders();
        while (enmu.hasMoreElements())
        {
            Appender a = (Appender) enmu.nextElement();
            a.setLayout(new PatternLayout(pattern));
        }
    }
}
