
package org.fanhongtao.tools.misc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.fanhongtao.swing.BaseFrame;

/**
 * 因为GFW的存在，在使用Google搜索时，经常会遇到搜索结果无法打开。<br>
 * 通过本工具，可以将Google的URL链接转换成原始的URL链接。
 * 
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt
 */
public class GoogleUrlTranslater extends BaseFrame implements ClipboardOwner {
    private static final int DEFAULT_WIDTH = 400;
    
    private static final int DEFAULT_HEIGHT = 400;

    private JTextArea googleArea = null;

    private JTextArea originalArea = null;
    
    private Clipboard clipboard;
    

    @Override
    protected void createContents(JFrame frame) {
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Container panel = frame.getContentPane();
        panel.setLayout(new BorderLayout());
        panel.add(getTextPanel(), BorderLayout.CENTER);
        panel.add(getButtonPanel(), BorderLayout.SOUTH);
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    private JPanel getTextPanel() {
        JPanel parent = new JPanel();
        parent.setLayout(new GridLayout(0, 1));

        JPanel textPanel = new JPanel();
        parent.add(textPanel);
        textPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        textPanel.add(label, BorderLayout.NORTH);
        label.setText("Google URL");

        JScrollPane panel = new JScrollPane();
        textPanel.add(panel);
        googleArea = new JTextArea();
        googleArea.setToolTipText("Input Google URL");
        panel.add(googleArea);
        panel.setViewportView(googleArea);

        JPanel detailPanel = new JPanel();
        parent.add(detailPanel);
        detailPanel.setLayout(new BorderLayout());
        label = new JLabel();
        detailPanel.add(label, BorderLayout.NORTH);
        label.setText("Original URL");

        panel = new JScrollPane();
        detailPanel.add(panel);
        originalArea = new JTextArea();
        originalArea.setEditable(false);
        originalArea.setToolTipText("Original URL, just copy & paste this URL to browser.");
        panel.add(originalArea);
        panel.setViewportView(originalArea);

        return parent;
    }

    private JPanel getButtonPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Translater");
        panel.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                translate();
            }

        });

        button = new JButton("Clipboard");
        panel.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                copyToClipboard();
            }

        });
        return panel;
    }

    private void translate() {
        String googleUrl = googleArea.getText();
        int idx = googleUrl.indexOf("url=http");
        String originalUrl = googleUrl.substring(idx + 4);
        originalUrl = originalUrl.replace("%3A", ":");
        originalUrl = originalUrl.replace("%2F", "/");
        originalArea.setText(originalUrl);
    }

    private void copyToClipboard() {
        StringSelection content = new StringSelection(originalArea.getText());
        clipboard.setContents(content, GoogleUrlTranslater.this);
    }
    
    public static void main(String[] args) {
        new GoogleUrlTranslater().run("Google URL Translater");
    }

    @Override
    public void lostOwnership(Clipboard arg0, Transferable arg1) {
    }
}
