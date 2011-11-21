package org.fanhongtao.swt.viewer;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Fan Hongtao
 * @created 2010-12-19
 */
public class TableLabelProviderAdapter implements ITableLabelProvider
{
    @Override
    public void addListener(ILabelProviderListener listener)
    {
    }
    
    @Override
    public void dispose()
    {
    }
    
    @Override
    public boolean isLabelProperty(Object element, String property)
    {
        return false;
    }
    
    @Override
    public void removeListener(ILabelProviderListener listener)
    {
    }
    
    @Override
    public Image getColumnImage(Object element, int columnIndex)
    {
        return null;
    }
    
    @Override
    public String getColumnText(Object element, int columnIndex)
    {
        return null;
    }
    
}
