package org.fanhongtao.log;

import java.io.StringWriter;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Fan Hongtao
 * @created 2010-10-31
 */
public class JTextAreaAppender extends WriterAppender
{
    private JTextArea textArea;
    
    private StringWriter sw;
    
    public JTextAreaAppender(JTextArea textArea)
    {
        this.textArea = textArea;
        sw = new StringWriter(1024);
        setWriter(sw);
    }
    
    @Override
    public void append(LoggingEvent event)
    {
        sw.getBuffer().setLength(0);
        super.append(event);
        
        final String message = sw.toString();
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                textArea.append(message);
            }
        });
    }
}
