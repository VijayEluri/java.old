package org.fanhongtao.tools.treeviewer;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.fanhongtao.tools.treeviewer.bean.AttrBean;

/**
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-22
 */
public class AttrTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;
    
    private List<AttrBean> attrList;
    
    public AttrTableModel(List<AttrBean> attrList)
    {
        super();
        this.attrList = attrList;
    }
    
    public void setAttrList(List<AttrBean> attrList)
    {
        this.attrList = attrList;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount()
    {
        return attrList.size();
    }
    
    @Override
    public int getColumnCount()
    {
        return 2;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        AttrBean tag = attrList.get(rowIndex);
        if (tag == null)
        {
            return null;
        }
        
        switch (columnIndex)
        {
            case 0:
                return tag.getName();
            case 1:
                return tag.getValue();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column)
    {
        switch (column)
        {
            case 0:
                return "Name";
            case 1:
                return "Value";
            default:
                return null;
        }
    }
}
