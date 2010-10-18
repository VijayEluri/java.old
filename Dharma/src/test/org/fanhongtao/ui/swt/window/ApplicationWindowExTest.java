package org.fanhongtao.ui.swt.window;

import org.eclipse.swt.widgets.Shell;
import org.fanhongtao.log.LogUtils;

/**
 * @author Fan Hongtao
 * @created 2010-10-18
 */
public class ApplicationWindowExTest extends ApplicationWindowEx
{
    
    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        shell.setText(getBundleString("Message.Title"));
    }
    
    public static void main(String[] args)
    {
        LogUtils.initBasicLog();
        ApplicationWindowExTest window = new ApplicationWindowExTest();
        window.run();
    }
}
