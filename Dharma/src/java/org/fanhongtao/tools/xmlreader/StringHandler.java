package org.fanhongtao.tools.xmlreader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StringHandler extends PrintHandler
{

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        sb.append(new String(ch, start, length));
        newLine = false;
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException
    {
        level--;
        if (newLine)
        {
            indent();
        }
        sb.append("</");
        sb.append(name);
        sb.append('>');
        sb.append(CRLF);
        newLine = true;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
    {
        if (!newLine)
        {
            sb.append(CRLF);
        }
        indent();
        level++;
        sb.append('<');
        sb.append(name);
        addAttributes(attributes);
        sb.append('>');
        newLine = false;
    }

    /**
     * 将元素的属性加入字符串
     * 
     * @param attributes
     */
    protected void addAttributes(Attributes attributes)
    {
        for (int i = 0, n = attributes.getLength(); i < n; i++)
        {
            String attrName = attributes.getLocalName(i);
            String attrValue = attributes.getValue(i);
            sb.append(' ');
            sb.append(attrName);
            sb.append("=\"");
            sb.append(attrValue);
            sb.append('"');
        }
    }

}
