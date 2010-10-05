package org.fanhongtao.tools.misc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UnicodeViewer
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        UnicodeFrame frame = new UnicodeFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}

class UnicodeFrame extends JFrame
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final int DEFAULT_WIDTH = 400;
    
    private static final int DEFAULT_HEIGHT = 400;
    
    private static final String CRLF = System.getProperty("line.separator");
    
    private JTextArea textArea = null;
    
    private JTextArea detailArea = null;
    
    public UnicodeFrame()
    {
        setTitle("Unicode Viewer");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Container panel = getContentPane();
        panel.setLayout(new BorderLayout());
        panel.add(getTextPanel(), BorderLayout.CENTER);
        panel.add(getButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel getTextPanel()
    {
        JPanel parent = new JPanel();
        parent.setLayout(new GridLayout(0, 1));
        
        JPanel textPanel = new JPanel();
        parent.add(textPanel);
        textPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        textPanel.add(label, BorderLayout.NORTH);
        label.setText("待编/解码字符");
        
        JScrollPane panel = new JScrollPane();
        textPanel.add(panel);
        textArea = new JTextArea();
        textArea.setToolTipText("输入需要编/解码的字符串");
        panel.add(textArea);
        panel.setViewportView(textArea);
        
        JPanel detailPanel = new JPanel();
        parent.add(detailPanel);
        detailPanel.setLayout(new BorderLayout());
        label = new JLabel();
        detailPanel.add(label, BorderLayout.NORTH);
        label.setText("Detail");
        
        panel = new JScrollPane();
        detailPanel.add(panel);
        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setToolTipText("只读文本框，用于显示编/解码后的结果");
        panel.add(detailArea);
        panel.setViewportView(detailArea);
        
        return parent;
    }
    
    private JPanel getButtonPanel()
    {
        JPanel panel = new JPanel();
        JButton button = new JButton("编码");
        panel.add(button);
        button.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                encode();
            }
            
        });
        
        button = new JButton("解码");
        panel.add(button);
        button.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                decode();
            }
            
        });
        
        return panel;
    }
    
    private void encode()
    {
        String fixSring[] = { "", "000", "00", "0" };
        char inputChars[] = textArea.getText().toCharArray();
        StringBuffer sb = new StringBuffer();
        StringBuffer detailSb = new StringBuffer();
        for (int i = 0; i < inputChars.length; i++)
        {
            char ch = inputChars[i];
            int intValue = (int) ch;
            String charStr = Integer.toHexString(intValue);
            detailSb.append(inputChars[i]);
            detailSb.append('\t');
            if (charStr.length() < 4)
            {
                sb.append(fixSring[charStr.length()]);
                detailSb.append(fixSring[charStr.length()]);
            }
            sb.append(charStr);
            detailSb.append(charStr);
            detailSb.append(CRLF);
        }
        
        textArea.setText(sb.toString());
        detailArea.setText(detailSb.toString());
    }
    
    private void decode()
    {
        String input = textArea.getText();
        StringBuffer sb = new StringBuffer();
        StringBuffer detailSb = new StringBuffer();
        for (int i = 0; i < input.length(); i = i + 4)
        {
            String charStr = input.substring(i, i + 4);
            int intValue = Integer.parseInt(charStr, 16);
            char ch = (char) intValue;
            sb.append(ch);
            detailSb.append(ch);
            detailSb.append('\t');
            detailSb.append(charStr);
            detailSb.append(CRLF);
        }
        textArea.setText(sb.toString());
        detailArea.setText(detailSb.toString());
    }
}
