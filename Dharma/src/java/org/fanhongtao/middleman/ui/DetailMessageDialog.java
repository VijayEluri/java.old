package org.fanhongtao.middleman.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.net.frame.MsgInfo;


/**
 * 实现显示详细的消息
 * @author Dharma
 * @created 2008-11-26
 */
public class DetailMessageDialog extends Dialog
{
    /**
     * 消息
     */
    private MsgInfo msgInfo;

    /**
     * 用于显示信息的文本框
     */
    private Text textInfo = null;

    public DetailMessageDialog(Shell parentShell, MsgInfo msgInfo)
    {
        super(parentShell);
        this.msgInfo = msgInfo;
        setShellStyle(super.getShellStyle() | SWT.RESIZE | SWT.MAX);
    }

    @Override
    protected Control createDialogArea(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setLayoutData(new GridData(GridData.FILL_BOTH));
        group.setText("&Detail");

        Composite composite = new Composite(group, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        textInfo = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL);
        textInfo.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite c1 = new Composite(composite, SWT.NONE);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        c1.setLayoutData(gridData);
        c1.setLayout(new RowLayout());

        Button b1 = new Button(c1, SWT.RADIO);
        b1.setText("&ASCII");
        b1.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                showAsciiMessage();
            }

        });
        b1.setSelection(true);
        showAsciiMessage(); // 缺省按ASCII形式显示

        Button b2 = new Button(c1, SWT.RADIO);
        b2.setText("&HEX");
        b2.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                showHexMessage();
            }

        });

        // return super.createDialogArea(parent);
        return group;
    }

    @Override
    protected Point getInitialSize()
    {
        return new Point(500, 400);
    }

    private void showAsciiMessage()
    {
        textInfo.setText(new String(msgInfo.getMsg(), 0, msgInfo.getMsg().length));
    }

    private void showHexMessage()
    {
        textInfo.setText(StringUtils.toHexString(msgInfo.getMsg()));
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        byte[] bytes = "hello, world".getBytes();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setMsg(bytes, 0, bytes.length);
        DetailMessageDialog dlg = new DetailMessageDialog(null, msgInfo);
        dlg.open();
    }
}
