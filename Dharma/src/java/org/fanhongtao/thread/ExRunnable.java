package org.fanhongtao.thread;

public abstract class ExRunnable implements Runnable
{
    /** 是否终止运行 */
    private boolean stoped = false;

    /** 名字 */
    private String name = "";

    public synchronized boolean isStoped()
    {
        return stoped;
    }

    public synchronized void setStoped(boolean stoped)
    {
        this.stoped = stoped;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
