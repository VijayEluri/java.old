package org.fanhongtao.swt.dialog;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.fanhongtao.log.LogUtils;
import org.fanhongtao.log.RunLogger;
import org.fanhongtao.swt.BaseShell;

/**
 * @author Fan Hongtao
 * @created 2010-10-19
 */
public class ListInputDialogTest extends BaseShell
{
    
    /* (non-Javadoc)
     * @see org.fanhongtao.swt.BaseShell#createContents(org.eclipse.swt.widgets.Shell)
     */
    @Override
    public void createContents(final Shell shell)
    {
        shell.setLayout(new GridLayout());
        Button btn = new Button(shell, SWT.NONE);
        btn.setText("Choose");
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                ListInputDialog dialog = new ListInputDialog(shell, "Choose name", "Name", null, null);
                dialog.setBlockOnOpen(true);
                dialog.setItems(new String[] { "alice", "jack", "jim", "frank", "tom" });
                if (dialog.open() == Window.OK)
                {
                    RunLogger.info("Selected  value [" + dialog.getValue() + "]");
                }
            }
        });
        
    }
    
    public static void main(String[] args)
    {
        LogUtils.initBasicLog();
        new ListInputDialogTest().run("List input dialog test");
    }
}
