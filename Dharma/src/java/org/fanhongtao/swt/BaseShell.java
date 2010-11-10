package org.fanhongtao.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Dharma
 * @created 2008-10-22
 */
public abstract class BaseShell
{
    private Shell shell = null;
    
    public void run(String title)
    {
        Display display = new Display();
        shell = new Shell(display);
        shell.setText(title);
        createContents(shell);
        shell.open();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        
        dispose();
        display.dispose();
    }
    
    public abstract void createContents(Shell shell);
    
    protected Shell getShell()
    {
        return shell;
    }
    
    /**
     * Disposes the resources created
     */
    protected void dispose()
    {
        // Do nothing by default
    }
}
