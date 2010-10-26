package org.fanhongtao.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import junit.framework.Assert;

/**
 * JUnit测试时的辅助类
 * @author Dharma
 * @created 2010-3-11
 */
public class JUnitHelper
{
    public static void equal(Object obj1, Object obj2)
    {
        Class<?> c1 = obj1.getClass();
        Class<?> c2 = obj2.getClass();
        Assert.assertEquals("Not the same class", c1.getName(), c2.getName());
        
        Method[] methods = c1.getMethods();
        for (Method m : methods)
        {
            String name = m.getName();
            if ((!name.startsWith("get")) || (m.getParameterTypes().length != 0))
            {
                continue;
            }
            
            try
            {
                Object v1 = m.invoke(obj1);
                Object v2 = m.invoke(obj2);
                Assert.assertEquals("Value not same for method: " + getMethodString(m), v1, v2);
            }
            catch (Exception e)
            {
                Assert.fail("Failed to call method: " + getMethodString(m) + ", reason: " + e.getMessage());
            }
        }
    }
    
    public static String getMethodString(Method method)
    {
        Class<?> returnType = method.getReturnType();
        String name = method.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(Modifier.toString(method.getModifiers())).append(' ');
        sb.append(returnType.getName()).append(' ');
        sb.append(name).append('(');
        Class<?>[] paramTypes = method.getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++)
        {
            if (i > 0)
            {
                sb.append(", ");
            }
            sb.append(paramTypes[i].getName());
        }
        sb.append(')');
        return sb.toString();
    }
}
