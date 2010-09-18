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
        // 这里假设给TableViewer的数据源（input）是一个List对象
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
