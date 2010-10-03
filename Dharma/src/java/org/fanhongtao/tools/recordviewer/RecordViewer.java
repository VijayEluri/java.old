package org.fanhongtao.tools.recordviewer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 话单读取器，实现快速查看话单字段的值及对应的含义
 * 
 * @author Dharma
 * @created 2009-5-6
 */
public class RecordViewer
{

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        ViewerFrame frame = new ViewerFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class ViewerFrame extends JFrame
{
    private static final long serialVersionUID = 1L;

    private static final String TITLE = "Record Viewer";

    private JTextArea textArea;

    private JTextArea resultArea;

    private JPanel buttonPanel;

    private RecordInfo info;

    public ViewerFrame()
    {
        setTitle(TITLE);
        setSize(600, 400);

        buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton btnParse = new JButton("Parse");
        buttonPanel.add(btnParse);
        btnParse.setMnemonic(java.awt.event.KeyEvent.VK_P);
        btnParse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parseRecord();
            }
        });

        JButton btnChoose = new JButton("Choose");
        buttonPanel.add(btnChoose);
        btnChoose.setMnemonic(java.awt.event.KeyEvent.VK_C);
        btnChoose.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                chooseXML();
            }
        });

        textArea = new JTextArea(8, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);

        resultArea = new JTextArea(8, 40);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, resultArea);

        add(splitPane, BorderLayout.CENTER);
    }

    private void chooseXML()
    {
        String fileName = "record.xml";

        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file (.xml)", "xml");
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(filter);
        chooser.setCurrentDirectory(new File("."));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            info = readFromXml(fileName);
        }
    }

    /**
     * 从XML文件中读取话单格式
     * @param xmlFileName XML文件名
     * @return
     */
    private RecordInfo readFromXml(String xmlFileName)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser saxParser;
        try
        {
            saxParser = factory.newSAXParser();
            File file = new File(xmlFileName);
            RecordHandler handler = new RecordHandler();
            saxParser.parse(file, handler);
            RecordInfo info = handler.getInfo();
            setTitle(TITLE + " - " + info.getDescription());
            return info;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Failed to read xml " + xmlFileName + "\n" + e.getMessage());
            return null;
        }
    }

    /**
     * 解析在文本框中输入的话单
     */
    private void parseRecord()
    {
        if (info == null)
        {
            info = readFromXml("record.xml");
            if (info == null)
            {
                return;
            }
        }
        String record = textArea.getText().trim();
        if (record.length() == 0)
        {
            return;
        }
        StringBuffer resultBuf = new StringBuffer();
        String[] fields = record.split(info.getFieldSep());
        for (int i = 0; i < fields.length; i++)
        {
            resultBuf.append(info.getFieldTitle(i));
            resultBuf.append('\t');
            resultBuf.append(info.getFieldValue(i, fields[i]));
            resultBuf.append('\n');
        }
        resultArea.setText(resultBuf.toString());
    }
}
