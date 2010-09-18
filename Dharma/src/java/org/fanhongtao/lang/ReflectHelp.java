package org.fanhongtao.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Dharma
 * @created 2010-3-7
 */
public class ReflectHelp
{
    /**
    * 通过构造器取得实例
    * @param className 类的全限定名
    * @param intArgsClass 构造函数的参数类型
    * @param intArgs 构造函数的参数值
    * @return Object 构造函数
    */
    @SuppressWarnings("unchecked")
    public static Object getObjectByConstructor(String className, Class[] intArgsClass, Object[] intArgs)
    {

        Object returnObj = null;
        try
        {
            Class classType = Class.forName(className);
            Constructor constructor = classType.getDeclaredConstructor(intArgsClass); // 找到指定的构造方法
            constructor.setAccessible(true);// 设置安全检查，访问私有构造函数必须
            returnObj = constructor.newInstance(intArgs);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return returnObj;
    }

    /**
    * 修改成员变量的值
    * @param Object 修改对象
    * @param filedName 指定成员变量名
    * @param filedValue 修改的值
    */
    @SuppressWarnings("unchecked")
    public static void modifyFileValue(Object object, String filedName, String filedValue)
    {
        Class classType = object.getClass();
        Field fild = null;
        try
        {
            fild = classType.getDeclaredField(filedName);
            fild.setAccessible(true);// 设置安全检查，访问私有成员变量必须
            fild.set(object, filedValue);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** */
    /**
    * 访问类成员变量
    * @param Object 访问对象
    * @param filedName 指定成员变量名
    * @return Object 取得的成员变量的值
    * */
    @SuppressWarnings("unchecked")
    public static Object getFileValue(Object object, String filedName)
    {
        Class classType = object.getClass();
        Field fild = null;
        Object fildValue = null;
        try
        {
            fild = classType.getDeclaredField(filedName);
            fild.setAccessible(true);// 设置安全检查，访问私有成员变量必须
            fildValue = fild.get(object);

        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return fildValue;
    }

    /**
    * 调用类的方法，包括私有
    * @param Object 访问对象
    * @param methodName 指定成员变量名
    * @param type 方法参数类型
    * @param value 方法参数指
    * @return Object 方法的返回结果对象
    * */
    @SuppressWarnings("unchecked")
    public static Object useMethod(Object object, String methodName, Class[] type, Class[] value)
    {
        Class classType = object.getClass();
        Method method = null;
        Object fildValue = null;
        try
        {
            method = classType.getDeclaredMethod(methodName, type);
            method.setAccessible(true);// 设置安全检查，访问私有成员方法必须
            fildValue = method.invoke(object, (Object[]) value);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return fildValue;
    }

}
