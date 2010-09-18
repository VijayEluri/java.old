package org.fanhongtao.middleman.ui;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author Dharma
 * @created 2008-12-1
 */
public class MsgContentProvider implements IStructuredContentProvider
{

    @Override
    public Object[] getElements(Object inputElement)
    {
        // ��������TableViewer������Դ��input����һ��List����
        return ((List) inputElement).toArray();
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }

}
