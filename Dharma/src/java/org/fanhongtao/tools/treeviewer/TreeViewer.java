package org.fanhongtao.tools.treeviewer;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.fanhongtao.swing.BaseFrame;
import org.fanhongtao.tools.treeviewer.bean.AttrBean;
import org.fanhongtao.tools.treeviewer.bean.NodeBean;
import org.fanhongtao.tools.treeviewer.bean.TreeBean;
import org.fanhongtao.xml.DigesterUtils;

/**
 * TreeViewer is used to show the tree in a special XML format.<br>
 * I write this tool to help analyzing the complicated data structure of the working project. 
 * 
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 * @author Fan Hongtao <fanhongtao@gmail.com>
 * @created 2011-11-22
 */
public class TreeViewer extends BaseFrame implements TreeSelectionListener
{
    private static final List<AttrBean> EMPTY_ATTR_LIST = new ArrayList<AttrBean>();
    
    private TreeBean treeBean;
    
    private JTree tree;
    
    private AttrTableModel tableModel;
    
    @Override
    protected void createContents(JFrame frame)
    {
        // Create tree;
        loadTree();
        
        // Create table
        List<AttrBean> attrList = EMPTY_ATTR_LIST;
        if (treeBean.getNodeList().size() != 0)
        {
            attrList = treeBean.getNodeList().get(0).getAttrList();
        }
        
        tableModel = new AttrTableModel(attrList);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Create splite pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tree, table);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        frame.add(splitPane);
        
        // Maxmize window
        frame.setSize(new Dimension(800, 600));
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    private void loadTree()
    {
        treeBean = new TreeBean();
        
        try
        {
            // InputStream input = rootBean.getClass().getResourceAsStream("tagviewer.xml");
            InputStream input = new FileInputStream("res/treeviewer.xml");
            URL url = treeBean.getClass().getResource("treeviewer_rules.xml");
            treeBean = (TreeBean)DigesterUtils.parse(treeBean, input, url);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(treeBean.dump());
        
        DefaultMutableTreeNode top = null;
        List<NodeBean> nodeList = treeBean.getNodeList();
        if (nodeList.size() == 0)
        {
            top = new DefaultMutableTreeNode("EMPTY tree");
        }
        else
        {
            top = new DefaultMutableTreeNode("root");
            createNodes(top, nodeList);
        }
        
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
    }
    
    private void createNodes(DefaultMutableTreeNode parent, List<NodeBean> nodeList)
    {
        for (NodeBean node : nodeList)
        {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(node);
            parent.add(child);
            
            createNodes(child, node.getNodeList());
        }
    }
    
    public static void main(String[] args)
    {
        // LogUtils.initBasicLog();
        new TreeViewer().run("Tree Viewer");
    }
    
    @Override
    public void valueChanged(TreeSelectionEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        
        if (node == null)
            return;
        
        Object object = node.getUserObject();
        if (object instanceof NodeBean)
        {
            NodeBean nodeBean = (NodeBean)object;
            tableModel.setAttrList(nodeBean.getAttrList());
        }
        else
        {
            tableModel.setAttrList(EMPTY_ATTR_LIST);
        }
    }
}
