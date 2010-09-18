package org.fanhongtao.middleman.ui;

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.SwtTextLogAppender;
import org.fanhongtao.middleman.server.MuteServer;
import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.swt.layout.BorderData;
import org.fanhongtao.swt.layout.BorderLayout;
import org.fanhongtao.utils.Utils;


/**
 * @author Dharma
 * @created 2008-10-20
 */
public class MiddleMan
{

    private Text textPort = null;

    private Button btnStart = null;

    private Table table = null; // 显示消息概要信息的表

    private Text textDetail = null; // 显示某一个特定消息的详细信息

    private Text textLog = null; // 显示运行日志

    private ArrayList<MsgInfo> msgList = new ArrayList<MsgInfo>(); // 记录所收到的完整的消息

    private MuteServer server = null;

    static Logger logger = Logger.getLogger(MiddleMan.class);

    /**
     * run函数基本可以不用修改，所有SWT程序都是这样写的。
     */
    public void run()
    {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setSize(600, 500);
        shell.setText("Middle Man");
        createContents(shell);
        // shell.pack(); // 可选步骤
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

    public void createContents(Shell shell)
    {
        shell.setLayout(new BorderLayout());
        createConfigure(shell);
        createContent(shell);
    }

    /**
     * 创建输入监听端口号的
     * @param shell
     */
    private void createConfigure(Shell shell)
    {
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(BorderData.NORTH);
        composite.setLayout(new RowLayout());

        new Label(composite, SWT.NONE).setText("监听端口(&P)");
        textPort = new Text(composite, SWT.NONE);
        textPort.setText("8088          ");

        new Label(composite, SWT.NONE).setText("        ");
        btnStart = new Button(composite, SWT.TOGGLE);
        btnStart.setText("开始(&S)");
        btnStart.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                super.widgetSelected(e);
                pushButton();
            }

        });

    }

    private void pushButton()
    {
        if (btnStart.getSelection())
        {
            if (!startServer())
            {
                btnStart.setSelection(false);
            }
        }
        else
        {
            stopServer();
        }
    }

    private void createContent(Shell shell)
    {
        SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
        sashForm.setLayoutData(BorderData.CENTER);

        table = new Table(sashForm, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        for (int i = 0; i < columnName.length; i++)
        {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(columnName[i]);
            column.pack();
        }

        table.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseDoubleClick(MouseEvent e)
            {
                super.mouseDoubleClick(e);
                showDetailMsg();
            }

        });

        // textDetail = new Text(sashForm, SWT.BORDER | SWT.MULTI);

        textLog = new Text(sashForm, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textLog.addDisposeListener(new DisposeListener()
        {

            @Override
            public void widgetDisposed(DisposeEvent e)
            {
                shutDownServer();
            }

        });
        SwtTextLogAppender appender = new SwtTextLogAppender(new PatternLayout("%r [%t] %-5p %c - %m%n"), textLog);
        Logger.getRootLogger().addAppender(appender);

    }

    private boolean startServer()
    {
        int port = Integer.parseInt(textPort.getText().trim());
        // server = new AsyncServer(port, this);
        boolean ret = server.bind();
        if (ret)
        {
            new Thread(server).start();
            btnStart.setText("停止(&S)");
        }
        return ret;
    }

    private void stopServer()
    {
        server.setQuit(true);
        btnStart.setText("开始(&S)");
    }

    private void shutDownServer()
    {
        // 终止后台线程运行
        if (server != null)
        {
            server.setQuit(true);
            // Server线程select时间为1秒，这里sleep2秒，以便Server有足够的时间退出
            Utils.sleep(2000);
        }
    }

    /**
    * 在文本框中显示详细的消息
    */
    private void showDetailMsg()
    {
        int selIndex = table.getSelectionIndex();
        if (selIndex < 0)
        {
            // logger.warn("No message selected.");
            return;
        }
        TableItem item = table.getItem(selIndex);
        try
        {
            textDetail.setText(item.getText());
            int msgIndex = Integer.parseInt(item.getText(0)) - 1;
            MsgInfo msgInfo = (MsgInfo) msgList.get(msgIndex);
            textDetail.setText(StringUtils.toHexString(msgInfo.getMsg()));
        }
        catch (Exception e)
        {
            textDetail.append("显示错误信息：\r\n");
            String errInfo = e.getLocalizedMessage();
            textDetail.append(errInfo);
        }
    }

    public void log(String str)
    {
        textLog.append(str);
        textLog.append(StringUtils.CRLF);
    }

    private static final String[] columnName = { "序号", "发送IP", "发送端口", "接收IP", "接入端口", "消息长度" };

    void addMessage(MsgInfo msgInfo)
    {
        msgList.add(msgInfo);
        TableItem item = new TableItem(table, SWT.NONE);
        // "序号", "发送IP", "发送端口", "接收IP", "接入端口", "消息长度"
        item.setText(0, Integer.toString(msgList.size()));
        item.setText(1, msgInfo.getSrcIP());
        item.setText(2, Integer.toString(msgInfo.getSrcPort()));
        item.setText(3, msgInfo.getDestIP());
        item.setText(4, Integer.toString(msgInfo.getDestPort()));
        item.setText(5, Integer.toString(msgInfo.getMsg().length));
        table.redraw();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasicConfigurator.configure(); // 使用缺省配置
        new MiddleMan().run();
    }

}
