package org.fanhongtao.db;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录数据库中一张表的表结构
 * @author Dharma
 * @created 2009-6-3
 */
public class Table
{
    /** 表名 */
    private String name;

    /** 表中的字段 */
    private Column[] columns;

    /** 在读取XML文件时使用的列表，读取成功后，只使用 columns */
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
