package org.fanhongtao.net.frame;

/**
 * @author Fan Hongtao
 * @created 2010-10-4
 */
public class Data
{
    private byte[] buffer;
    
    private int begin;
    
    private int end;
    
    public Data()
    {
        this(new byte[1024], 0, 0);
    }
    
    public Data(byte[] buf)
    {
        this(buf, 0, buf.length - 1);
    }
    
    public Data(byte[] buf, int start, int length)
    {
        this.buffer = buf;
        this.begin = start;
        this.end = length;
    }
    
    public int getSize()
    {
        if (end >= begin)
        {
            return end - begin;
        }
        else
        {
            return buffer.length + begin - end;
        }
    }
    
    public int getCapability()
    {
        return buffer.length;
    }
    
    public void add(byte[] buf)
    {
        add(buf, 0, buf.length);
    }
    
    public void add(byte[] buf, int start, int length)
    {
        int size = getSize();
        int capability = getCapability();
        if (capability - size >= length)
        {
            if (end + length < capability)
            {
                System.arraycopy(buf, start, buffer, end, length);
                end += length;
            }
            else
            {
                int partlen = capability - end;
                System.arraycopy(buf, start, buffer, end, partlen);
                System.arraycopy(buf, start + partlen, buffer, 0, length - partlen);
                end = length - partlen;
            }
        }
        else
        {
            do
            {
                capability *= 2;
            } while (capability < size + capability);
            resize(capability);
            System.arraycopy(buf, start, buffer, end, length);
            end += length;
        }
    }
    
    private void resize(int newCapability)
    {
        byte[] buf = new byte[newCapability];
        int size = getSize();
        int capability = getCapability();
        if (begin <= end)
        {
            System.arraycopy(buffer, begin, buf, 0, size);
        }
        else
        {
            int partlen = capability - begin;
            System.arraycopy(buffer, begin, buf, 0, partlen);
            System.arraycopy(buffer, 0, buf, partlen, size - partlen);
        }
        begin = 0;
        end = size;
        buffer = buf;
    }
    
    public void skip(int num)
    {
        begin += num;
        if (begin >= buffer.length)
        {
            begin -= buffer.length;
        }
    }
    
    public byte[] getData()
    {
        byte[] data = peek(getSize());
        begin = end;
        return data;
    }
    
    public byte getByte()
    {
        if (begin == end)
        {
            throw new RuntimeException("Buffer is empty!");
        }
        
        byte b = buffer[begin];
        skip(1);
        return b;
    }
    
    public byte[] peek(int num)
    {
        int size = getSize();
        if (num > size)
        {
            throw new RuntimeException("Can't get " + num + " byte(s) from a bufffer of " + size);
        }
        
        byte[] data = new byte[num];
        int capability = getCapability();
        if (begin + num < capability)
        {
            System.arraycopy(buffer, begin, data, 0, num);
        }
        else
        {
            int partlen = buffer.length - end;
            System.arraycopy(buffer, begin, data, 0, partlen);
            System.arraycopy(buffer, 0, data, partlen, size - partlen);
        }
        return data;
    }
}
