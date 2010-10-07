package org.fanhongtao.mybatis.generator;

import java.util.List;

/**
 * Describe a table of a database.
 * @author Fan Hongtao
 * @created 2010-8-20
 */
public class Table
{
    /** The name of the table */
    private String name;

    /** The column list of the table */
    private List<Column> columnList;

    /** The primary key of the table*/
    private Index primaryKey;

    /** The index list of the table, including primary key. */
    private List<Index> indexList;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Column> getColumnList()
    {
        return columnList;
    }

    public void setColumnList(List<Column> columnList)
    {
        this.columnList = columnList;
    }

    public Index getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(Index primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public List<Index> getIndexList()
    {
        return indexList;
    }

    public void setIndexList(List<Index> indexList)
    {
        this.indexList = indexList;
    }

}
