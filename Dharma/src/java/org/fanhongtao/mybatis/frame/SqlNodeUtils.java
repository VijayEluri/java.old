package org.fanhongtao.mybatis.frame;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.xml.dynamic.ChooseSqlNode;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.builder.xml.dynamic.IfSqlNode;
import org.apache.ibatis.builder.xml.dynamic.MixedSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SetSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SqlNode;
import org.apache.ibatis.builder.xml.dynamic.TextSqlNode;
import org.apache.ibatis.builder.xml.dynamic.TrimSqlNode;
import org.apache.ibatis.builder.xml.dynamic.WhereSqlNode;
import org.apache.ibatis.session.Configuration;
import org.fanhongtao.lang.ReflectUtil;

/**
 * @author Fan Hongtao
 * @created 2010-8-31
 */
public class SqlNodeUtils
{
    public static SqlNode clone(SqlNode sqlNode)
    {
        SqlNode newSqlNode = null;
        if (sqlNode instanceof ChooseSqlNode)
        {
            newSqlNode = cloneChooseSqlNode((ChooseSqlNode) sqlNode);
        }
        else if (sqlNode instanceof ForEachSqlNode)
        {
            newSqlNode = cloneForEachSqlNode((ForEachSqlNode) sqlNode);
        }
        else if (sqlNode instanceof IfSqlNode)
        {
            newSqlNode = cloneIfSqlNode((IfSqlNode) sqlNode);
        }
        else if (sqlNode instanceof MixedSqlNode)
        {
            newSqlNode = cloneMixedSqlNode((MixedSqlNode) sqlNode);
        }
        else if (sqlNode instanceof TextSqlNode)
        {
            newSqlNode = cloneTextSqlNode((TextSqlNode) sqlNode);
        }
        else if (sqlNode instanceof SetSqlNode)
        {
            newSqlNode = cloneSetSqlNode((SetSqlNode) sqlNode);
        }
        else if (sqlNode instanceof WhereSqlNode)
        {
            newSqlNode = cloneWhereSqlNode((WhereSqlNode) sqlNode);
        }
        else if (sqlNode instanceof TrimSqlNode)
        {
            newSqlNode = cloneTrimSqlNode((TrimSqlNode) sqlNode);
        }
        else
        {
            throw new RuntimeException("Unsupported SqlNode: " + sqlNode.getClass());
        }
        return newSqlNode;
    }
    
    private static ChooseSqlNode cloneChooseSqlNode(ChooseSqlNode sqlNode)
    {
        @SuppressWarnings("unchecked")
        List<IfSqlNode> ifSqlNodes = (List<IfSqlNode>) ReflectUtil.getField(sqlNode, "ifSqlNodes");
        SqlNode defaultSqlNode = (SqlNode) ReflectUtil.getField(sqlNode, "defaultSqlNode");
        
        List<IfSqlNode> newIfSqlNodes = new ArrayList<IfSqlNode>();
        for (IfSqlNode ifSqlNode : ifSqlNodes)
        {
            newIfSqlNodes.add(cloneIfSqlNode(ifSqlNode));
        }
        
        SqlNode newDefaultSqlNode = null;
        if (defaultSqlNode != null)
        {
            newDefaultSqlNode = clone(defaultSqlNode);
        }
        
        ChooseSqlNode chooseSqlNode = new ChooseSqlNode(newIfSqlNodes, newDefaultSqlNode);
        return chooseSqlNode;
    }
    
    private static ForEachSqlNode cloneForEachSqlNode(ForEachSqlNode sqlNode)
    {
        Configuration configuration = (Configuration) ReflectUtil.getField(sqlNode, "configuration");
        SqlNode contents = (SqlNode) ReflectUtil.getField(sqlNode, "contents");
        String collectionExpression = (String) ReflectUtil.getField(sqlNode, "collectionExpression");
        String index = (String) ReflectUtil.getField(sqlNode, "index");
        String item = (String) ReflectUtil.getField(sqlNode, "item");
        String open = (String) ReflectUtil.getField(sqlNode, "open");
        String close = (String) ReflectUtil.getField(sqlNode, "close");
        String separator = (String) ReflectUtil.getField(sqlNode, "separator");
        
        SqlNode newContents = clone(contents);
        
        ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, newContents, collectionExpression, index,
                item, open, close, separator);
        return forEachSqlNode;
    }
    
    private static IfSqlNode cloneIfSqlNode(IfSqlNode sqlNode)
    {
        SqlNode contents = (SqlNode) ReflectUtil.getField(sqlNode, "contents");
        String test = (String) ReflectUtil.getField(sqlNode, "test");
        
        SqlNode newContents = clone(contents);
        
        IfSqlNode ifSqlNode = new IfSqlNode(newContents, test);
        return ifSqlNode;
    }
    
    private static MixedSqlNode cloneMixedSqlNode(MixedSqlNode sqlNode)
    {
        @SuppressWarnings("unchecked")
        List<SqlNode> contents = (List<SqlNode>) ReflectUtil.getField(sqlNode, "contents");
        
        List<SqlNode> newContents = new ArrayList<SqlNode>();
        for (SqlNode content : contents)
        {
            newContents.add(clone(content));
        }
        
        MixedSqlNode mixedSqlNode = new MixedSqlNode(newContents);
        return mixedSqlNode;
    }
    
    private static TextSqlNode cloneTextSqlNode(TextSqlNode sqlNode)
    {
        String text = (String) ReflectUtil.getField(sqlNode, "text");
        
        TextSqlNode textSqlNode = new TextSqlNode(text);
        return textSqlNode;
    }
    
    private static SetSqlNode cloneSetSqlNode(SetSqlNode sqlNode)
    {
        Configuration configuration = (Configuration) ReflectUtil.getField(TrimSqlNode.class, sqlNode, "configuration");
        SqlNode contents = (SqlNode) ReflectUtil.getField(TrimSqlNode.class, sqlNode, "contents");
        
        SqlNode newContents = clone(contents);
        
        SetSqlNode setSqlNode = new SetSqlNode(configuration, newContents);
        return setSqlNode;
    }
    
    private static WhereSqlNode cloneWhereSqlNode(WhereSqlNode sqlNode)
    {
        Configuration configuration = (Configuration) ReflectUtil.getField(TrimSqlNode.class, sqlNode, "configuration");
        SqlNode contents = (SqlNode) ReflectUtil.getField(TrimSqlNode.class, sqlNode, "contents");
        
        SqlNode newContents = clone(contents);
        
        WhereSqlNode whereSqlNode = new WhereSqlNode(configuration, newContents);
        return whereSqlNode;
    }
    
    private static TrimSqlNode cloneTrimSqlNode(TrimSqlNode sqlNode)
    {
        Configuration configuration = (Configuration) ReflectUtil.getField(sqlNode, "configuration");
        SqlNode contents = (SqlNode) ReflectUtil.getField(sqlNode, "contents");
        String prefix = (String) ReflectUtil.getField(sqlNode, "prefix");
        String prefixesToOverride = (String) ReflectUtil.getField(sqlNode, "prefixesToOverride");
        String suffix = (String) ReflectUtil.getField(sqlNode, "suffix");
        String suffixesToOverride = (String) ReflectUtil.getField(sqlNode, "suffixesToOverride");
        
        SqlNode newContents = clone(contents);
        
        TrimSqlNode trimSqlNode = new TrimSqlNode(configuration, newContents, prefix, prefixesToOverride, suffix,
                suffixesToOverride);
        return trimSqlNode;
    }
}
