package org.fanhongtao.mybatis.frame;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Fan Hongtao
 * @created 2010-8-17
 */
public class ServiceProxy<T> implements InvocationHandler
{
    private BaseService<T> delegate;
    
    public ServiceProxy(BaseService<T> baseService)
    {
        this.delegate = baseService;
    }
    
    public BaseService<T> getDelegate()
    {
        return delegate;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        Object result = null;
        if (delegate.isAutoCommit())
        {
            delegate.setSession(MyBatisConfig.getSession());
            MyBatisConfig.registerConnection(delegate.getSession().getConnection(), this);
            try
            {
                result = method.invoke(delegate, args);
                delegate.getSession().commit();
            }
            catch (Exception e)
            {
                throw new DbException(e);
            }
            finally
            {
                delegate.getSession().close();
            }
        }
        else
        {
            try
            {
                result = method.invoke(delegate, args);
            }
            catch (Exception e)
            {
                throw new DbException(e);
            }
        }
        return result;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T newProxy(Class<T> mapperClass)
    {
        ClassLoader classLoader = mapperClass.getClassLoader();
        Class[] interfaces = new Class[] { mapperClass };
        String mapperClassName = mapperClass.getName();
        String serviceClassName = mapperClassName.replaceFirst("iface", "service").replace("Mapper", "Service");
        BaseService service = null;
        try
        {
            Class c = classLoader.loadClass(serviceClassName);
            service = (BaseService) c.newInstance();
        }
        catch (Exception e)
        {
            throw new DbException(e);
        }
        InvocationHandler handler = new ServiceProxy<T>(service);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}
