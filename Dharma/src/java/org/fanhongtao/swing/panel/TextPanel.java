package org.fanhongtao.swing.panel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

/**
 * @author Dharma
 * @created 2009-5-18
 */
public class TextPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private JTextComponent component;
    
    public TextPanel(String text, JTextComponent component)
    {
        // this.setLayout(new GridLayout(0, 2));
        // this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout(10, 10));
        this.component = component;
        int idx = text.indexOf('&');
        JLabel label;
        if (idx == -1)
        {
            label = new JLabel(text);
        }
        else
        {
            text = text.substring(0, idx) + text.substring(idx + 1);
            char ch = text.charAt(idx);
            label = new JLabel(text);
            label.setDisplayedMnemonic((int) ch);
            component.setFocusAccelerator(ch);
        }
        add(label, BorderLayout.WEST);
        add(component, BorderLayout.CENTER);
    }
    
    public JTextComponent getComponent()
    {
        return component;
    }
}
