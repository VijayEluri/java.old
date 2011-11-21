package org.fanhongtao.swt.viewer;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Fan Hongtao
 * @created 2010-12-19
 */
public class StructuredContentProviderAdapter implements IStructuredContentProvider
{
    
    @Override
    public void dispose()
    {
    }
    
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }
    
    @Override
    public Object[] getElements(Object inputElement)
    {
        return null;
    }
}
