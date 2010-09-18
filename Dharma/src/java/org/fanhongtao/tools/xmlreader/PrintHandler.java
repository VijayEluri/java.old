package org.fanhongtao.tools.xmlreader;

import org.xml.sax.helpers.DefaultHandler;

public class PrintHandler extends DefaultHandler
{

    /**
     * 记录解析后的XML文件
     */
    protected StringBuffer sb = new StringBuffer(1024);

    /**
     * 是已经是一行的开始
     */
    protected boolean newLine = true;

    /**
     * XML文件中的第几层
     */
    protected int level = 0;

    /**
     * 换行符
     */
    protected static final String CRLF = System.getProperty("line.separator");

    /**
     * 实现缩进
     */
    protected void indent()
    {
        for (int i = 0; i < level; i++)
        {
            sb.append("    ");
        }
    }

    /**
     * 返回解析后的XML
     * 
     * @return
     */
    public String getParsedXML()
    {
        return sb.toString();
    }
}
