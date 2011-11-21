package org.fanhongtao.net.frame;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A {@link Data} pool with fixed length, each <i>data</i> in the pool has the same size.
 * 
 * @author Fan Hongtao
 * @created 2010-10-26
 */
public class DataPool
{
    /** size of each data object */
    private static final int DATA_SIZE = 4 * 1024;
    
    private int dataSize;
    
    private ConcurrentLinkedQueue<Data> dataList;
    
    public DataPool(int poolSize)
    {
        this(poolSize, DATA_SIZE);
    }
    
    public DataPool(int poolSize, int dataSize)
    {
        this.dataSize = dataSize;
        dataList = new ConcurrentLinkedQueue<Data>(new ArrayList<Data>(poolSize));
        for (int i = 0; i < poolSize; i++)
        {
            Data data = new Data(dataSize);
            data.setInPool(true);
            dataList.add(data);
        }
    }
    
    /**
     * Try to borrow a data from the pool.<br>
     * If the required size is big than the data's size, or the pool is empty, 
     * return a new data with the specified size.
     * Otherwise, return a data from the pool.
     * 
     * @param requiredSize
     * @return
     */
    public Data borrowData(int requiredSize)
    {
        Data data = null;
        if (requiredSize > this.dataSize)
        {
            data = new Data(requiredSize);
        }
        else
        {
            data = dataList.poll();
            if (null == data)
            {
                data = new Data(requiredSize);
            }
        }
        return data;
    }
    
    public void returnData(Data data)
    {
        if (data.isInPool())
        {
            dataList.add(data);
        }
    }
}
