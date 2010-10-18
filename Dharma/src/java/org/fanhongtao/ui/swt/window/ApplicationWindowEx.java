package org.fanhongtao.ui.swt.window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.fanhongtao.log.RunLogger;

/**
 * @author Fan Hongtao
 * @created 2010-10-18
 */
public class ApplicationWindowEx extends ApplicationWindow
{
    /** Resource for I18N */
    private ResourceBundle bundle = null;
    
    public ApplicationWindowEx()
    {
        super(null);
    }
    
    public ApplicationWindowEx(Shell parentShell)
    {
        super(parentShell);
    }
    
    public void run()
    {
        setBlockOnOpen(true);
        open();
        Display.getCurrent().dispose();
    }
    
    protected String getBundleString(String key)
    {
        return bundle.getString(key);
    }
    
    protected void showErrorMsg(String message)
    {
        showErrorMsg(getShell(), message);
    }
    
    protected void showErrorMsg(Shell parent, String message)
    {
        MessageDialog.openError(parent, getBundleString("Message.Error"), message);
    }
    
    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        addShellImage(shell);
        loadI18nBundle();
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
        String basePath = "org/fanhongtao/ui/swt/window/";
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
    
    private void loadI18nBundle()
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
