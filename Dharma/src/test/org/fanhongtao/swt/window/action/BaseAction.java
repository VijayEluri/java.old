package org.fanhongtao.swt.window.action;

import org.eclipse.jface.action.Action;
import org.fanhongtao.swt.window.ApplicationWindowExTest;

/**
 * BaseAction is used to link application window and action(s).
 * 
 * @author Fan Hongtao
 * @created 2010-11-16
 */
public abstract class BaseAction extends Action
{
    protected BaseAction()
    {
        super();
    }
    
    protected BaseAction(String text)
    {
        super(text);
    }
    
    protected static ApplicationWindowExTest getApp()
    {
        return ApplicationWindowExTest.getApp();
    }
    
    protected static String getBundleString(String key)
    {
        return getApp().getBundleString(key);
    }
}
