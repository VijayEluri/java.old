package org.fanhongtao.tools.chmbuilder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChmBuilderFrame extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JTextField textChmFile;

    private JTextField textHtmlDir;

    private JTextField textDefaultHtml;

    private JButton createButton;

    public ChmBuilderFrame()
    {
        setTitle("CHM Builder");
        // setSize(500, 400);
        JPanel panel = new JPanel();
        // panel.setLayout(new BorderLayout());
        // panel = new JPanel();
        // 创建输入选项的控件
        panel.setLayout(new GridLayout(0, 2));
        add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel();
        label.setText("CHM file");
        panel.add(label);
        textChmFile = new JTextField();
        panel.add(textChmFile);

        label = new JLabel();
        label.setText("HTML directory");
        panel.add(label);
        textHtmlDir = new JTextField();
        panel.add(textHtmlDir);

        label = new JLabel();
        label.setText("HTML index");
        panel.add(label);
        textDefaultHtml = new JTextField();
        panel.add(textDefaultHtml);

        // 设置缺省值
        textChmFile.setText("Apache.Lang.chm");
        textHtmlDir.setText("d:\\java\\Apache\\Commons\\apidocs");
        textDefaultHtml.setText("index.html");

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);
        createButton = new JButton();
        createButton.setText("Create");
        createButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                buildChm();
            }

        });
        btnPanel.add(createButton);

        pack();

    }

    private void buildChm()
    {
        String chmFile = textChmFile.getText();
        String htmlDir = textHtmlDir.getText();
        String defaultHtml = textDefaultHtml.getText();
        try
        {
            ChmBuilder builder = new ChmBuilder(chmFile, htmlDir, defaultHtml, null);
            builder.run();
            JOptionPane.showMessageDialog(this, "Build success.\nCHM file: " + builder.getChmFileName(), "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, "Build failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ChmBuilderFrame frame = new ChmBuilderFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
