package org.fanhongtao.tools.recordviewer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * �����ĸ�ʽ����
 * <record separator="," description="���Լ�¼��ʽ">
 *     <field name="field1" />
 *     <field name="field2">
 *         <value src="1" dest="���"/>
 *         <value src="2" dest="Hello"/>
 *     </field>
 * </record>
 * @author Dharma
 * @created 2009-5-6
 */
class RecordHandler extends DefaultHandler
{
    private RecordInfo info;

    private RecordField currField;

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, name, attributes);
        if (localName.equals("field"))
        {
            currField = new RecordField(attributes.getValue(0));
            info.addField(currField);
        }
        else if (localName.equals("value"))
        {
            if (currField == null)
            {
                throw new SAXException("Need 'field' element first.");
            }

            String src = attributes.getValue("src");
            String dest = attributes.getValue("dest");
            if ((src == null) || (dest == null))
            {
                throw new SAXException("'value' element must has both 'src' and 'dest' attributes.");
            }
            currField.setFieldValue(src, dest);
        }
        else if (localName.equals("record"))
        {
            String separator = attributes.getValue("separator");
            String description = attributes.getValue("description");
            info = new RecordInfo(description, separator);
        }

    }

    public RecordInfo getInfo()
    {
        return info;
    }
}
