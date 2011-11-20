package org.fanhongtao.tools.tagviewer.bean;

/**
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-20
 */
public class TitleBean
{
    private String columnName;
    
    private String columnValue;
    
    public String getColumnName()
    {
        return columnName;
    }
    
    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }
    
    public String getColumnValue()
    {
        return columnValue;
    }
    
    public void setColumnValue(String columnValue)
    {
        this.columnValue = columnValue;
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(1024);
        buf.append("\tTitle, columnName=\"").append(columnName);
        buf.append("\", columnValue=\"").append(columnValue).append("\"");
        return buf.toString();
    }
    
}
