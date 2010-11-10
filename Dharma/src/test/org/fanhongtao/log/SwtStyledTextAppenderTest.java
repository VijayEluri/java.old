package org.fanhongtao.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.fanhongtao.swt.BaseShell;

/**
 * @author Fan Hongtao
 * @created 2010-11-10
 */
public class SwtStyledTextAppenderTest extends BaseShell
{
    private static Logger log = Logger.getLogger(SwtStyledTextAppenderTest.class);
    
    private StyledText logText;
    
    // The font to use for displaying log message
    private Font font;
    
    /* (non-Javadoc)
     * @see org.fanhongtao.swt.BaseShell#createContents(org.eclipse.swt.widgets.Shell)
     */
    @Override
    public void createContents(Shell shell)
    {
        // create controller
        shell.setLayout(new GridLayout());
        logText = new StyledText(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        logText.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        // set font
        font = new Font(Display.getCurrent(), "Courier New", 10, SWT.NORMAL);
        logText.setFont(font);
        
        // make it an appender of Log4J
        SwtStyledTextAppender appender = new SwtStyledTextAppender(logText);
        appender.setLayout(new PatternLayout(LogUtils.DEFAULT_PATTERN_LAYOUT));
        Logger.getRootLogger().addAppender(appender);
        
        // write some log messages
        log.info("Write some info message");
        log.error("Write some error message");
        log.info("Write some info message, too");
    }
    
    @Override
    protected void dispose()
    {
        // We created this font, so we must dispose it
        if (font != null)
        {
            font.dispose();
        }
    }
    
    public static void main(String[] args)
    {
        new SwtStyledTextAppenderTest().run("StyledText Appender Test");
    }
}
