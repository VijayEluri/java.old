package org.fanhongtao.swing;

import javax.swing.JFrame;

/**
 * @author Dharma
 * @created 2009-5-18
 */
public abstract class BaseFrame
{
    private JFrame frame;
    
    public void run(String title)
    {
        frame = new JFrame();
        frame.setTitle(title);
        createContents(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    abstract protected void createContents(JFrame frame);
    
    protected JFrame getFrame()
    {
        return frame;
    }
    
}
