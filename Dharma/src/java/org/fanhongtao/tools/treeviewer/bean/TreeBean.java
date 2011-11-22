package org.fanhongtao.tools.treeviewer.bean;

import java.util.ArrayList;
import java.util.List;

import org.fanhongtao.lang.StringUtils;

/**
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-22
 */
public class TreeBean
{
    private List<NodeBean> nodeList = new ArrayList<NodeBean>();
    
    public List<NodeBean> getNodeList()
    {
        return nodeList;
    }
    
    public void addNode(NodeBean node)
    {
        this.nodeList.add(node);
    }
    
    public String dump()
    {
        StringBuffer buf = new StringBuffer(1024);
        
        buf.append("<tree>");
        buf.append(StringUtils.CRLF);
        
        for (NodeBean node : nodeList)
        {
            buf.append(node.dump());
            buf.append(StringUtils.CRLF);
        }
        
        buf.append("</tree>");
        return buf.toString();
    }
}
