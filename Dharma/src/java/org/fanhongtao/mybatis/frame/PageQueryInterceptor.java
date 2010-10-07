package org.fanhongtao.mybatis.frame;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.DynamicSqlSource;
import org.apache.ibatis.builder.xml.dynamic.IfSqlNode;
import org.apache.ibatis.builder.xml.dynamic.MixedSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SqlNode;
import org.apache.ibatis.builder.xml.dynamic.TextSqlNode;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.fanhongtao.lang.ReflectUtil;

/**
 * @author Fan Hongtao
 * @created 2010-8-27
 */

@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class }))
public class PageQueryInterceptor implements Interceptor
{
    
    private static int MAPPED_STATEMENT_INDEX = 0;
    
    private static int PARAMETER_INDEX = 1;
    
    private static int ROWBOUNDS_INDEX = 2;
    
    @Override
    public Object intercept(Invocation invocation)
        throws Throwable
    {
        Executor executor = (Executor) invocation.getTarget();
        Connection connection = executor.getTransaction().getConnection();
        if (MyBatisConfig.hasConnection(connection))
        {
            queryCount(invocation, connection);
        }
        
        return invocation.proceed();
    }
    
    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(Properties properties)
    {
    }
    
    @SuppressWarnings("unchecked")
    private void queryCount(Invocation invocation, Connection connection)
        throws Throwable
    {
        Object[] queryArgs = invocation.getArgs();
        
        MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
        
        // 将需要执行的查询语句修改成 select count(*) 的形式
        MappedStatement queryCountMs = MyBatisConfig.getQueryCountStatement(ms);
        if (null == queryCountMs)
        {
            queryCountMs = createMappedStatement(ms);
            MyBatisConfig.registerQueryCountStatement(ms, queryCountMs);
        }
        
        Executor executor = (Executor) invocation.getTarget();
        SimpleExecutor s = new SimpleExecutor(ms.getConfiguration(), executor.getTransaction());
        List<Integer> list = (List<Integer>) s.doQuery(queryCountMs, parameter, RowBounds.DEFAULT, null);
        if (list.size() == 1)
        {
            int count = (Integer) list.get(0);
            MyBatisConfig.setRecordNum(connection, count);
            
            int offset = rowBounds.getOffset();
            if ((offset > count) && (offset != RowBounds.NO_ROW_OFFSET))
            {
                PageHelper helper = new PageHelper(count, rowBounds.getLimit());
                helper.setCurrPage(helper.getMaxPage());
                queryArgs[ROWBOUNDS_INDEX] = helper.getRowBounds();
            }
        }
        else
        {
            throw new TooManyResultsException("Expected one result to be returned by PageQueryInterceptor.intercept()");
        }
    }
    
    private MappedStatement createMappedStatement(MappedStatement ms)
    {
        MappedStatement queryMs = null;
        synchronized (ms)
        {
            queryMs = MyBatisConfig.getQueryCountStatement(ms);
            if (null != queryMs)
            {
                return queryMs;
            }
        }
        
        DynamicSqlSource sqlSource = (DynamicSqlSource) ms.getSqlSource();
        Configuration configuration = (Configuration) ReflectUtil.getField(sqlSource, "configuration");
        SqlNode rootSqlNode = (SqlNode) ReflectUtil.getField(sqlSource, "rootSqlNode");
        
        MixedSqlNode newRootSqlNode = (MixedSqlNode) SqlNodeUtils.clone(rootSqlNode);
        
        @SuppressWarnings("unchecked")
        List<SqlNode> contents = (List<SqlNode>) ReflectUtil.getField(newRootSqlNode, "contents");
        
        TextSqlNode firstNode = (TextSqlNode) contents.get(0);
        String firstSql = (String) ReflectUtil.getField(firstNode, "text");
        String tmpSql = firstSql.toUpperCase();
        int fromIndex = tmpSql.indexOf("FROM");
        int orderByIndex = tmpSql.indexOf("ORDER BY");
        
        if (orderByIndex > 0)
        {
            tmpSql = "select count(*) " + firstSql.substring(fromIndex, orderByIndex);
            ReflectUtil.setField(firstNode, "text", tmpSql);
        }
        else
        {
            tmpSql = "select count(*) " + firstSql.substring(fromIndex);
            ReflectUtil.setField(firstNode, "text", tmpSql);
            
            for (int i = 1; i < contents.size(); i++)
            {
                SqlNode node = contents.get(i);
                replaceSql(node);
            }
        }
        
        DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(configuration, newRootSqlNode);
        queryMs = copyFromMappedStatement(ms, dynamicSqlSource);
        return queryMs;
    }
    
    private void replaceSql(SqlNode sqlNode)
    {
        if (sqlNode instanceof TextSqlNode)
        {
            TextSqlNode textSqlNode = (TextSqlNode) sqlNode;
            String sql = (String) ReflectUtil.getField(textSqlNode, "text");
            int index = sql.toUpperCase().indexOf("ORDER BY");
            if (index >= 0)
            {
                String tmpSql = sql.substring(0, index);
                ReflectUtil.setField(textSqlNode, "text", tmpSql);
            }
        }
        else if (sqlNode instanceof IfSqlNode)
        {
            IfSqlNode ifSqlNode = (IfSqlNode) sqlNode;
            SqlNode contents = (SqlNode) ReflectUtil.getField(ifSqlNode, "contents");
            replaceSql(contents);
        }
        else if (sqlNode instanceof MixedSqlNode)
        {
            MixedSqlNode mixedSqlNode = (MixedSqlNode) sqlNode;
            @SuppressWarnings("unchecked")
            List<SqlNode> contents = (List<SqlNode>) ReflectUtil.getField(mixedSqlNode, "contents");
            for (SqlNode node : contents)
            {
                replaceSql(node);
            }
        }
    }
    
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource sqlSource)
    {
        Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource,
                SqlCommandType.SELECT);
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(ms.getKeyProperty());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        
        // 将返回值类型修改成 Integer
        ResultMap.Builder resultMapBuilder = new ResultMap.Builder(ms.getConfiguration(), "", Integer.class,
                new ArrayList<ResultMapping>());
        List<ResultMap> resultMapList = new ArrayList<ResultMap>();
        resultMapList.add(resultMapBuilder.build());
        builder.resultMaps(resultMapList);
        
        builder.cache(ms.getCache());
        MappedStatement newMs = builder.build();
        return newMs;
    }
}
