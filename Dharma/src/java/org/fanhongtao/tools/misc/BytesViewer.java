package org.fanhongtao.tools.misc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.swt.BaseShell;
import org.fanhongtao.swt.layout.BorderData;
import org.fanhongtao.swt.layout.BorderLayout;

/**
 * 实现将Eclipse中单步跟踪调测是显示的byte[]内容转换成如下形式，方便在理解所查看的内容。<br>
 * This program is used to translate byte[] displayed by Eclipse debugger into a more 
 * readable form. For example, translate <br>
 * <tt>[-28, -67, -96, -27, -91, -67, 100, 115, 102, -28, -67, -96, -27, -91, -67, 100]</tt> <br> 
 * into the following string.<br>
 * <tt>&nbsp&nbsp
 *     [HEX]  :  &nbsp0  &nbsp1  &nbsp2  &nbsp3  &nbsp4 &nbsp5  &nbsp6  &nbsp7  &nbsp8  &nbsp9 10 11 12 13 14 15 ; 0123456789ABCDEF <br>
 *   ----------------------------------------------------------------------------- <br>
 *   00000000h: E4 BD A0 E5 A5 BD 64 73 66 E4 BD A0 E5 A5 BD 64 ; ......dsf......d <br>
 * <tt>
 * 
 * @author Fan Hongtao
 * @created 2010-11-3
 */
public class BytesViewer extends BaseShell
{
    // 需要解析的Byte数组
    private Text srcBytes = null;
    
    // 解析后的Byte数组
    private Text destBytes = null;
    
    // 系统运行过程中的提示信息
    private Text textInfo = null;
    
    /* (non-Javadoc)
     * @see org.fanhongtao.swt.BaseShell#createContents(org.eclipse.swt.widgets.Shell)
     */
    @Override
    public void createContents(Shell shell)
    {
        shell.setLayout(new BorderLayout());
        createBottons(shell);
        createBytesText(shell);
        createInfoText(shell);
    }
    
    private void createBottons(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(BorderData.NORTH);
        composite.setLayout(new RowLayout());
        Button btnStart = new Button(composite, SWT.NONE);
        btnStart.setText("&Start");
        btnStart.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                try
                {
                    parseBytes();
                }
                catch (Exception e1)
                {
                    destBytes.setText("");
                    textInfo.setText("Parse failed: " + e1.getMessage());
                    textInfo.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
                }
            }
        });
    }
    
    private void createBytesText(Composite parent)
    {
        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayoutData(BorderData.CENTER);
        
        // 创建输入原始字符串内容的控件
        Composite composite = new Composite(sashForm, SWT.NONE);
        composite.setLayout(new GridLayout());
        Label label = new Label(composite, SWT.NONE);
        label.setText("&Original Bytes");
        
        srcBytes = new Text(composite, SWT.BORDER | SWT.MULTI);
        srcBytes.setLayoutData(new GridData(GridData.FILL_BOTH));
        srcBytes.setText(DETAULT_BYTES);
        
        // 创建显示转换后字符串的控件
        composite = new Composite(sashForm, SWT.NONE);
        composite.setLayout(new GridLayout());
        label = new Label(composite, SWT.NONE);
        label.setText("&Convert");
        
        destBytes = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
        destBytes.setLayoutData(new GridData(GridData.FILL_BOTH));
        Font font = new Font(getShell().getDisplay(), "Courier New", 10, SWT.NORMAL);
        destBytes.setFont(font);
    }
    
    private void createInfoText(Composite parent)
    {
        textInfo = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
        textInfo.setLayoutData(BorderData.SOUTH);
    }
    
    private void parseBytes()
    {
        String src = srcBytes.getText();
        String[] hexs = src.split("[, \t\\[\\]]+"); // 使用逗号, 空格, TAB, [ 和 ] 这五个字符进
        byte[] bytes = new byte[hexs.length];
        int count = 0;
        for (String s : hexs)
        {
            if (s.length() == 0)
                continue;
            bytes[count++] = Byte.parseByte(s);
        }
        destBytes.setText("  [HEX]  :  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 ; 0123456789ABCDEF\n");
        destBytes.append("-----------------------------------------------------------------------------\n");
        destBytes.append(StringUtils.toHexString(bytes, 0, count));
        textInfo.setText("Parse Success.");
        textInfo.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK));
    }
    
    public static void main(String[] args)
    {
        new BytesViewer().run("Bytes Viewer");
    }
    
    private static final String DETAULT_BYTES = "[-28, -67, -96, -27, -91, -67, 100, 115, 102, -28, -67, -96, -27, -91, -67, 100, 115, 102]";
}
