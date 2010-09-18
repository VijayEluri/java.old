package org.fanhongtao.tools.dbviewer;

import javax.swing.JFrame;

/**
 * @author Dharma
 * @created 2010-7-4
 */
public class DBViewer
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        DBViewerFrame frame = new DBViewerFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
