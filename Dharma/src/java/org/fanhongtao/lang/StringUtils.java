package org.fanhongtao.lang;

import java.util.Formatter;
import java.util.Locale;

/**
 * @author Dharma
 * @created 2008-10-18
 */
public class StringUtils
{
    /** 换行符 */
    public static final String CRLF = System.getProperty("line.separator");

    /** 十六进制的符号 */
    private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 将byte数组转换成十六进制的字符串
     * @param bytes
     * @return 转换后的字符串
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
     * 将字符串转换成类似于UltraEdit中的十六进制的形式显示
     * @param str 
     * @return 转换后的字符串
     */
    public static String toHexString(String str)
    {
        byte[] bytes = str.getBytes();
        return toHexString(bytes);
    }

    /**
     * 将byte数据转换成类似于UltraEdit中的十六进制的形式显示
     * @param bytes 需要转换的数组
     * @return 转换后的字符串
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
            // 写序号
            format.format("%08Xh:", i);

            // 写十六进制的码流
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

            // 补空格及分隔符
            for (; j < INCREMENT; j++)
            {
                sb.append("   ");
            }
            sb.append(" ; ");

            // 写原始的字符
            sb.append(new String(bytes, i, count));

            sb.append(CRLF);
        }

        return sb.toString();
    }

    public static void main(String args[])
    {
        // 全是英文字符的情况
        System.out.println(StringUtils.toHexString("hello, world. this is a test, and hope you can enjoy"));

        // 全是汉字的情况
        System.out.println(StringUtils.toHexString("你好，这是一个测试程序"));

        // 测试在汉字中间换行的情况
        System.out.println(StringUtils.toHexString("hello, world. t你好his is a test, and hope you can enjoy"));
    }
}
