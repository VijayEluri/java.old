package org.fanhongtao.tools.xmlreader;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class NamespaceStringHandler extends StringHandler
{

    private HashMap<String, String> uriMap = new HashMap<String, String>();

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

        // 增加新的名字空间
        if (uri.length() != 0) // 有可能元素没有对应的名字空间
        {
            String value = uriMap.get(uri);
            if (value == null) // 新的名字空间
            {
                uriMap.put(uri, uri);
                int index = name.lastIndexOf(localName);
                String nameSpace = name.substring(0, index - 1);
                sb.append(" xmlns:");
                sb.append(nameSpace);
                sb.append("=\"");
                sb.append(uri);
                sb.append('"');
            }
        }
        sb.append('>');
        newLine = false;
    }
}
