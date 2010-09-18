package org.fanhongtao.utils;

/**
 * 生成唯一的编号序号生成器，所生成的序号为 “指定的前缀 + 序号”，序号从0开始，上限为<br>
 * 模仿Apache的axiom中的UUIDGenerator而写成。
 * 
 * @author Dharma
 * @created 2008-10-25
 */
public class UIDGenerator
{
    /** ID的前缀 */
    private static String baseUID = null;

    /** ID的序号 */
    private static long incrementingValue = 0;

    /** ID序号的最大值 */
    private static long maxValue = 0;

    /** 字符形式的ID序号最大值的应长度 */
    private static int maxValueLen = 0;

    /** 用于序号前补0的字符中数组 */
    private static String ZEROS[] = null;

    static
    {
        UIDGenerator.init("", 9999);
    }

    /**
     * 初始化序号生成器
     * @param baseUID 序号前缀
     * @param maxValue 序号的最大值
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
     * 返回一个唯一的ID。ID长度固定为: baseUID长度 + 13（系统时间的长度） + maxValue长度
     * 当序号长度小于maxValue的长度时，会在序号的前面补0 。
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
         * System.currentTimeMillis()返回的是从1970-01-01开始的毫秒数。 
         * 当返回值为 9999999999xxx(10个9)时，共是 9999999999/3600/24/365 = 317 年。 
         * 即是 公元 2287 年之后的事情了。所以可以固定认为是13位数。
         * 
         * 基于性能考虑，不使用 String.format的形式，而是手工拼接字符串。
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
