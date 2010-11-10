package org.fanhongtao.log;

import java.awt.Color;
import java.io.StringWriter;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Level;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * This appender write log messages into a JTextPane controller.<br>
 * If the log level is equal to ERROR, or bigger than ERROR, the message is 
 * written in <b>red</b> color.
 * 
 * @author Fan Hongtao
 * @created 2010-11-10
 */
public class JTextPaneAppender extends WriterAppender
{
    private static SimpleAttributeSet red = new SimpleAttributeSet();
    
    private JTextPane textPane;
    
    private StringWriter sw;
    
    static
    {
        StyleConstants.setForeground(red, Color.red);
    }
    
    public JTextPaneAppender(JTextPane textPane)
    {
        this.textPane = textPane;
        sw = new StringWriter(1024);
        setWriter(sw);
    }
    
    @Override
    public void append(LoggingEvent event)
    {
        sw.getBuffer().setLength(0);
        super.append(event);
        
        final String message = sw.toString();
        final int logLevel = event.getLevel().toInt();
        final StyledDocument doc = textPane.getStyledDocument();
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if (logLevel >= Level.ERROR_INT)
                    {
                        doc.insertString(doc.getLength(), message, red);
                    }
                    else
                    {
                        doc.insertString(doc.getLength(), message, null);
                    }
                }
                catch (BadLocationException e)
                {
                    // e.printStackTrace();
                }
            }
        });
    }
}
