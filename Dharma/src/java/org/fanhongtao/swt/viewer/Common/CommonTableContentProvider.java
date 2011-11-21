package org.fanhongtao.swt.viewer.Common;

import java.util.List;

import org.fanhongtao.swt.viewer.StructuredContentProviderAdapter;

/**
 * @author Fan Hongtao
 * @created 2010-12-19
 */
public class CommonTableContentProvider extends StructuredContentProviderAdapter
{
    
    @SuppressWarnings("rawtypes")
    @Override
    public Object[] getElements(Object inputElement)
    {
        return ((List)inputElement).toArray();
    }
    
}
