package org.fanhongtao.mybatis.frame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author Fan Hongtao
 * @created 2010-8-20
 */
public class MyBatisConfig
{
    private static String propFileName = "database.properties";
    
    private static String cfgFileName = "org/fanhongtao/mybatis/frame/MapperConfig.xml";
    
    private static SqlSessionFactory sqlSessionFactory = null;
    
    @SuppressWarnings("rawtypes")
    private static Map<ServiceProxy, Integer> serviceMap = new HashMap<ServiceProxy, Integer>();
    
    @SuppressWarnings("rawtypes")
    private static Map<Connection, ServiceProxy> sessionMap = new HashMap<Connection, ServiceProxy>();
    
    private static Map<MappedStatement, MappedStatement> msMap = new HashMap<MappedStatement, MappedStatement>();
    
    private MyBatisConfig()
    {
    }
    
    private static synchronized void init()
    {
        if (null != sqlSessionFactory)
        {
            return;
        }
        
        FileInputStream fin = null;
        Reader reader = null;
        try
        {
            Properties properties = new Properties();
            if (null != propFileName)
            {
                fin = new FileInputStream(propFileName);
                properties.load(fin);
            }
            
            reader = Resources.getResourceAsReader(cfgFileName);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
        }
        catch (Exception t)
        {
            throw new RuntimeException(t);
        }
        finally
        {
            if (null != fin)
            {
                try
                {
                    fin.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void setPropFileName(String propFileName)
    {
        MyBatisConfig.propFileName = propFileName;
    }
    
    public static void setCfgFileName(String cfgFileName)
    {
        MyBatisConfig.cfgFileName = cfgFileName;
    }
    
    public static SqlSession getSession()
    {
        if (null == sqlSessionFactory)
        {
            init();
        }
        return sqlSessionFactory.openSession();
    }
    
    @SuppressWarnings("rawtypes")
    static void registerProxy(ServiceProxy proxy)
    {
        synchronized (serviceMap)
        {
            serviceMap.put(proxy, 0);
        }
    }
    
    @SuppressWarnings("rawtypes")
    static void deleteroxy(ServiceProxy proxy)
    {
        synchronized (serviceMap)
        {
            serviceMap.remove(proxy);
        }
    }
    
    @SuppressWarnings("rawtypes")
    static void registerConnection(Connection connection, ServiceProxy proxy)
    {
        boolean hasKey = false;
        synchronized (serviceMap)
        {
            hasKey = serviceMap.containsKey(proxy);
        }
        
        if (hasKey)
        {
            synchronized (sessionMap)
            {
                sessionMap.put(connection, proxy);
            }
        }
    }
    
    static boolean hasConnection(Connection connection)
    {
        synchronized (sessionMap)
        {
            return sessionMap.containsKey(connection);
        }
    }
    
    @SuppressWarnings("rawtypes")
    static void setRecordNum(Connection connection, int recordNum)
    {
        ServiceProxy proxy = null;
        synchronized (sessionMap)
        {
            proxy = sessionMap.remove(connection);
        }
        
        if (null != proxy)
        {
            proxy.getDelegate().setRecordNum(recordNum);
        }
    }
    
    public static void registerQueryCountStatement(MappedStatement ms, MappedStatement queryCountMs)
    {
        synchronized (msMap)
        {
            msMap.put(ms, queryCountMs);
        }
    }
    
    public static MappedStatement getQueryCountStatement(MappedStatement ms)
    {
        synchronized (msMap)
        {
            return msMap.get(ms);
        }
    }
}
