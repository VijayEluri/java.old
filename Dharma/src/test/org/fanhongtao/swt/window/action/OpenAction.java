package org.fanhongtao.swt.window.action;

/**
 * @author Fan Hongtao
 * @created 2010-11-16
 */
public class OpenAction extends BaseAction
{
    public OpenAction()
    {
        super(getBundleString("Meun.Open"));
    }
    
    public void run()
    {
        getApp().showInfo("Information", "Open clicked");
    }
    
}
