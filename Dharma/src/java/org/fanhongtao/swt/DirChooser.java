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
 * ʵ�ֶ�Ŀ¼ѡ��ķ�װ
 * @author Dharma
 * @created 2009-3-31
 */
public class DirChooser
{
    /** ���ÿؼ���Composite */
    private Composite composite;

    /** ������ѡ��·�����ı��� */
    private Text textPath;

    /**
     * ���ö�����Է��ص� Composite ��������λ�ã��磺<br>  
     *   composite.setLayoutData(new GridData(GridData.FILL_BOTH));
     * @param parent ���ؼ�
     * @param labelText �ؼ��ı�
     * @param tipText ��ʾ��ѡ��·�����ı����ϵ���ʾ��Ϣ������Ϊ null
     * @param btnText ѡ��ť����ʾ������
     * @param titleText ���°�ť�󣬵�������ʾ������ʾ������
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
