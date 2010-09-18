package org.fanhongtao.tools.dir;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.LogUtils;
import org.fanhongtao.log.RunLogger;
import org.fanhongtao.utils.TimeDuration;


/**
 * 列出指定目录下的文件
 * @author Dharma
 * @created 2009-3-31
 */
public class Dir
{
    private static char fieldSep = '\t';

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void dir(File path)
    {
        StringBuffer buf = new StringBuffer(1024);
        File[] files = path.listFiles();
        if (files == null) // 注意这里需要判断空值
        {
            return;
        }
        Arrays.sort(files);
        for (File one : files)
        {
            if (one.isDirectory())
            {
                dir(one);
            }
            else if (one.isFile())
            {
                try
                {
                    buf.append(one.getCanonicalPath());
                    buf.append(fieldSep);
                    buf.append(one.length());
                    buf.append(fieldSep);
                    buf.append(df.format(one.lastModified()));
                    buf.append(StringUtils.CRLF);
                }
                catch (IOException e)
                {
                    RunLogger.warn("Exception", e);
                }
            }
        }
        RunLogger.info(buf.toString());
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure(); // 使用缺省配置
        LogUtils.changeRootLayout("%m");

        TimeDuration dur = new TimeDuration();
        for (String arg : args)
        {
            System.out.println("Dir: " + arg);
            File path = new File(arg);
            dir(path);
        }
        dur.setStopTime();
        System.err.println(dur);
    }
}
