package org.fanhongtao.tools.xmlreader;

import org.xml.sax.helpers.DefaultHandler;

public class PrintHandler extends DefaultHandler
{

    /**
     * ��¼�������XML�ļ�
     */
    protected StringBuffer sb = new StringBuffer(1024);

    /**
     * ���Ѿ���һ�еĿ�ʼ
     */
    protected boolean newLine = true;

    /**
     * XML�ļ��еĵڼ���
     */
    protected int level = 0;

    /**
     * ���з�
     */
    protected static final String CRLF = System.getProperty("line.separator");

    /**
     * ʵ������
     */
    protected void indent()
    {
        for (int i = 0; i < level; i++)
        {
            sb.append("    ");
        }
    }

    /**
     * ���ؽ������XML
     * 
     * @return
     */
    public String getParsedXML()
    {
        return sb.toString();
    }
}
