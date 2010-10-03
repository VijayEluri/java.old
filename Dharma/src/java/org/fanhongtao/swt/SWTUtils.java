package org.fanhongtao.swt;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author Dharma
 * @created 2008-10-18
 */
public final class SWTUtils
{
    private SWTUtils()
    {
    }

    /**
     * 将Shell窗口显示在屏幕中间
     * 
     * @param shell
     */
    public static void setCenter(Shell shell)
    {
        // 取得屏幕的宽度和高度（单位：像素）
        Rectangle rtg = shell.getMonitor().getClientArea();
        int width = rtg.width;
        int height = rtg.height;

        // 取得shell的宽度和高度（单位：像素）
        int x = shell.getSize().x;
        int y = shell.getSize().y;

        // 计算shell居中时，左上角的坐标
        Point p = new Point((width - x) / 2, (height - y) / 2);
        shell.setLocation(p);
    }

    /**
     * 显示对话框<br>
     * 如果是JFace程序，则应该使用JFace封闭的Dialog。
     * @param shell 父shell
     * @param style 对话框的类型
     * @param title 对话框的Title
     * @param message 所要显示的内容
     */
    public static void showMessage(Shell shell, int style, String title, String message)
    {
        MessageBox messageBox = new MessageBox(shell, style);
        messageBox.setText(title);
        messageBox.setMessage(message);
        messageBox.open();
    }

}
