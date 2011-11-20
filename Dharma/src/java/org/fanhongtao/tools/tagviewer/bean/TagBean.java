package org.fanhongtao.tools.tagviewer.bean;

/**
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-20
 */
public class TagBean
{
    private String name;
    
    private String value;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(1024);
        buf.append("Tag, name=\"").append(name);
        buf.append("\", value=\"").append(value).append("\"");
        return buf.toString();
    }
    
}
