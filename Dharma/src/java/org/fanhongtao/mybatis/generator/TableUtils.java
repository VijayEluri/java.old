package org.fanhongtao.mybatis.generator;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.fanhongtao.mybatis.frame.MyBatisConfig;


/**
 * @author Fan Hongtao
 * @created 2010-8-20
 */
public class TableUtils
{
    @SuppressWarnings("rawtypes")
    private Class clazz;
    
    private Map<String, String> fieldMap = new HashMap<String, String>();
    
    @SuppressWarnings("rawtypes")
    public TableUtils(Class c)
    {
        this.clazz = c;
        Field[] classFields = c.getDeclaredFields();
        for (Field field : classFields)
        {
            String fieldName = field.getName();
            fieldMap.put(fieldName.toLowerCase(), fieldName);
        }
    }
    
    public Table parseTable()
    {
        String tableName = clazz.getSimpleName();
        Table table = new Table();
        table.setName(tableName);
        
        SqlSession session = MyBatisConfig.getSession();
        Connection conn = session.getConnection();
        try
        {
            table.setColumnList(getColumnList(conn, tableName));
            table.setIndexList(getIndexList(conn, tableName));
            table.setPrimaryKey(getPrimaryKey(table));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            session.close();
        }
        return table;
    }
    
    private List<Column> getColumnList(Connection conn, String tableName)
        throws SQLException
    {
        List<Column> columnList = new ArrayList<Column>();
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from " + tableName + " where 1 != 1");
        ResultSetMetaData rsm = resultSet.getMetaData();
        for (int i = 0, n = rsm.getColumnCount(); i < n; i++)
        {
            Column column = new Column();
            String columnName = rsm.getColumnName(i + 1);
            String tmp = fieldMap.get(columnName.toLowerCase());
            if (null != tmp)
            {
                column.setName(tmp);
                column.setType(rsm.getColumnTypeName(i + 1));
                columnList.add(column);
            }
        }
        return columnList;
    }
    
    private List<Index> getIndexList(Connection conn, String tableName)
        throws SQLException
    {
        List<Index> indexList = new ArrayList<Index>();
        ResultSet resultSet = conn.getMetaData().getIndexInfo(null, null, tableName.toUpperCase(), false, false);
        Index index = null;
        String oldName = null;
        while (resultSet.next())
        {
            String indexName = resultSet.getString("INDEX_NAME");
            if (!indexName.equals(oldName))
            {
                oldName = indexName;
                if (null != index)
                {
                    indexList.add(index);
                }
                index = new Index();
                index.setName(indexName);
                boolean unique = !resultSet.getBoolean("NON_UNIQUE");
                index.setUnique(unique);
                if (indexName.startsWith("PRIMARY_KEY"))
                {
                    index.setPrimaryKey(true);
                }
            }
            
            String columnName = fieldMap.get(resultSet.getString("COLUMN_NAME").toLowerCase());
            if (null != columnName)
            {
                index.addColumn(columnName);
            }
        }
        
        if ((null != index) && (index.getColumnList().size() > 0))
        {
            indexList.add(index);
        }
        return indexList;
    }
    
    private Index getPrimaryKey(Table table)
    {
        for (Index index : table.getIndexList())
        {
            if (index.isPrimaryKey())
            {
                return index;
            }
        }
        return null;
    }
}
