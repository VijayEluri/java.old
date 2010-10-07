package org.fanhongtao.mybatis.generator;

/**
 * The column of a table.
 * @author Fan Hongtao
 * @created 2010-8-20
 */
public class Column
{
    /** Column Name */
    private String name;

    /** Column type */
    private String type;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

}
