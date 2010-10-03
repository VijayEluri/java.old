package org.fanhongtao.db;

import java.util.ArrayList;
import java.util.List;

/**
 * The table structure
 * @author Dharma
 * @created 2009-6-3
 */
public class Table
{
    /** Table's name */
    private String name;
    
    /** Columns of the table */
    private Column[] columns;
    
    /** This list is used when reading from XML.<br>
     * After reading, we only use columns */
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
