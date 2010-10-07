package org.fanhongtao.mybatis.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe a index of a {@link Table}
 * @author Fan Hongtao
 * @created 2010-8-20
 */
public class Index
{
    /** The name of the index */
    private String name;

    /** Is this a unique index. The default is false. */
    private boolean unique = false;

    private boolean primaryKey = false;

    /** The column(s) of the index. */
    private List<String> columnList = new ArrayList<String>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isUnique()
    {
        return unique;
    }

    public void setUnique(boolean unique)
    {
        this.unique = unique;
    }

    public boolean isPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public List<String> getColumnList()
    {
        return columnList;
    }

    public void setColumnList(List<String> columnList)
    {
        this.columnList = columnList;
    }

    public void addColumn(String column)
    {
        this.columnList.add(column);
    }
}
