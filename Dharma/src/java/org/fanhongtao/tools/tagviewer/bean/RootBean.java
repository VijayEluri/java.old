package org.fanhongtao.tools.tagviewer.bean;

import java.util.ArrayList;
import java.util.List;

import org.fanhongtao.lang.StringUtils;

/**
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-20
 */
public class RootBean
{
    private List<GroupBean> groupList = new ArrayList<GroupBean>();
    
    public List<GroupBean> getGroupList()
    {
        return groupList;
    }
    
    public void addGroup(GroupBean group)
    {
        this.groupList.add(group);
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(1024);
        for (GroupBean group : groupList)
        {
            buf.append(group.toString());
            buf.append(StringUtils.CRLF);
        }
        return buf.toString();
    }
    
}
