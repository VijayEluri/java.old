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
     * ��Shell������ʾ����Ļ�м�
     * 
     * @param shell
     */
    public static void setCenter(Shell shell)
    {
        // ȡ����Ļ�Ŀ�Ⱥ͸߶ȣ���λ�����أ�
        Rectangle rtg = shell.getMonitor().getClientArea();
        int width = rtg.width;
        int height = rtg.height;

        // ȡ��shell�Ŀ�Ⱥ͸߶ȣ���λ�����أ�
        int x = shell.getSize().x;
        int y = shell.getSize().y;

        // ����shell����ʱ�����Ͻǵ�����
        Point p = new Point((width - x) / 2, (height - y) / 2);
        shell.setLocation(p);
    }

    /**
     * ��ʾ�Ի���<br>
     * �����JFace������Ӧ��ʹ��JFace��յ�Dialog��
     * @param shell ��shell
     * @param style �Ի��������
     * @param title �Ի����Title
     * @param message ��Ҫ��ʾ������
     */
    public static void showMessage(Shell shell, int style, String title, String message)
    {
        MessageBox messageBox = new MessageBox(shell, style);
        messageBox.setText(title);
        messageBox.setMessage(message);
        messageBox.open();
    }

}
