package org.fanhongtao.lang;

import java.util.Formatter;
import java.util.Locale;

/**
 * @author Dharma
 * @created 2008-10-18
 */
public class StringUtils
{
    /** ���з� */
    public static final String CRLF = System.getProperty("line.separator");

    /** ʮ�����Ƶķ��� */
    private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * ��byte����ת����ʮ�����Ƶ��ַ���
     * @param bytes
     * @return ת������ַ���
     */
    public static String bytesToHexString(byte[] bytes)
    {
        StringBuffer sb = new StringBuffer(1024);
        for (int i = 0; i < bytes.length; i++)
        {
            int n = bytes[i];
            if (n < 0)
                n += 256;
            sb.append(HEX[n / 16]);
            sb.append(HEX[n % 16]);
        }
        return sb.toString();
    }

    /**
     * ���ַ���ת����������UltraEdit�е�ʮ�����Ƶ���ʽ��ʾ
     * @param str 
     * @return ת������ַ���
     */
    public static String toHexString(String str)
    {
        byte[] bytes = str.getBytes();
        return toHexString(bytes);
    }

    /**
     * ��byte����ת����������UltraEdit�е�ʮ�����Ƶ���ʽ��ʾ
     * @param bytes ��Ҫת��������
     * @return ת������ַ���
     */
    public static String toHexString(byte[] bytes)
    {
        final int INCREMENT = 0x10;
        StringBuffer sb = new StringBuffer(1024);
        Formatter format = new Formatter(sb, Locale.US);

        byte b;
        int j;
        int count = 0;
        for (int i = 0; i < bytes.length; i += INCREMENT)
        {
            // д���
            format.format("%08Xh:", i);

            // дʮ�����Ƶ�����
            for (j = 0; j < INCREMENT && i + j < bytes.length; j++)
            {
                b = bytes[j + i];
                if (j != 8)
                {
                    format.format(" %02X", b);
                }
                else
                {
                    format.format(" %02X", b);
                }
            }
            count = j;

            // ���ո񼰷ָ���
            for (; j < INCREMENT; j++)
            {
                sb.append("   ");
            }
            sb.append(" ; ");

            // дԭʼ���ַ�
            sb.append(new String(bytes, i, count));

            sb.append(CRLF);
        }

        return sb.toString();
    }

    public static void main(String args[])
    {
        // ȫ��Ӣ���ַ������
        System.out.println(StringUtils.toHexString("hello, world. this is a test, and hope you can enjoy"));

        // ȫ�Ǻ��ֵ����
        System.out.println(StringUtils.toHexString("��ã�����һ�����Գ���"));

        // �����ں����м任�е����
        System.out.println(StringUtils.toHexString("hello, world. t���his is a test, and hope you can enjoy"));
    }
}
