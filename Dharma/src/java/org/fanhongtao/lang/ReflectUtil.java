package org.fanhongtao.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Dharma
 * @created 2010-3-7
 */
public class ReflectUtil
{
    /**
    * Get an instance of object from it's constructor
    * @param className The full class name 
    * @param argsClass The argument(s) type of the constructor
    * @param argsValue The argument(s) value of the constructor
    * @return Object The object created
    */
    public static Object getObjectByConstructor(String className, Class<?>[] argsClass, Object[] argsValue)
    {
        Object returnObj = null;
        try
        {
            Class<?> classType = Class.forName(className);
            Constructor<?> constructor = classType.getDeclaredConstructor(argsClass);
            boolean oldAccess = constructor.isAccessible();
            constructor.setAccessible(true);
            returnObj = constructor.newInstance(argsValue);
            constructor.setAccessible(oldAccess);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return returnObj;
    }
    
    /**
     * Set the value of a field to <i>value</i>.
     * @param obj The object try to access
     * @param fieldName The name of the field
     * @param value New value for the field 
     */
    public static void setField(Object obj, String fieldName, Object value)
    {
        try
        {
            Field field = obj.getClass().getDeclaredField(fieldName);
            boolean oldAccess = field.isAccessible();
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(oldAccess);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Get the value of a field
     * @param object The object try to access
     * @param fieldName The name of the field
     * @return Value of the field.
     */
    public static Object getField(Object object, String filedName)
    {
        Class<?> classType = object.getClass();
        Object value = null;
        try
        {
            Field field = classType.getDeclaredField(filedName);
            boolean oldAccess = field.isAccessible();
            field.setAccessible(true);
            value = field.get(object);
            field.setAccessible(oldAccess);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }
    
    /**
     * Get the value of a field
     * @param clazz The class (or parent class) of object
     * @param obj The object try to access
     * @param fieldName The name of the field. The field must defined in class.
     * @return Value of the field.
     */
    public static Object getField(Class<?> clazz, Object obj, String fieldName)
    {
        Object value = null;
        try
        {
            Field field = clazz.getDeclaredField(fieldName);
            boolean oldAccess = field.isAccessible();
            field.setAccessible(true);
            value = field.get(obj);
            field.setAccessible(oldAccess);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }
    
    /**
    * Call the method of an object.
    * @param Object The object try to visit.
    * @param methodName Method's name
    * @param type The argument(s) type of the method
    * @param value The argument(s) value of the method
    * @return Object The return value of method
    * */
    public static Object callMethod(Object object, String methodName, Class<?>[] type, Class<?>[] value)
    {
        Class<?> classType = object.getClass();
        Method method = null;
        Object resultValue = null;
        try
        {
            method = classType.getDeclaredMethod(methodName, type);
            boolean oldAccess = method.isAccessible();
            method.setAccessible(true);
            resultValue = method.invoke(object, (Object[]) value);
            method.setAccessible(oldAccess);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return resultValue;
    }
    
}
