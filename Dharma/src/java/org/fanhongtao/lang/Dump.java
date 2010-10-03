package org.fanhongtao.lang;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现将一个Java对象的内容以方便阅读的方式转换成字符串。<br>
 * 注意，因为使用了StringBuilder，这个类不是线程安全的，只能在一个线程内部使用这个类。<br>
 * 通常的用法是在需要时临时定义一个Dump对象，然后调用其toString方法。如：
 *  System.out.println(new Dump().toString(object));
 * 
 * @author Dharma
 * @created 2010-2-28
 */
public class Dump
{
    /** 打印信息的缺省层数 */
    public static final int DEFAULT_LEVEL = 3;
    
    private static final String CRLF = System.getProperty("line.separator");
    
    /** 保存转换过程中的字符串 */
    private StringBuilder buf = null;
    
    /** 打印对象的最大层数 */
    private int maxLevel = -1;
    
    /** 记录每个打印的对象，防止对象相互引用而造成死循环 */
    private Set<Integer> path = new HashSet<Integer>();
    
    public Dump()
    {
        this(DEFAULT_LEVEL);
    }
    
    public Dump(int maxLevel)
    {
        buf = new StringBuilder(1024);
        this.maxLevel = maxLevel;
    }
    
    // public String toString(int i)
    // {
    // return Integer.toString(i);
    // }
    
    public String toString(Object obj)
    {
        return toString(null, obj);
    }
    
    public String toString(String prefix, Object obj)
    {
        if (null != prefix)
        {
            buf.append(prefix).append(CRLF);
        }
        dumpObj(obj);
        return buf.toString();
    }
    
    private void dumpObj(Object obj)
    {
        if (path.size() > maxLevel)
        {
            buf.append("Too many level!");
            return;
        }
        
        if (null == obj)
        {
            buf.append("null");
            return;
        }
        else if ((obj instanceof Number) || (obj instanceof String) || (obj instanceof Character)
                || (obj instanceof Boolean))
        {
            buf.append(obj);
            return;
        }
        
        // 判断对象是否重复
        int id = System.identityHashCode(obj);
        if (path.contains(id))
        {
            buf.append("Object is reentered!");
            return;
        }
        path.add(id);
        begin();
        if (obj instanceof List)
        {
            dumpList((List<?>) obj);
        }
        else if (obj instanceof Map)
        {
            dumpMap((Map<?, ?>) obj);
        }
        else if (obj instanceof byte[])
        {
            dumpByteArray((byte[]) obj);
        }
        else
        {
            Class<?> objClass = obj.getClass();
            if (objClass.isArray())
            {
                dumpArray((Object[]) obj);
            }
            else
            {
                dumpClass(obj, objClass);
            }
        }
        end();
        path.remove(id);
    }
    
    /**
     * 将一个Map对象转换字符串
     * @param map
     */
    private void dumpMap(Map<?, ?> map)
    {
        Iterator<?> iter = map.entrySet().iterator();
        // while (iter.hasNext())
        for (int i = 0; i < map.entrySet().size(); i++)
        {
            if (i != 0)
            {
                buf.append(", ").append(CRLF);
            }
            
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
            buf.append("key: ");
            dumpObj(entry.getKey());
            buf.append(", value: ");
            dumpObj(entry.getValue());
        }
    }
    
    /**
     * 将一个List对象转换字符串
     * @param list
     */
    private void dumpList(List<?> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (i != 0)
            {
                buf.append(", ").append(CRLF);
            }
            dumpObj(list.get(i));
        }
    }
    
    /**
     * 将一个数组对象转换字符串
     * @param objs 待转换的数组
     */
    private void dumpArray(Object[] objs)
    {
        for (int i = 0; i < objs.length; i++)
        {
            if (i != 0)
            {
                buf.append(", ").append(CRLF);
            }
            dumpObj(objs[i]);
        }
    }
    
    private void dumpByteArray(byte[] bytes)
    {
        buf.append(StringUtils.toHexString(bytes));
    }
    
    /**
     * 将一个自定义的类对象转换字符串
     * @param obj 待转换类对象
     * @param objClass 该对象对应的类定义
     */
    private void dumpClass(Object obj, Class<?> objClass)
    {
        Field[] fields = objClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            if (i != 0)
            {
                buf.append(", "); // .append(CRLF);
            }
            Field field = fields[i];
            field.setAccessible(true); // 关闭java的访问修饰符检测
            Object value;
            try
            {
                value = field.get(obj);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                continue;
            }
            buf.append(field.getName()).append('=');
            dumpObj(value);
        }
    }
    
    private void begin()
    {
        buf.append('{');
    }
    
    private void end()
    {
        buf.append('}');
    }
}
