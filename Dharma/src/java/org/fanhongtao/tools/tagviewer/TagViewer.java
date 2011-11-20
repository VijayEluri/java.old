package org.fanhongtao.tools.tagviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;

import org.fanhongtao.swing.BaseFrame;
import org.fanhongtao.tools.tagviewer.bean.GroupBean;
import org.fanhongtao.tools.tagviewer.bean.RootBean;
import org.fanhongtao.tools.tagviewer.bean.TagBean;
import org.fanhongtao.tools.tagviewer.bean.TitleBean;
import org.fanhongtao.xml.DigesterUtils;

/**
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-20
 */
public class TagViewer extends BaseFrame
{
    
    @Override
    protected void createContents(JFrame frame)
    {
        // Load configuration file
        RootBean rootBean = new RootBean();
        try
        {
            // InputStream input = rootBean.getClass().getResourceAsStream("tagviewer.xml");
            InputStream input = new FileInputStream("res/tagviewer.xml");
            URL url = rootBean.getClass().getResource("tagviewer_rules.xml");
            rootBean = (RootBean)DigesterUtils.parse(rootBean, input, url);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Create Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        List<GroupBean> groupList = rootBean.getGroupList();
        for (GroupBean group : groupList)
        {
            JComponent panel = makeGroupPanel(group);
            tabbedPane.addTab(group.getName(), panel);
        }
        frame.add(tabbedPane);
        
        // Maxmize window
        frame.setSize(new Dimension(800, 600));
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    protected JComponent makeGroupPanel(GroupBean group)
    {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BorderLayout());
        final MyTableModel tableModel = new MyTableModel(group);
        
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel filterPanel = new JPanel(false);
        filterPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.add(new JLabel("Filter"));
        final JTextField filterText = new JTextField(40);
        filterText.getDocument().addDocumentListener(new DocumentListener()
        {
            
            @Override
            public void removeUpdate(DocumentEvent e)
            {
                tableModel.filter(filterText.getText());
            }
            
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                tableModel.filter(filterText.getText());
            }
            
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                tableModel.filter(filterText.getText());
            }
        });
        filterPanel.add(filterText);
        panel.add(filterPanel, BorderLayout.NORTH);
        
        return panel;
    }
    
    class MyTableModel extends AbstractTableModel
    {
        private static final long serialVersionUID = 1L;
        
        private GroupBean group;
        
        private List<TagBean> filtedTagList;
        
        public MyTableModel(GroupBean group)
        {
            super();
            this.group = group;
            this.filtedTagList = group.getTagList();
        }
        
        public void filter(String filter)
        {
            filtedTagList = new ArrayList<TagBean>();
            filter = filter.toLowerCase();
            for (TagBean tag : group.getTagList())
            {
                if (tag.getName().toLowerCase().contains(filter) || tag.getValue().toLowerCase().contains(filter))
                {
                    filtedTagList.add(tag);
                }
            }
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount()
        {
            return filtedTagList.size();
        }
        
        @Override
        public int getColumnCount()
        {
            return 2;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            TagBean tag = filtedTagList.get(rowIndex);
            if (tag == null)
            {
                return null;
            }
            
            switch (columnIndex)
            {
                case 0:
                    return tag.getName();
                case 1:
                    return tag.getValue();
                default:
                    return null;
            }
        }
        
        @Override
        public String getColumnName(int column)
        {
            TitleBean title = group.getTitle();
            switch (column)
            {
                case 0:
                    return title.getColumnName();
                case 1:
                    return title.getColumnValue();
                default:
                    return null;
            }
        }
    }
    
    public static void main(String[] args)
    {
        new TagViewer().run("Tag Viewer");
    }
}
