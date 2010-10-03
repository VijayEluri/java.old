package org.fanhongtao.tools.misc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.swt.BaseShell;


/**
 * @author Dharma
 * @created 2009-5-18
 */
public class TagCalcShell extends BaseShell
{
    private Text tagField;

    private Text resultArea;

    private Button btnCreateChm;

    @Override
    public void createContents(Shell shell)
    {
        shell.setLayout(new GridLayout());
        createInputs(shell);
        createButtons(shell);
        getShell().setSize(400, 300);
    }

    private void createInputs(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new GridLayout(2, false));

        new Label(composite, SWT.NONE).setText("&Tag");
        tagField = new Text(composite, SWT.BORDER);
        tagField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        tagField.setToolTipText("输入要计算的Tag值");

        new Label(composite, SWT.NONE).setText("结果(&R)");
        resultArea = new Text(composite, SWT.BORDER | SWT.MULTI);
        resultArea.setLayoutData(new GridData(GridData.FILL_BOTH));
        resultArea.setToolTipText("计算结果");
    }

    private void createButtons(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        composite.setLayoutData(gridData);
        composite.setLayout(new RowLayout());
        btnCreateChm = new Button(composite, SWT.NONE);
        btnCreateChm.setText("计算(&C)");
        btnCreateChm.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                calcTag();
            }

        });
    }

    private void calcTag()
    {
        String tag = tagField.getText().trim();
        int value = 0;
        try
        {
            if (tag.toLowerCase().startsWith("0x"))
            {
                value = Integer.parseInt(tag.substring(2), 16);
            }
            else
            {
                value = Integer.parseInt(tag);
            }
        }
        catch (NumberFormatException e)
        {
            try
            {
                value = Integer.parseInt(tag, 16);
            }
            catch (NumberFormatException ex)
            {
                MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_ERROR);
                messageBox.setText("Invalid Tag value");
                messageBox.setMessage(ex.getMessage());
                messageBox.open();
                return;
            }
        }

        if (value < 0)
        {
            value += 256;
        }
        StringBuffer buf = new StringBuffer();
        buf.append("Hex: ").append(Integer.toHexString(value).toUpperCase()).append(StringUtils.CRLF);
        buf.append("Dec: ").append(value).append(StringUtils.CRLF);
        if (value > 127)
        {
            value -= 256;
            buf.append("Neg: ").append(value).append(StringUtils.CRLF);
        }
        resultArea.setText(buf.toString());
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new TagCalcShell().run("Tag Calc");
    }

}
