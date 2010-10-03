package org.fanhongtao.utils;

import java.util.TimerTask;

import org.fanhongtao.log.RunLogger;


/**
 * @author Dharma
 * @created 2009-6-25
 */
public abstract class SafeTimerTask extends TimerTask
{
    /** 任务的名字 */
    private String name;

    public SafeTimerTask(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run()
    {
        if (null != name)
        {
            RunLogger.debug("Execute timer-task " + name);
        }

        try
        {
            execute();
        }
        catch (Throwable e)
        {
            RunLogger.error("Execute timer-task " + name + " failed.", e);
        }
    }

    abstract protected void execute();
}
