package org.fanhongtao.utils;

import java.text.SimpleDateFormat;

import org.fanhongtao.lang.StringUtils;


public class TimeDuration
{
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long stopTime = 0;

    public TimeDuration()
    {
        startTime = System.currentTimeMillis();
    }

    public long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public long getStopTime()
    {
        return stopTime;
    }

    public void setStopTime()
    {
        stopTime = System.currentTimeMillis();
    }

    public void setStopTime(long stopTime)
    {
        this.stopTime = stopTime;
    }

    @Override
    public String toString()
    {
        if (stopTime == 0)
        {
            stopTime = System.currentTimeMillis();
        }
        StringBuffer buf = new StringBuffer(256);
        buf.append("Start time: ").append(df.format(startTime));
        buf.append(StringUtils.CRLF);
        buf.append("Stop  time: ").append(df.format(stopTime));
        buf.append(StringUtils.CRLF);
        buf.append("Duration  : ");
        long duration = stopTime - startTime;
        buf.append(duration / 1000).append('.').append(duration % 1000);
        buf.append(StringUtils.CRLF);
        return buf.toString();
    }
}
