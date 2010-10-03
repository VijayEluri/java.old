package org.fanhongtao.swt;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * 实现对目录选择的封装
 * @author Dharma
 * @created 2009-3-31
 */
public class DirChooser
{
    /** 放置控件的Composite */
    private Composite composite;

    /** 保存所选择路径的文本域 */
    private Text textPath;

    /**
     * 调用都必须对返回的 Composite 对象设置位置，如：<br>  
     *   composite.setLayoutData(new GridData(GridData.FILL_BOTH));
     * @param parent 父控件
     * @param labelText 控件文本
     * @param tipText 显示所选择路径的文本域上的提示信息，可以为 null
     * @param btnText 选择按钮上显示的文字
     * @param titleText 按下按钮后，弹出的提示框上显示的文字
     * 
     */
    public DirChooser(Composite parent, String labelText, String tipText, String btnText, final String titleText)
    {
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(3, false));

        new Label(composite, SWT.NONE).setText(labelText);
        textPath = new Text(composite, SWT.BORDER);
        textPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        if (tipText != null)
        {
            textPath.setToolTipText(tipText);
        }

        Button btn = new Button(composite, SWT.FLAT);
        btn.setText(btnText);
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                DirectoryDialog dlg = new DirectoryDialog(composite.getShell());
                dlg.setText(titleText);

                String tmp = textPath.getText().trim();
                if (tmp.length() != 0)
                {
                    try
                    {
                        dlg.setFilterPath(new File(tmp).getCanonicalPath());
                    }
                    catch (IOException ex)
                    {
                        // do nothing
                    }
                }

                String dirName = dlg.open();
                if (dirName != null)
                {
                    textPath.setText(dirName);
                }
            }
        });
    }

    public Composite getComposite()
    {
        return composite;
    }

    public Text getTextPath()
    {
        return textPath;
    }

}
