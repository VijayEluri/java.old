package org.fanhongtao.swt.window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.fanhongtao.log.LogUtils;
import org.fanhongtao.log.RunLogger;

public class ApplicationWindowEx extends ApplicationWindow
{
    /** Resource for I18N */
    private ResourceBundle bundle = null;
    
    public ApplicationWindowEx()
    {
        this(null);
    }
    
    public ApplicationWindowEx(Shell parentShell)
    {
        super(parentShell);
    }
    
    protected void initLog(String log4jFile)
    {
        if (log4jFile == null)
        {
            LogUtils.initBasicLog();
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
    
    public String getBundleString(String key)
    {
        if (null == bundle)
        {
            loadI18nBundle();
        }
        return bundle.getString(key);
    }
    
    public void showErrorMsg(String message)
    {
        showErrorMsg(getShell(), message);
    }
    
    public void showErrorMsg(Shell parent, String message)
    {
        MessageDialog.openError(parent, getBundleString("Message.Error"), message);
    }
    
    public void showError(String title, String info)
    {
        MessageDialog.openError(getShell(), title, info);
    }
    
    public void showInfo(String title, String info)
    {
        MessageDialog.openInformation(getShell(), title, info);
    }
    
    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        addShellImage(shell);
    }
    
    @Override
    protected boolean showTopSeperator()
    {
        if (getMenuBarManager() != null)
        {
            return super.showTopSeperator();
        }
        else
        {
            return false;
        }
    }
    
    protected void addShellImage(Shell shell)
    {
        // the default icon file was generate by f.png, with http://www.bitbug.net/
        String basePath = "org/fanhongtao/swt/window/";
        URL smallURL = this.getClass().getClassLoader().getResource(basePath + "f16.ico");
        URL bigURL = this.getClass().getClassLoader().getResource(basePath + "f32.ico");
        try
        {
            Image smallIcon = new Image(null, smallURL.openStream());
            Image bigIcon = new Image(null, bigURL.openStream());
            shell.setImages(new Image[] { smallIcon, bigIcon });
        }
        catch (IOException e)
        {
            // e.printStackTrace();
        }
    }
    
    private synchronized void loadI18nBundle()
    {
        try
        {
            bundle = ResourceBundle.getBundle(this.getClass().getName());
        }
        catch (Exception e)
        {
            RunLogger.debug("Can't load i18n bundle", e);
        }
    }
}
