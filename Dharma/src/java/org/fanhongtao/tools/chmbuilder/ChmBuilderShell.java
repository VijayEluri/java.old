package org.fanhongtao.tools.chmbuilder;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.swt.BaseShell;


/**
 * 
 * @author Dharma
 * @created 2008-11-15
 */
public class ChmBuilderShell extends BaseShell
{
    private Text textHtmlDir;

    private Text textDefaultPage;

    private Text textChmFile;

    private Text textChmTitle; // CHM�ļ��ı���

    private Button btnCreateChm;

    @Override
    public void createContents(Shell shell)
    {
        shell.setLayout(new GridLayout());
        createInputs(shell);
        createButtons(shell);
        getShell().pack();
        textChmFile.setText("");
    }

    private void createInputs(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new GridLayout(3, false));

        Button btn = null;

        // HTML dir
        new Label(composite, SWT.NONE).setText("HTML�ļ�Ŀ¼(&H)");
        textHtmlDir = new Text(composite, SWT.BORDER);
        textHtmlDir.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textHtmlDir.setToolTipText("ָ��ԭʼ��HTML�ļ�����Ŀ¼");
        btn = new Button(composite, SWT.FLAT);
        btn.setText("ѡ��(&S)");
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                chooseHtmlDir();
            }
        });

        // CHM�е�ȱʡ�ļ�������CHMʱ��ʾ���ļ���
        new Label(composite, SWT.NONE).setText("ȱʡ�ļ�(&D)");
        textDefaultPage = new Text(composite, SWT.BORDER);
        textDefaultPage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textDefaultPage.setToolTipText("ָ������CHMʱ��ʾ��ҳ��");
        btn = new Button(composite, SWT.FLAT);
        btn.setText("ѡ��(&E)");
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                chooseHtmlIndexFile();
            }
        });

        // CHM file
        new Label(composite, SWT.NONE).setText("CHM�ļ���(&C)");
        textChmFile = new Text(composite, SWT.BORDER);
        textChmFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textChmFile.setToolTipText("����Ҫ���ɵ�CHM�ļ���");
        btn = new Button(composite, SWT.FLAT);
        btn.setText("ѡ��(&O)");
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                chooseChmFile();
            }
        });

        // CHM Title
        new Label(composite, SWT.NONE).setText("CHM�ļ�����(&T)");
        textChmTitle = new Text(composite, SWT.BORDER);
        textChmTitle.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textChmTitle.setToolTipText("����Ҫ���ɵ�CHM�ļ��ı���");
        new Label(composite, SWT.NONE).setText("");

        textChmFile.setText("d:\\java\\Apache\\Commons\\apidocs");
        // ����ȱʡֵ
        // textChmFile.setText("Apache.Lang.chm");
        // textHtmlDir.setText("d:\\java\\Apache\\Commons\\apidocs");
        // textHtmlIndex.setText("index.html");
    }

    private void createButtons(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        composite.setLayoutData(gridData);
        composite.setLayout(new RowLayout());
        btnCreateChm = new Button(composite, SWT.NONE);
        btnCreateChm.setText("����CHM�ļ�(&G)");
        btnCreateChm.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                generateChm();
            }

        });
    }

    /**
     * ָ��ԭʼHTML�ļ���Ŀ¼
     */
    private void chooseHtmlDir()
    {
        DirectoryDialog dlg = new DirectoryDialog(getShell());
        dlg.setText("ѡ��ԭʼHTML��Ŀ¼");

        String tmp = textHtmlDir.getText().trim();
        if (tmp.length() != 0)
        {
            try
            {
                dlg.setFilterPath(new File(tmp).getCanonicalPath());
            }
            catch (IOException e)
            {
                // do nothing
            }
        }

        String dirName = dlg.open();
        if (dirName != null)
        {
            textHtmlDir.setText(dirName);
        }
    }

    /**
     * ָ����HTML index�ļ���
     */
    private void chooseHtmlIndexFile()
    {
        FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
        dlg.setText("ָ������CHMʱ��ȱʡ�ļ�");
        dlg.setFilterNames(new String[] { "HTM(*.htm); HTML(*.html)" });
        dlg.setFilterExtensions(new String[] { "*.htm; *.html" });

        // ����ȱʡ·��
        String tmp = textDefaultPage.getText().trim();
        if (tmp.length() != 0)
        {
            try
            {
                dlg.setFilterPath(new File(tmp).getCanonicalPath());
            }
            catch (IOException e)
            {
                // do nothing
            }
        }
        else
        {
            tmp = textHtmlDir.getText().trim();
            if (tmp.length() != 0)
            {
                dlg.setFilterPath(tmp);
            }
        }

        String fileName = dlg.open();
        if (fileName != null)
        {
            textDefaultPage.setText(fileName);
        }
    }

    /**
     * ָ�����ɵ�CHM�ļ���
     */
    private void chooseChmFile()
    {
        FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
        dlg.setText("ָ��Ҫ���ɵ�CHM�ļ�");
        dlg.setFilterNames(new String[] { "CHM(*.chm)" });
        dlg.setFilterExtensions(new String[] { "*.chm" });

        String tmp = textChmFile.getText().trim();
        if (tmp.length() != 0)
        {
            try
            {
                dlg.setFilterPath(new File(tmp).getCanonicalPath());
            }
            catch (IOException e)
            {
                // do nothing
            }
        }
        else
        {
            tmp = textHtmlDir.getText().trim();
            if (tmp.length() != 0)
            {
                dlg.setFilterPath(new File(tmp).getParent()); // chm�ļ�ͨ��Ӧ����HTMLĿ¼��������
            }
        }

        String fileName = dlg.open();
        if (fileName != null)
        {
            textChmFile.setText(fileName);
        }
    }

    /**
     * ����CHM�ļ�
     */
    private void generateChm()
    {
        String chmFile = textChmFile.getText().trim();
        String htmlDir = textHtmlDir.getText().trim();
        String defaultHtml = textDefaultPage.getText().trim();
        String chmTitle = textChmTitle.getText().trim();
        if ((chmFile.length() == 0) || (htmlDir.length() == 0) || (defaultHtml.length() == 0))
        {
            MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_ERROR);
            messageBox.setText("����");
            messageBox.setMessage("�ļ�����Ŀ¼������Ϊ��");
            messageBox.open();
            return;
        }
        try
        {
            ChmBuilder builder = new ChmBuilder(chmFile, htmlDir, defaultHtml, chmTitle);
            builder.run();
            MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
            messageBox.setText("��ʾ");
            messageBox.setMessage("����CHM�ļ��ɹ���\nCHM �ļ���: " + builder.getChmFileName());
            messageBox.open();
        }
        catch (IOException e)
        {
            MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_ERROR);
            messageBox.setText("����");
            messageBox.setMessage("����CHMʧ��.");
            messageBox.open();
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure(); // ʹ��ȱʡ����
        new ChmBuilderShell().run("CHM Builder");
    }
}
