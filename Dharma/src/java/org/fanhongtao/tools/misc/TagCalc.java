package org.fanhongtao.tools.misc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.swing.BaseFrame;
import org.fanhongtao.swing.panel.TextPanel;

/**
 * @author Dharma
 * @created 2009-5-18
 */
public class TagCalc extends BaseFrame
{
    private JTextField tagField;
    
    private JTextArea resultArea;
    
    /* (non-Javadoc)
     * @see dharma.ui.swing.BaseFrame#createContents(javax.swing.JFrame)
     */
    @Override
    protected void createContents(JFrame frame)
    {
        // frame.setSize(500, 250);
        
        JPanel buttonPanel = new JPanel();
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        JButton btnCalc = new JButton("Calc");
        buttonPanel.add(btnCalc);
        btnCalc.setMnemonic(java.awt.event.KeyEvent.VK_C);
        btnCalc.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                calcTag();
            }
        });
        
        tagField = new JTextField(40);
        TextPanel tagPanel = new TextPanel("&Tag", tagField);
        
        resultArea = new JTextArea(5, 35);
        resultArea.setEditable(false);
        TextPanel resultPanel = new TextPanel("&Result", resultArea);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.add(tagPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.CENTER);
        
        frame.setSize(frame.getPreferredSize());
        frame.setResizable(false);
    }
    
    private void calcTag()
    {
        String tag = tagField.getText().trim();
        int value = 0;
        try
        {
            value = Integer.parseInt(tag);
        }
        catch (NumberFormatException e)
        {
            try
            {
                value = Integer.parseInt(tag, 16);
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(getFrame(), ex.getMessage(), "Invalid Tag value", JOptionPane.OK_OPTION);
                return;
            }
        }
        
        if (value < 0)
        {
            value += 256;
        }
        StringBuffer buf = new StringBuffer();
        buf.append("Hex: ").append(Integer.toHexString(value).toUpperCase()).append(StringUtils.CRLF);
        buf.append("Dec: ").append(value).append(StringUtils.CRLF);
        if (value > 127)
        {
            value -= 256;
            buf.append("Neg: ").append(value).append(StringUtils.CRLF);
        }
        resultArea.setText(buf.toString());
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new TagCalc().run("Tag Calc");
    }
    
}
