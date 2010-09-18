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
 * �г�ָ��Ŀ¼���ļ�
 * @author Dharma
 * @created 2009-3-31
 */
public class DirShell extends BaseShell
{
    /** ��Ҫ��ѯ��Ŀ¼ */
    private Text textPath;

    /** ������ʾ��Ϣ���ı� */
    private Text textDetail;

    @Override
    public void createContents(Shell shell)
    {
        shell.setLayout(new GridLayout());

        // ����ѡ��Ŀ¼�İ�ť
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setLayout(new FillLayout());
        DirChooser chooser = new DirChooser(composite, "Ŀ¼(&D)", "��Ҫ��ѯ��Ŀ¼", "ѡ��(&C)", "ѡ��Ŀ¼");
        textPath = chooser.getTextPath();

        // ������ʾ�ļ���Ϣ���ı���
        composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new FillLayout());
        textDetail = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

        // ͨ��Log4J���ı�������ʾ����
        SwtTextLogAppender appender = new SwtTextLogAppender(new PatternLayout("%m"), textDetail);
        Logger.getRootLogger().removeAllAppenders(); // ɾ��ԭ����Appender(�������ڿ���̨����ʾ)
        Logger.getRootLogger().addAppender(appender);

        // ������ʼ��ť
        composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setLayout(new FillLayout());
        Button btnStart = new Button(composite, SWT.NONE);
        btnStart.setText("��ʼ(&S)");
        btnStart.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                TimeDuration dur = new TimeDuration();
                String pathName = textPath.getText();
                File file = new File(pathName);
                Dir.dir(file);
                SWTUtils.showMessage(getShell(), SWT.NONE, "����", StringUtils.CRLF + dur);
            }

        });
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure(); // ʹ��ȱʡ����
        new DirShell().run("Dir");
    }
}
