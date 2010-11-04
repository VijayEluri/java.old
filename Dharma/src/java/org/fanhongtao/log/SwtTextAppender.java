package org.fanhongtao.log;

import java.io.StringWriter;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * The appender to write log message into a SWT Text controller.<br>
 * This appender can not used in the Log4j properties because I can't find
 * a easy way to specify the which Text controller is used. So I used this 
 * appender only in the Java Code.
 * @author Dharma
 * @created 2008-10-29
 */
public class SwtTextAppender extends WriterAppender
{
    /** The Text controller to write log text */
    private Text text = null;
    
    private StringWriter sw;
    
    public SwtTextAppender(Text text)
    {
        this.text = text;
        sw = new StringWriter(1024);
        setWriter(sw);
    }
    
    @Override
    public void append(LoggingEvent event)
    {
        sw.getBuffer().setLength(0);
        super.append(event);
        
        // write log message into Text controller
        final String message = sw.toString();
        Display.getDefault().syncExec(new Runnable()
        {
            @Override
            public void run()
            {
                text.append(message);
            }
        });
    }
}
