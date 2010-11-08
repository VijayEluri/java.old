package javademo.swt.viewer.tableviewer;

import java.util.ArrayList;
import java.util.List;

import javademo.swt.viewer.Person;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.fanhongtao.swt.BaseShell;
import org.fanhongtao.swt.SWTUtils;

/**
 * @author Fan Hongtao
 * @created 2010-11-8
 */
public class TableViewerTest extends BaseShell
{
    private TableViewer viewer;
    
    private List<Person> list;
    
    /* (non-Javadoc)
     * @see org.fanhongtao.swt.BaseShell#createContents(org.eclipse.swt.widgets.Shell)
     */
    @Override
    public void createContents(Shell shell)
    {
        shell.setLayout(new GridLayout());
        
        list = new ArrayList<Person>();
        list.add(new Person("Tom", 15, "male"));
        list.add(new Person("Rose", 16, "famale"));
        list.add(new Person("Alice", 17, "famale"));
        list.add(new Person("Alex", 18, "male"));
        
        viewer = new TableViewer(shell, SWT.FULL_SELECTION);
        String[] columnNames = new String[] { "Name", "Age", "Gender" };
        int[] columnWidth = new int[] { 100, 100, 200 };
        Table table = viewer.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        for (int i = 0; i < columnNames.length; i++)
        {
            TableColumn column = new TableColumn(table, SWT.LEFT);
            column.setText(columnNames[i]);
            column.setWidth(columnWidth[i]);
        }
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        viewer.setContentProvider(new PersonContentProvider());
        viewer.setLabelProvider(new PersonLabelProvider());
        viewer.setInput(list); // @see PersonContentProvider#getElements
        
        table.addMouseListener(new MouseAdapter()
        {
            
            @Override
            public void mouseDoubleClick(MouseEvent e)
            {
                StructuredSelection sel = (StructuredSelection)viewer.getSelection();
                Person person = (Person)sel.getFirstElement();
                SWTUtils.showMessage(getShell(), SWT.OK, "Selected person", person.toString());
            }
        });
    }
    
    public static void main(String[] args)
    {
        new TableViewerTest().run("Table Viewer Test");
    }
    
}
