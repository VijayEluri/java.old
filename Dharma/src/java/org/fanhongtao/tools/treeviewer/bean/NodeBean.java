package org.fanhongtao.tools.treeviewer.bean;

import java.util.ArrayList;
import java.util.List;

import org.fanhongtao.lang.StringUtils;

/**
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-22
 */
public class NodeBean
{
    /**
     * Unique ID of Node object.
     */
    private String id;
    
    /**
     * Text displayed in the tree.
     */
    private String title;
    
    /**
     * Type, class name, of node.
     */
    private String type;
    
    private List<AttrBean> attrList = new ArrayList<AttrBean>();
    
    private List<NodeBean> nodeList = new ArrayList<NodeBean>();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public List<AttrBean> getAttrList()
    {
        return attrList;
    }
    
    public void addAttr(AttrBean attr)
    {
        this.attrList.add(attr);
    }
    
    public List<NodeBean> getNodeList()
    {
        return nodeList;
    }
    
    public void addNode(NodeBean node)
    {
        this.nodeList.add(node);
    }
    
    @Override
    public String toString()
    {
        return title;
    }
    
    public String dump()
    {
        StringBuffer buf = new StringBuffer(1024);
        
        buf.append("<node id=\"" + id + "\" title=\"" + title + "\" type=\"" + type + "\">");
        buf.append(StringUtils.CRLF);
        
        for (AttrBean attr : attrList)
        {
            buf.append(attr.dump());
            buf.append(StringUtils.CRLF);
        }
        
        for (NodeBean node : nodeList)
        {
            buf.append(node.dump());
            buf.append(StringUtils.CRLF);
        }
        
        buf.append("</node>");
        return buf.toString();
    }
}
