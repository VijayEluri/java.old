package org.fanhongtao.tools.recordviewer;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordInfo
{
    /** ��¼��������Ϣ */
    private String description;

    /** ��¼���ֶηָ��� */
    private String fieldSep;

    /** �����ֶε��б� */
    private ArrayList<RecordField> fieldList;

    public RecordInfo(String description, String fieldSep)
    {
        this.description = description;
        this.fieldSep = fieldSep;
        fieldList = new ArrayList<RecordField>();
    }

    public String getDescription()
    {
        return description;
    }

    void addField(RecordField field)
    {
        fieldList.add(field);
    }

    public String getFieldSep()
    {
        return fieldSep;
    }

    public String getFieldTitle(int index)
    {
        if ((index < 0) || (index >= fieldList.size()))
        {
            return "NULL FIELD: ";
        }

        return fieldList.get(index).getTitle();
    }

    public String getFieldValue(int index, String srcValue)
    {
        if ((index < 0) || (index >= fieldList.size()))
        {
            return srcValue;
        }
        return fieldList.get(index).getFieldValue(srcValue);
    }
}

class RecordField
{
    /** �ֶ���ʾ�� */
    private String title;

    /** �ֶ�ȡֵ���京���ӳ�� */
    private HashMap<String, String> valueMap;

    public RecordField(String title)
    {
        this.title = title;
        valueMap = new HashMap<String, String>();
    }

    public String getTitle()
    {
        return title;
    }

    public String getFieldValue(String srcValue)
    {
        String destValue = valueMap.get(srcValue);
        return (destValue == null) ? srcValue : destValue;
    }

    public void setFieldValue(String src, String dest)
    {
        valueMap.put(src, dest);
    }
}
