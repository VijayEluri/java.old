package org.fanhongtao.net.frame;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * @author Dharma
 * @created 2009-5-2
 * @param <E>
 * @deprecated Use {@link BlockingQueue} .
 */
public class BlockedList<E>
{
    private ArrayList<E> list;

    public BlockedList()
    {
        list = new ArrayList<E>();
    }

    public void add(E e)
    {
        synchronized (list)
        {
            list.add(e);
            list.notifyAll();
        }
    }

    public E get()
    {
        synchronized (list)
        {
            if (!list.isEmpty())
            {
                E e = list.remove(0);
                return e;
            }
            else
            {
                try
                {
                    list.wait(100);
                }
                catch (InterruptedException e)
                {
                }
                return null;
            }
        }
    }
}
