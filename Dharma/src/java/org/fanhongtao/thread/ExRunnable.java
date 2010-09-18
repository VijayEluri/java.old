package org.fanhongtao.thread;

public abstract class ExRunnable implements Runnable
{
    /** �Ƿ���ֹ���� */
    private boolean stoped = false;

    /** ���� */
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
