package org.fanhongtao.mybatis.generator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Fan Hongtao
 * @created 2010-10-7
 */
public class ClassInfo
{
    private String pgkName;
    
    private Map<String, String> classMap = new LinkedHashMap<String, String>();
    
    public String getPgkName()
    {
        return pgkName;
    }
    
    public void setPgkName(String pgkName)
    {
        this.pgkName = pgkName;
    }
    
    public void addClass(String className, String classDesc)
    {
        classMap.put(className, classDesc);
    }
    
    public Map<String, String> getClassMap()
    {
        return classMap;
    }
    
    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
