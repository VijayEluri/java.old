package org.fanhongtao.mybatis.generator;

import java.io.StringWriter;

/**
 * @author Fan Hongtao
 * @created 2010-8-20
 */
public class ExStringWriter extends StringWriter
{
    private static final String CRLF = System.getProperty("line.separator");

    public ExStringWriter()
    {
        super();
    }

    public ExStringWriter(int initialSize)
    {
        super(initialSize);
    }

    public void writeln(String s)
    {
        write(s);
        write(CRLF);
    }
}
