package org.fanhongtao.tools.dir;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.SwtTextLogAppender;
import org.fanhongtao.swt.BaseShell;
import org.fanhongtao.swt.DirChooser;
import org.fanhongtao.swt.SWTUtils;
import org.fanhongtao.utils.TimeDuration;


/**
 * 列出指定目录下文件
 * @author Dharma
 * @created 2009-3-31
 */
public class DirShell extends BaseShell
{
    /** 所要查询的目录 */
    private Text textPath;

    /** 保存显示信息的文本 */
    private Text textDetail;

    @Override
    public void createContents(Shell shell)
    {
        shell.setLayout(new GridLayout());

        // 创建选择目录的按钮
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setLayout(new FillLayout());
        DirChooser chooser = new DirChooser(composite, "目录(&D)", "所要查询的目录", "选择(&C)", "选择目录");
        textPath = chooser.getTextPath();

        // 创建显示文件信息的文本域
        composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new FillLayout());
        textDetail = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

        // 通过Log4J在文本域里显示内容
        SwtTextLogAppender appender = new SwtTextLogAppender(new PatternLayout("%m"), textDetail);
        Logger.getRootLogger().removeAllAppenders(); // 删除原来的Appender(即：不在控制台上显示)
        Logger.getRootLogger().addAppender(appender);

        // 创建开始按钮
        composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setLayout(new FillLayout());
        Button btnStart = new Button(composite, SWT.NONE);
        btnStart.setText("开始(&S)");
        btnStart.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                TimeDuration dur = new TimeDuration();
                String pathName = textPath.getText();
                File file = new File(pathName);
                Dir.dir(file);
                SWTUtils.showMessage(getShell(), SWT.NONE, "结束", StringUtils.CRLF + dur);
            }

        });
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure(); // 使用缺省配置
        new DirShell().run("Dir");
    }
}
