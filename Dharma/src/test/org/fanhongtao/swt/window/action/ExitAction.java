package org.fanhongtao.swt.window.action;

/**
 * @author Fan Hongtao
 * @created 2010-11-16
 */
public class ExitAction extends BaseAction
{
    public ExitAction()
    {
        super(getBundleString("Menu.Exit"));
        setToolTipText("Exit");
    }
    
    public void run()
    {
        getApp().close();
    }
}
