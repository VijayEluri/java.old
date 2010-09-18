package org.fanhongtao.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.fanhongtao.swt.layout.BorderData;
import org.fanhongtao.swt.layout.BorderLayout;

public class BorderLayoutTest
{
    public static void main(String[] args)
    {
        Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setLayout(new BorderLayout());
        Button b1 = new Button(shell, SWT.PUSH);
        b1.setText("North");
        b1.setLayoutData(BorderData.NORTH);
        Button b5 = new Button(shell, SWT.PUSH);
        b5.setText("Center");
        b5.setLayoutData(BorderData.CENTER);
        // shell.pack();
        shell.open();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        display.dispose();
    }
}
