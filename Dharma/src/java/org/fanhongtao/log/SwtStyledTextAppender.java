package org.fanhongtao.log;

import java.io.StringWriter;

import org.apache.log4j.Level;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * This appender write log messages into a StyledText controller.<br>
 * If the log level is equal to ERROR, or bigger than ERROR, the message is 
 * written in <b>red</b> color.
 * 
 * @author Fan Hongtao
 * @created 2010-11-10
 */
public class SwtStyledTextAppender extends WriterAppender
{
    private static final Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
    
    /** The StyledText controller to write log text */
    private StyledText styledText = null;
    
    private StringWriter sw;
    
    public SwtStyledTextAppender(StyledText styledText)
    {
        this.styledText = styledText;
        sw = new StringWriter(1024);
        setWriter(sw);
    }
    
    @Override
    public void append(LoggingEvent event)
    {
        sw.getBuffer().setLength(0);
        super.append(event);
        
        // write log message into StyledText controller
        final String message = sw.toString();
        final int logLevel = event.getLevel().toInt();
        Display.getDefault().syncExec(new Runnable()
        {
            @Override
            public void run()
            {
                int start = styledText.getCharCount();
                styledText.append(message);
                if (logLevel >= Level.ERROR_INT)
                {
                    int stop = styledText.getCharCount();
                    StyleRange range = new StyleRange(start, stop - start, red, null);
                    styledText.setStyleRange(range);
                }
            }
        });
    }
}
