package org.fanhongtao.db;

import java.util.ArrayList;
import java.util.List;

/**
 * ��¼���ݿ���һ�ű�ı�ṹ
 * @author Dharma
 * @created 2009-6-3
 */
public class Table
{
    /** ���� */
    private String name;

    /** ���е��ֶ� */
    private Column[] columns;

    /** �ڶ�ȡXML�ļ�ʱʹ�õ��б���ȡ�ɹ���ֻʹ�� columns */
    private List<Column> columnList = new ArrayList<Column>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void adjust()
    {
        columns = columnList.toArray(new Column[] {});
    }

    public void addColumn(Column column)
    {
        columnList.add(column);
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(1024);
        buf.append("Table: ");
        buf.append(name);
        for (int i = 0; i < columns.length; i++)
        {
            if (i > 0)
            {
                buf.append(", ");
            }
            buf.append(columns[i].toString());
        }
        return buf.toString();
    }

}
