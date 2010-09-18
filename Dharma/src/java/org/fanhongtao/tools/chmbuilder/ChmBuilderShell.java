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

    private Text textChmTitle; // CHM文件的标题

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
        new Label(composite, SWT.NONE).setText("HTML文件目录(&H)");
        textHtmlDir = new Text(composite, SWT.BORDER);
        textHtmlDir.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textHtmlDir.setToolTipText("指定原始的HTML文件所在目录");
        btn = new Button(composite, SWT.FLAT);
        btn.setText("选择(&S)");
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                chooseHtmlDir();
            }
        });

        // CHM中的缺省文件（启动CHM时显示的文件）
        new Label(composite, SWT.NONE).setText("缺省文件(&D)");
        textDefaultPage = new Text(composite, SWT.BORDER);
        textDefaultPage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textDefaultPage.setToolTipText("指定启动CHM时显示的页面");
        btn = new Button(composite, SWT.FLAT);
        btn.setText("选择(&E)");
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                chooseHtmlIndexFile();
            }
        });

        // CHM file
        new Label(composite, SWT.NONE).setText("CHM文件名(&C)");
        textChmFile = new Text(composite, SWT.BORDER);
        textChmFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textChmFile.setToolTipText("输入要生成的CHM文件名");
        btn = new Button(composite, SWT.FLAT);
        btn.setText("选择(&O)");
        btn.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                chooseChmFile();
            }
        });

        // CHM Title
        new Label(composite, SWT.NONE).setText("CHM文件标题(&T)");
        textChmTitle = new Text(composite, SWT.BORDER);
        textChmTitle.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textChmTitle.setToolTipText("输入要生成的CHM文件的标题");
        new Label(composite, SWT.NONE).setText("");

        textChmFile.setText("d:\\java\\Apache\\Commons\\apidocs");
        // 设置缺省值
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
        btnCreateChm.setText("生成CHM文件(&G)");
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
     * 指定原始HTML文件的目录
     */
    private void chooseHtmlDir()
    {
        DirectoryDialog dlg = new DirectoryDialog(getShell());
        dlg.setText("选择原始HTML的目录");

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
     * 指定的HTML index文件名
     */
    private void chooseHtmlIndexFile()
    {
        FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
        dlg.setText("指定启动CHM时的缺省文件");
        dlg.setFilterNames(new String[] { "HTM(*.htm); HTML(*.html)" });
        dlg.setFilterExtensions(new String[] { "*.htm; *.html" });

        // 设置缺省路径
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
     * 指定生成的CHM文件名
     */
    private void chooseChmFile()
    {
        FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
        dlg.setText("指定要生成的CHM文件");
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
                dlg.setFilterPath(new File(tmp).getParent()); // chm文件通常应该在HTML目录外面生成
            }
        }

        String fileName = dlg.open();
        if (fileName != null)
        {
            textChmFile.setText(fileName);
        }
    }

    /**
     * 创建CHM文件
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
            messageBox.setText("错误");
            messageBox.setMessage("文件名、目录名不能为空");
            messageBox.open();
            return;
        }
        try
        {
            ChmBuilder builder = new ChmBuilder(chmFile, htmlDir, defaultHtml, chmTitle);
            builder.run();
            MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
            messageBox.setText("提示");
            messageBox.setMessage("生成CHM文件成功。\nCHM 文件名: " + builder.getChmFileName());
            messageBox.open();
        }
        catch (IOException e)
        {
            MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_ERROR);
            messageBox.setText("错误");
            messageBox.setMessage("生成CHM失败.");
            messageBox.open();
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure(); // 使用缺省配置
        new ChmBuilderShell().run("CHM Builder");
    }
}
