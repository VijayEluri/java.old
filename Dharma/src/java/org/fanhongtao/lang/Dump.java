package org.fanhongtao.lang;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ʵ�ֽ�һ��Java����������Է����Ķ��ķ�ʽת�����ַ�����<br>
 * ע�⣬��Ϊʹ����StringBuilder������಻���̰߳�ȫ�ģ�ֻ����һ���߳��ڲ�ʹ������ࡣ<br>
 * ͨ�����÷�������Ҫʱ��ʱ����һ��Dump����Ȼ�������toString�������磺
 *  System.out.println(new Dump().toString(object));
 * 
 * @author Dharma
 * @created 2010-2-28
 */
public class Dump
{
    /** ��ӡ��Ϣ��ȱʡ���� */
    public static final int DEFAULT_LEVEL = 3;

    private static final String CRLF = System.getProperty("line.separator");

    /** ����ת�������е��ַ��� */
    private StringBuilder buf = null;

    /** ��ӡ����������� */
    private int maxLevel = -1;

    /** ��¼ÿ����ӡ�Ķ��󣬷�ֹ�����໥���ö������ѭ�� */
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

    @SuppressWarnings("unchecked")
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

        // �ж϶����Ƿ��ظ�
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
            dumpList((List) obj);
        }
        else if (obj instanceof Map)
        {
            dumpMap((Map) obj);
        }
        else if (obj instanceof byte[])
        {
            dumpByteArray((byte[]) obj);
        }
        else
        {
            Class objClass = obj.getClass();
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
     * ��һ��Map����ת���ַ���
     * @param map
     */
    @SuppressWarnings("unchecked")
    private void dumpMap(Map map)
    {
        Iterator iter = map.entrySet().iterator();
        // while (iter.hasNext())
        for (int i = 0; i < map.entrySet().size(); i++)
        {
            if (i != 0)
            {
                buf.append(", ").append(CRLF);
            }

            Map.Entry entry = (Map.Entry) iter.next();
            buf.append("key: ");
            dumpObj(entry.getKey());
            buf.append(", value: ");
            dumpObj(entry.getValue());
        }
    }

    /**
     * ��һ��List����ת���ַ���
     * @param list
     */
    @SuppressWarnings("unchecked")
    private void dumpList(List list)
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
     * ��һ���������ת���ַ���
     * @param objs ��ת��������
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
     * ��һ���Զ���������ת���ַ���
     * @param obj ��ת�������
     * @param objClass �ö����Ӧ���ඨ��
     */
    @SuppressWarnings("unchecked")
    private void dumpClass(Object obj, Class objClass)
    {
        Field[] fields = objClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            if (i != 0)
            {
                buf.append(", "); // .append(CRLF);
            }
            Field field = fields[i];
            field.setAccessible(true); // �ر�java�ķ������η����
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
