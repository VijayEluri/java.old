package org.fanhongtao.tools.tagviewer.bean;

import java.util.ArrayList;
import java.util.List;

import org.fanhongtao.lang.StringUtils;

/**
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-20
 */
public class GroupBean
{
    private String name;
    
    private TitleBean title;
    
    private List<TagBean> tagList = new ArrayList<TagBean>();
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public TitleBean getTitle()
    {
        return title;
    }
    
    public void setTitle(TitleBean title)
    {
        this.title = title;
    }
    
    public List<TagBean> getTagList()
    {
        return tagList;
    }
    
    public void addTag(TagBean tag)
    {
        this.tagList.add(tag);
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(1024);
        buf.append("Group, name=").append(name).append(", ");
        buf.append(StringUtils.CRLF);
        if (title != null)
        {
            buf.append(title.toString());
            buf.append(StringUtils.CRLF);
        }
        for (TagBean tag : tagList)
        {
            buf.append("\t");
            buf.append(tag.toString());
            buf.append(StringUtils.CRLF);
        }
        buf.append("]");
        return buf.toString();
    }
    
}
