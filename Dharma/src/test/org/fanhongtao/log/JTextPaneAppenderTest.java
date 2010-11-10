package org.fanhongtao.log;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.fanhongtao.swing.BaseFrame;

/**
 * @author Fan Hongtao
 * @created 2010-11-10
 */
public class JTextPaneAppenderTest extends BaseFrame
{
    private static Logger log = Logger.getLogger(JTextPaneAppenderTest.class);
    
    private JTextPane logText;
    
    // The font to use for displaying log message
    private Font font;
    
    /* (non-Javadoc)
     * @see org.fanhongtao.swing.BaseFrame#createContents(javax.swing.JFrame)
     */
    @Override
    protected void createContents(JFrame frame)
    {
        frame.setSize(400, 300);
        
        // create controller
        logText = new JTextPane();
        frame.add(logText);
        
        // set font
        font = new Font("Courier New", Font.PLAIN, 15);
        logText.setFont(font);
        
        // make it an appender of Log4J
        JTextPaneAppender appender = new JTextPaneAppender(logText);
        appender.setLayout(new PatternLayout(LogUtils.DEFAULT_PATTERN_LAYOUT));
        Logger.getRootLogger().addAppender(appender);
        
        // write some log messages
        log.info("Write some info message");
        log.error("Write some error message");
        log.info("Write some info message, too");
    }
    
    public static void main(String[] args)
    {
        new JTextPaneAppenderTest().run("JTextPane Appender Test");
    }
}
