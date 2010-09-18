package org.fanhongtao.utils;

/**
 * ����Ψһ�ı������������������ɵ����Ϊ ��ָ����ǰ׺ + ��š�����Ŵ�0��ʼ������Ϊ<br>
 * ģ��Apache��axiom�е�UUIDGenerator��д�ɡ�
 * 
 * @author Dharma
 * @created 2008-10-25
 */
public class UIDGenerator
{
    /** ID��ǰ׺ */
    private static String baseUID = null;

    /** ID����� */
    private static long incrementingValue = 0;

    /** ID��ŵ����ֵ */
    private static long maxValue = 0;

    /** �ַ���ʽ��ID������ֵ��Ӧ���� */
    private static int maxValueLen = 0;

    /** �������ǰ��0���ַ������� */
    private static String ZEROS[] = null;

    static
    {
        UIDGenerator.init("", 9999);
    }

    /**
     * ��ʼ�����������
     * @param baseUID ���ǰ׺
     * @param maxValue ��ŵ����ֵ
     */
    public static void init(String baseUID, long maxValue)
    {
        UIDGenerator.baseUID = baseUID;
        UIDGenerator.maxValue = maxValue;
        UIDGenerator.maxValueLen = String.valueOf(maxValue).length();

        StringBuffer buf = new StringBuffer();
        ZEROS = new String[UIDGenerator.maxValueLen];
        for (int i = 0; i < UIDGenerator.maxValueLen; i++)
        {
            ZEROS[i] = buf.toString();
            buf.append('0');
        }
    }

    /**
     * ����һ��Ψһ��ID��ID���ȹ̶�Ϊ: baseUID���� + 13��ϵͳʱ��ĳ��ȣ� + maxValue����
     * ����ų���С��maxValue�ĳ���ʱ��������ŵ�ǰ�油0 ��
     * 
     * @return string
     */
    public static synchronized String getUID()
    {
        if (++incrementingValue >= maxValue)
        {
            incrementingValue = 0;
        }

        /*
         * System.currentTimeMillis()���ص��Ǵ�1970-01-01��ʼ�ĺ������� 
         * ������ֵΪ 9999999999xxx(10��9)ʱ������ 9999999999/3600/24/365 = 317 �ꡣ 
         * ���� ��Ԫ 2287 ��֮��������ˡ����Կ��Թ̶���Ϊ��13λ����
         * 
         * �������ܿ��ǣ���ʹ�� String.format����ʽ�������ֹ�ƴ���ַ�����
         */
        StringBuffer buf = new StringBuffer(64);
        buf.append(baseUID);
        buf.append(System.currentTimeMillis());
        String s = String.valueOf(incrementingValue);
        buf.append(ZEROS[maxValueLen - s.length()]);
        buf.append(s);

        return buf.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        for (int i = 0; i < 150; i++)
        {
            System.out.println(UIDGenerator.getUID());
        }

    }

}
