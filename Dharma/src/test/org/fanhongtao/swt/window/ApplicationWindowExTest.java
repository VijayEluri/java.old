package org.fanhongtao.swt.window;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.log.LogUtils;
import org.fanhongtao.swt.window.action.ExitAction;
import org.fanhongtao.swt.window.action.OpenAction;

/**
 * @author Fan Hongtao
 * @created 2010-10-18
 */
public class ApplicationWindowExTest extends ApplicationWindowEx
{
    private static ApplicationWindowExTest app;
    
    public ApplicationWindowExTest()
    {
        app = this;
        
        // need to override createMenuManager()
        addMenuBar();
        
        // need to override createStatusLineManager()
        addStatusLine();
    }
    
    public static ApplicationWindowExTest getApp()
    {
        return app;
    }
    
    @Override
    protected MenuManager createMenuManager()
    {
        MenuManager menuManager = new MenuManager();
        
        MenuManager fileMenuManager = new MenuManager(getBundleString("Menu.File"));
        menuManager.add(fileMenuManager);
        
        fileMenuManager.add(new OpenAction());
        fileMenuManager.add(new Separator());
        fileMenuManager.add(new ExitAction());
        
        return menuManager;
    }
    
    @Override
    protected StatusLineManager createStatusLineManager()
    {
        return super.createStatusLineManager();
    }
    
    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        shell.setText(getBundleString("Message.Title"));
        
        new Text(shell, SWT.BORDER | SWT.MULTI);
        
        getStatusLineManager().setMessage("Program started.");
    }
    
    public static void main(String[] args)
    {
        LogUtils.initBasicLog();
        ApplicationWindowExTest window = new ApplicationWindowExTest();
        window.run();
    }
}
