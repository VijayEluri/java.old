package org.fanhongtao.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class RunLogger
{
    private static String FQCN = RunLogger.class.getName();

    private static Logger logger = Logger.getLogger(RunLogger.class.getName());

    public static void error(String msg)
    {
        logger.log(FQCN, Level.ERROR, msg, null);
    }

    public static void error(String msg, Throwable thrown)
    {
        logger.log(FQCN, Level.ERROR, msg, thrown);
    }

    public static void warn(String msg)
    {
        logger.log(FQCN, Level.WARN, msg, null);
    }

    public static void warn(String msg, Throwable thrown)
    {
        logger.log(FQCN, Level.WARN, msg, thrown);
    }

    public static void info(String msg)
    {
        logger.log(FQCN, Level.INFO, msg, null);
    }

    public static void info(String msg, Throwable thrown)
    {
        logger.log(FQCN, Level.INFO, msg, thrown);
    }

    public static void debug(String msg)
    {
        logger.log(FQCN, Level.DEBUG, msg, null);
    }

    public static void debug(String msg, Throwable thrown)
    {
        logger.log(FQCN, Level.DEBUG, msg, thrown);
    }
}
