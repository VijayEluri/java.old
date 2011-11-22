package org.fanhongtao.tools.treeviewer.bean;

/**
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-22
 */
public class AttrBean
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
    
    public String dump()
    {
        StringBuffer buf = new StringBuffer(1024);
        buf.append("<attr name=\"").append(name);
        buf.append("\", value=\"").append(value).append("\"/>");
        return buf.toString();
    }
}
