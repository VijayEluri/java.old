package javademo.swt.viewer.tableviewer;

import java.util.List;

import javademo.swt.viewer.Person;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Fan Hongtao
 * @created 2010-11-8
 */
public class PersonContentProvider implements IStructuredContentProvider
{
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose()
    {
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement)
    {
        // inputElement is the object setted by TableViewer.setInput()
        @SuppressWarnings("unchecked")
        List<Person> list = (List<Person>)inputElement;
        return list.toArray();
    }
    
}
