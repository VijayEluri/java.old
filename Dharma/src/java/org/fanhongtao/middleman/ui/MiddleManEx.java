package org.fanhongtao.middleman.ui;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.log.SwtTextAppender;
import org.fanhongtao.middleman.core.IMessageWindow;
import org.fanhongtao.middleman.server.EchoServer;
import org.fanhongtao.middleman.server.MuteServer;
import org.fanhongtao.net.frame.MsgInfo;
import org.fanhongtao.swt.layout.BorderData;
import org.fanhongtao.swt.layout.BorderLayout;
import org.fanhongtao.swt.window.ApplicationWindowEx;
import org.fanhongtao.utils.Utils;

public class MiddleManEx extends ApplicationWindowEx implements IMessageWindow
{
    private Combo comboServer = null;
    
    private Text textPort = null;
    
    private Button btnStart = null;
    
    private TableViewer tv = null; // 显示消息概要信息的表
    
    private Text textLog = null; // 显示运行日志
    
    private ArrayList<MsgInfo> msgList = new ArrayList<MsgInfo>(); // 记录所收到的完整的消息
    
    private MuteServer server = null;
    
    private int msgSerial = 0;
    
    private long startTime = 0;
    
    public MiddleManEx(Shell parentShell)
    {
        super(parentShell);
    }
    
    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        shell.setText("Middle man");
        shell.setSize(500, 400);
    }
    
    @Override
    protected Control createContents(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new BorderLayout());
        createConfigure(composite);
        createContent(composite);
        return composite;
    }
    
    /**
     * 创建输入监听端口号的
     * @param shell
     */
    private void createConfigure(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(BorderData.NORTH);
        composite.setLayout(new RowLayout());
        
        new Label(composite, SWT.NONE).setText("服务器类型(&T)");
        comboServer = new Combo(composite, SWT.READ_ONLY);
        comboServer.add("Mute Server");
        comboServer.add("Echo Server");
        comboServer.setData("Mute Server", "MuteServer");
        comboServer.setData("Echo Server", "EchoServer");
        comboServer.select(0);
        
        new Label(composite, SWT.NONE).setText(" 监听端口(&P)");
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
            else
            {
                comboServer.setEnabled(false);
            }
        }
        else
        {
            stopServer();
            comboServer.setEnabled(true);
        }
    }
    
    private void createContent(Composite parent)
    {
        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayoutData(BorderData.CENTER);
        
        tv = new TableViewer(sashForm, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
        Table table = tv.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        TableLayout layout = new TableLayout();
        table.setLayout(layout);
        for (int i = 0; i < columnName.length; i++)
        {
            layout.addColumnData(new ColumnWeightData(columnLen[i]));
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(columnName[i]);
        }
        tv.setLabelProvider(new MsgLableProvider());
        tv.setContentProvider(new MsgContentProvider());
        tv.setInput(msgList);
        addListener(tv);
        
        textLog = new Text(sashForm, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        textLog.addDisposeListener(new DisposeListener()
        {
            
            @Override
            public void widgetDisposed(DisposeEvent e)
            {
                shutDownServer();
            }
            
        });
        SwtTextAppender appender = new SwtTextAppender(textLog);
        appender.setLayout(new PatternLayout("%r [%t] %-5p %c - %m%n"));
        Logger.getRootLogger().addAppender(appender);
    }
    
    private boolean startServer()
    {
        String serverType = comboServer.getText();
        int port = Integer.parseInt(textPort.getText().trim());
        if (serverType.equalsIgnoreCase("Echo Server"))
        {
            server = new EchoServer(port, this);
        }
        else
        {
            server = new MuteServer(port, this);
        }
        startTime = System.currentTimeMillis();
        MsgLableProvider provider = (MsgLableProvider)tv.getLabelProvider();
        provider.setStartTime(startTime);
        
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
    
    private void addListener(TableViewer tv)
    {
        tv.addDoubleClickListener(new IDoubleClickListener()
        {
            
            @Override
            public void doubleClick(DoubleClickEvent event)
            {
                IStructuredSelection selection = (IStructuredSelection)event.getSelection();
                MsgInfo msgInfo = (MsgInfo)selection.getFirstElement();
                DetailMessageDialog dlg = new DetailMessageDialog(getShell(), msgInfo);
                dlg.open();
            }
            
        });
    }
    
    public void log(String str)
    {
        textLog.append(str);
        textLog.append(StringUtils.CRLF);
    }
    
    private static final String[] columnName = { "序号", "时间", "发送IP", "发送端口", "接收IP", "接入端口", "消息长度", "消息" };
    
    private static final int[] columnLen = { 15, 15, 20, 20, 20, 20, 20, 30 };
    
    public void addMessage(MsgInfo msgInfo)
    {
        msgInfo.setTime(System.currentTimeMillis() - startTime);
        msgInfo.setSerial(++msgSerial);
        msgList.add(msgInfo);
        tv.refresh();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        MiddleManEx app = new MiddleManEx(null);
        app.initLog("middle_man.properties");
        app.run();
    }
    
}
