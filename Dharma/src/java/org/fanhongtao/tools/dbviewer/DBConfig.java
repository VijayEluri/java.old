package org.fanhongtao.tools.dbviewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * 输入数据库的配置信息
 * @author Dharma
 * @created 2010-7-4
 */
public class DBConfig extends JPanel
{
    private static final long serialVersionUID = 7616513524121542033L;

    /** 保存JDBC驱动显示值和实际值的Map */
    private static final Map<String, String> driverMap;
    static
    {
        driverMap = new HashMap<String, String>();
        driverMap.put("H2", "org.h2.Driver");
    }

    private boolean ok;

    private JButton okButton;

    private JDialog dialog;

    /** 选择JDBC驱动的下拉框 */
    private JComboBox comboBox;

    /** 记录URL地址的控件 */
    private JTextField textURL;

    /** 记录数据库用户名信息的控件 */
    private JTextField textUser;

    /** 记录数据库用户密码的控件 */
    private JTextField textPassword;

    public DBConfig()
    {
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 1));
        add(leftPanel, BorderLayout.WEST);

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(4, 1));
        add(midPanel, BorderLayout.CENTER);

        // Driver
        JLabel label = new JLabel("Driver:");
        leftPanel.add(label);
        comboBox = new JComboBox();
        comboBox.addItem("H2");
        midPanel.add(comboBox);

        // URL
        label = new JLabel("URL:");
        leftPanel.add(label);
        textURL = new JTextField("jdbc:h2:tcp://localhost/dharma", 50);
        midPanel.add(textURL);

        // User
        label = new JLabel("User:");
        leftPanel.add(label);
        textUser = new JTextField("sa", 50);
        midPanel.add(textUser);

        // Password
        label = new JLabel("Password:");
        leftPanel.add(label);
        textPassword = new JTextField("", 50);
        midPanel.add(textPassword);

        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                ok = true;
                dialog.setVisible(false);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ok = false;
                dialog.setVisible(false);
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean showDialog(Component parent, String title)
    {
        ok = false;

        // locate the owner frame
        Frame owner = null;
        if (parent instanceof Frame)
            owner = (Frame) parent;
        else
            owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);

        // if first time, or if owner has changed, make new dialog
        if (dialog == null || dialog.getOwner() != owner)
        {
            dialog = new JDialog(owner, true);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(okButton);
            dialog.pack();
        }

        // set title and show dialog
        dialog.setTitle(title);
        dialog.setVisible(true);

        return ok;
    }

    public String getDrivers()
    {
        return driverMap.get(comboBox.getSelectedItem());
    }

    public String getURL()
    {
        return textURL.getText();
    }

    public String getUser()
    {
        return textUser.getText();
    }

    public String getPassword()
    {
        return textPassword.getText();
    }

}
