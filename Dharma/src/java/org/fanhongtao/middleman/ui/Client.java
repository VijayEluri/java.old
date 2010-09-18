package org.fanhongtao.middleman.ui;

import org.eclipse.swt.widgets.Shell;
import org.fanhongtao.swt.windows.ApplicationWindowEx;


public class Client extends ApplicationWindowEx
{

    public Client(Shell parentShell)
    {
        super(parentShell);
    }

    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        shell.setText("Client");
        shell.setSize(500, 400);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Client app = new Client(null);
        app.initLog("middle_man.properties");
        app.run();
    }

}
