package org.fanhongtao.swt.layout;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.fanhongtao.swt.layout.BorderData;
import org.fanhongtao.swt.layout.BorderLayout;

public class BorderLayoutTest2
{
    public static void main(String[] args)
    {
        Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setLayout(new BorderLayout());
        Button b1 = new Button(shell, SWT.PUSH);
        b1.setText("North");
        b1.setLayoutData(BorderData.NORTH);
        Button b2 = new Button(shell, SWT.PUSH);
        b2.setText("South");
        b2.setLayoutData(BorderData.SOUTH);
        Button b3 = new Button(shell, SWT.PUSH);
        b3.setText("East");
        b3.setLayoutData(BorderData.EAST);
        Button b4 = new Button(shell, SWT.PUSH);
        b4.setText("West");
        b4.setLayoutData(BorderData.WEST);
        Button b5 = new Button(shell, SWT.PUSH);
        b5.setText("Center");
        b5.setLayoutData(BorderData.CENTER);
        shell.pack();
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
