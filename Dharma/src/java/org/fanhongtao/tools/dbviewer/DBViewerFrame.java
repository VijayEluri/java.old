package org.fanhongtao.tools.dbviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Dharma
 * @created 2010-7-4
 */
public class DBViewerFrame extends JFrame
{
    private static final long serialVersionUID = -678384677454161045L;

    private DBConfig dialog;

    /** ��ʾ���ݿ��б��List */
    private JList listTables;

    private JTable tablePrimaryKey;

    private JTable tableIndex;

    private JTable tableColumn;

    private Connection connection;

    public DBViewerFrame()
    {
        createMenu(this);

        JComponent component = createViewPanel();
        this.add(component, BorderLayout.CENTER);
        this.setSize(this.getPreferredSize());
    }

    private void createMenu(JFrame frame)
    {
        JMenuBar mbar = new JMenuBar();
        frame.setJMenuBar(mbar);
        JMenu fileMenu = new JMenu("DB");
        mbar.add(fileMenu);

        JMenuItem connectItem = new JMenuItem("Connect");
        connectItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (null == dialog)
                {
                    dialog = new DBConfig();
                }

                if (dialog.showDialog(DBViewerFrame.this, "Connect"))
                {
                    connectDB();
                }
            }
        });
        fileMenu.add(connectItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
    }

    private JComponent createViewPanel()
    {
        listTables = new JList();
        listTables.setMinimumSize(new Dimension(100, 200));
        // listTables.setSize(100, 200);
        listTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ���á���ѡ��
        listTables.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                String tableName = (String) listTables.getSelectedValue();
                if (null != tableName)
                {
                    try
                    {
                        viewTable(tableName);
                    }
                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(listTables);

        JTabbedPane tabbedPane = new JTabbedPane();
        tablePrimaryKey = new JTable(new PrimaryKeyTableModel(null));
        JScrollPane pane = new JScrollPane(tablePrimaryKey);
        tabbedPane.addTab("Primary Key", pane);

        // ����ʱָ����ͷ�����ݡ������㣬�޸ĳ�ʹ�� TableModel �ķ�ʽ
        // String[] columnNames = { "Index", "Column" };
        // Object[][] cells = { { "", "" } };
        // tableIndex = new JTable(cells, columnNames);
        tableIndex = new JTable(new IndexTableModel(null));
        pane = new JScrollPane(tableIndex);
        tabbedPane.addTab("Index", pane);

        tableColumn = new JTable(new ColumnTableModel(null));
        pane = new JScrollPane(tableColumn);
        tabbedPane.addTab("Column", pane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, tabbedPane);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        return splitPane;
    }

    /**
     * �������ݿ⣬��������ߵġ����б�
     */
    private void connectDB()
    {
        String url = dialog.getURL();
        String username = dialog.getUser();
        String password = dialog.getPassword();
        // System.setProperty("jdbc.drivers", dialog.getDrivers());
        try
        {
            Class.forName(dialog.getDrivers());
            if (null != connection)
            {
                connection.close();
                connection = null;
            }

            connection = DriverManager.getConnection(url, username, password);
            listTables.removeAll();
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet mrs = meta.getTables(null, null, null, new String[] { "TABLE" });
            DefaultListModel model = new DefaultListModel();
            // ListModel model = listTables.getModel();
            while (mrs.next())
            {
                model.addElement(mrs.getString(3));
            }
            mrs.close();
            listTables.setModel(model);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * �鿴ָ����ı�ṹ
     * @param tableName ����
     * @throws SQLException 
     */
    private void viewTable(String tableName) throws SQLException
    {
        // ���ñ�ġ���������Ϣ
        DatabaseMetaData dbMeta = connection.getMetaData();
        ResultSet rs = dbMeta.getPrimaryKeys(null, null, tableName);
        // tablePrimaryKey.removeAll();
        // TableModel model = tablePrimaryKey.getModel();
        tablePrimaryKey.setModel(new PrimaryKeyTableModel(rs));
        rs.close();

        /* 
         * System.err.println("TABLE_CAT : "+pkRSet.getObject(1));
        System.err.println("TABLE_SCHEM: "+pkRSet.getObject(2));
        System.err.println("TABLE_NAME : "+pkRSet.getObject(3));
        System.err.println("COLUMN_NAME: "+pkRSet.getObject(4));
        System.err.println("KEY_SEQ : "+pkRSet.getObject(5));
        System.err.println("PK_NAME : "+pkRSet.getObject(6)); 
         */

        // ���ñ�ġ���������Ϣ
        rs = dbMeta.getIndexInfo(null, null, tableName, false, false);
        tableIndex.setModel(new IndexTableModel(rs));
        rs.close();

        // ���ñ�ġ��ֶΡ���Ϣ
        // ResultSet rs = dbMeta.getColumns(null, null, tableName, null);
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("select * from " + tableName + " where 1!=1");
        tableColumn.setModel(new ColumnTableModel(resultset));
        resultset.close();

    }
}
