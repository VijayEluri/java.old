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
    * ͨ��������ȡ��ʵ��
    * @param className ���ȫ�޶���
    * @param intArgsClass ���캯���Ĳ�������
    * @param intArgs ���캯���Ĳ���ֵ
    * @return Object ���캯��
    */
    @SuppressWarnings("unchecked")
    public static Object getObjectByConstructor(String className, Class[] intArgsClass, Object[] intArgs)
    {

        Object returnObj = null;
        try
        {
            Class classType = Class.forName(className);
            Constructor constructor = classType.getDeclaredConstructor(intArgsClass); // �ҵ�ָ���Ĺ��췽��
            constructor.setAccessible(true);// ���ð�ȫ��飬����˽�й��캯������
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
    * �޸ĳ�Ա������ֵ
    * @param Object �޸Ķ���
    * @param filedName ָ����Ա������
    * @param filedValue �޸ĵ�ֵ
    */
    @SuppressWarnings("unchecked")
    public static void modifyFileValue(Object object, String filedName, String filedValue)
    {
        Class classType = object.getClass();
        Field fild = null;
        try
        {
            fild = classType.getDeclaredField(filedName);
            fild.setAccessible(true);// ���ð�ȫ��飬����˽�г�Ա��������
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
    * �������Ա����
    * @param Object ���ʶ���
    * @param filedName ָ����Ա������
    * @return Object ȡ�õĳ�Ա������ֵ
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
            fild.setAccessible(true);// ���ð�ȫ��飬����˽�г�Ա��������
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
    * ������ķ���������˽��
    * @param Object ���ʶ���
    * @param methodName ָ����Ա������
    * @param type ������������
    * @param value ��������ָ
    * @return Object �����ķ��ؽ������
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
            method.setAccessible(true);// ���ð�ȫ��飬����˽�г�Ա��������
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
