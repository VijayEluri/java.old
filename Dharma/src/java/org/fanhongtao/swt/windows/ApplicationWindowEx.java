package org.fanhongtao.swt.windows;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ApplicationWindowEx extends ApplicationWindow
{

    static Logger logger = Logger.getLogger(ApplicationWindowEx.class);

    public ApplicationWindowEx(Shell parentShell)
    {
        super(parentShell);
    }

    protected void initLog(String log4jFile)
    {
        // ��ʼ����־
        if (log4jFile == null)
        {
            BasicConfigurator.configure(); // ʹ��ȱʡ����
        }
        else
        {
            PropertyConfigurator.configure(log4jFile);
        }
    }

    /**
     * Runs the application
     */
    public void run()
    {
        // Don't return from open() until window closes
        setBlockOnOpen(true);

        // Open the main window
        open();

        // Dispose the display
        Display.getCurrent().dispose();
    }

    protected void showError(String title, String info)
    {
        MessageDialog.openError(getShell(), title, info);
    }

    protected void showInfo(String title, String info)
    {
        MessageDialog.openInformation(getShell(), title, info);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ApplicationWindowEx app = new ApplicationWindowEx(null);
        app.initLog(null);
        app.run();
    }
}
