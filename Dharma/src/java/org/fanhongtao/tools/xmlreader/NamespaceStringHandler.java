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

        // �����µ����ֿռ�
        if (uri.length() != 0) // �п���Ԫ��û�ж�Ӧ�����ֿռ�
        {
            String value = uriMap.get(uri);
            if (value == null) // �µ����ֿռ�
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
