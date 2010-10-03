package org.fanhongtao.lang.map;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Dharma
 * @created 2010-3-5
 */
public class PictureLRUCache extends LinkedHashMap<String, Picture>
{
    private static final long serialVersionUID = 2688432395514283574L;
    
    private static int sysUpperLimit = 90;
    
    private static int sysLowerLimit = 80;
    
    private static long sysMaxCacheSize = 1000;
    
    private long upperLimit = sysMaxCacheSize / 100 * sysUpperLimit;
    
    private long lowerLimit = sysMaxCacheSize / 100 * sysLowerLimit;
    
    private long cacheSize;
    
    public PictureLRUCache()
    {
        this(16, (float) 0.75);
    }
    
    public PictureLRUCache(int initialCapacity, float loadFactor)
    {
        super(initialCapacity, loadFactor, true);
    }
    
    public long getUpperLimit()
    {
        return upperLimit;
    }
    
    public long getLowerLimit()
    {
        return lowerLimit;
    }
    
    public void show(String title)
    {
        System.out.print("\t" + title + " : ");
        Iterator<Map.Entry<String, Picture>> iterator = this.entrySet().iterator();
        while (iterator.hasNext())
        {
            System.out.print(iterator.next().getKey() + ", ");
        }
        System.out.println();
    }
    
    /**
     * Delete content in the cache.<br>
     * After delete, the number of content is <i>lowerLimit</i> or a bit more than that.
     */
    public void forceDelete()
    {
        Iterator<Map.Entry<String, Picture>> iterator = this.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, Picture> entry = iterator.next();
            Picture pic = entry.getValue();
            if (cacheSize - pic.getSize() < lowerLimit)
            {
                break;
            }
            cacheSize -= pic.getSize();
            iterator.remove();
        }
    }
    
    @Override
    public Picture put(String key, Picture value)
    {
        Picture oldPicture = super.put(key, value);
        if (null == oldPicture)
        {
            cacheSize += value.getSize();
            System.out.println("Add pic: " + value.getName() + ", current size: " + cacheSize);
        }
        return oldPicture;
    }
    
    @Override
    public Picture remove(Object key)
    {
        Picture picture = super.remove(key);
        if (null != picture)
        {
            cacheSize -= picture.getSize();
        }
        return picture;
    }
    
    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<String, Picture> arg0)
    {
        if (cacheSize >= upperLimit)
        {
            Picture pic = arg0.getValue();
            cacheSize -= pic.getSize();
            System.out.println("Try to delete " + pic.getName() + ", current size: " + cacheSize);
            return true;
        }
        else
        {
            return false;
        }
    }
}

class Picture
{
    private int size;
    
    private String name;
    
    public Picture(int size, String name)
    {
        this.size = size;
        this.name = name;
    }
    
    public int getSize()
    {
        return size;
    }
    
    public void setSize(int size)
    {
        this.size = size;
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
