package org.fanhongtao.middleman.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.fanhongtao.net.frame.MsgInfo;


/**
 * 
 * @author Dharma
 * @created 2008-12-1
 */
public class MsgLableProvider implements ITableLabelProvider
{
    // Holds the listeners
    ArrayList<ILabelProviderListener> listeners = new ArrayList<ILabelProviderListener>();

    private long startTime = 0;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    @Override
    public Image getColumnImage(Object element, int columnIndex)
    {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex)
    {
        MsgInfo msgInfo = (MsgInfo) element;
        switch (columnIndex)
        {
        case 0:
            return Integer.toString(msgInfo.getSerial());
        case 1:
            // return Long.toString(msgInfo.getTime());
            return formatter.format(new Date(startTime + msgInfo.getTime()));
        case 2:
            return msgInfo.getSrcIP();
        case 3:
            return Integer.toString(msgInfo.getSrcPort());
        case 4:
            return msgInfo.getDestIP();
        case 5:
            return Integer.toString(msgInfo.getDestPort());
        case 6:
            return Integer.toString(msgInfo.getMsg().length);
        case 7:
            if (msgInfo.getMsg().length > 100)
            {
                return new String(msgInfo.getMsg(), 0, 100) + "...";
            }
            else
            {
                return new String(msgInfo.getMsg(), 0, msgInfo.getMsg().length);
            }
        default:
            return null;
        }
    }

    @Override
    public void addListener(ILabelProviderListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public boolean isLabelProperty(Object element, String property)
    {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener)
    {
        listeners.remove(listener);
    }

}
