package org.fanhongtao.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * 将日志记录在SWT的Text控件中。
 * 由于使用了SWT控件，不太好在配置配置文件中配置Text，所以只使用在代码中。
 * 
 * @author Dharma
 * @created 2008-10-29
 */
public class SwtTextLogAppender extends AppenderSkeleton
{
    /** 记录日志的Text控件 */
    private Text text = null;

    public SwtTextLogAppender(Layout layout, Text text)
    {
        this.layout = layout;
        this.text = text;
    }

    public SwtTextLogAppender(Layout layout)
    {
        this(layout, null);
    }

    public void setText(Text text)
    {
        this.text = text;
    }

    @Override
    protected void append(LoggingEvent event)
    {
        if (this.layout == null)
        {
            errorHandler.error("No layout set for the appender named [" + name + "].", null, ErrorCode.MISSING_LAYOUT);
            return;
        }
        final String str = this.layout.format(event);

        // 将日志信息写入Text控件
        if (text != null)
        {
            Display.getDefault().syncExec(new Runnable()
            {
                @Override
                public void run()
                {
                    text.append(str);
                }
            });
        }

    }

    @Override
    public void close()
    {
        if (this.closed) // closed is defined in AppenderSkeleton
            return;
        this.closed = true;
    }

    @Override
    public boolean requiresLayout()
    {
        return true;
    }

}
