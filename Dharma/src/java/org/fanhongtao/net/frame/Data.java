package org.fanhongtao.net.frame;

/**
 * @author Fan Hongtao
 * @created 2010-10-26
 */
public class Data
{
    private byte[] buffer;
    
    private int begin;
    
    private int end;
    
    /** whether the data belongs to a {@link DataPool}  */
    private boolean inPool = false;
    
    public Data(int size)
    {
        buffer = new byte[size];
        begin = 0;
        end = 0;
    }
    
    public Data(byte[] buffer)
    {
        this(buffer, 0, buffer.length);
    }
    
    public Data(byte[] buffer, int begin, int end)
    {
        this.buffer = buffer;
        this.begin = begin;
        this.end = end;
    }
    
    public boolean isInPool()
    {
        return inPool;
    }
    
    public void setInPool(boolean inPool)
    {
        this.inPool = inPool;
    }
    
    public int getSize()
    {
        return end - begin;
    }
    
    public void skip(int num)
    {
        begin += num;
    }
    
    public byte getByte()
    {
        return buffer[begin++];
    }
    
    public Data copyData(Data data)
    {
        byte[] tmpBuffer = new byte[data.getSize()];
        System.arraycopy(data.buffer, data.begin, tmpBuffer, 0, tmpBuffer.length);
        return new Data(tmpBuffer);
    }
}
