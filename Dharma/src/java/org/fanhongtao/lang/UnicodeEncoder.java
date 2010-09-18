package org.fanhongtao.lang;

public class UnicodeEncoder
{
    /**
     * 将一个Unicode字符串转换成小写的Hex形式的字符串。<br>
     * 如：将"A"转换成"0041", "你"转换成"4f60"
     * 
     * @param unicodeStr Unicode字符串
     * @return 对应的Hex形式的字符串
     */
    public static String encode(String unicodeStr)
    {
        String fixSring[] = { "", "000", "00", "0" };
        char inputChars[] = unicodeStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inputChars.length; i++)
        {
            char ch = inputChars[i];
            int intValue = (int) ch;
            String charStr = Integer.toHexString(intValue);
            if (charStr.length() < 4)
            {
                sb.append(fixSring[charStr.length()]);
            }
            sb.append(charStr);
        }

        return sb.toString();
    }

    /**
     * 将Hex形式的字符串转换成其对应的Unicode串
     * 
     * @param hexStr Hex形式的字符串
     * @return 对应的Unicode字符串
     */
    public static String decode(String hexStr)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hexStr.length(); i = i + 4)
        {
            String charStr = hexStr.substring(i, i + 4);
            int intValue = Integer.parseInt(charStr, 16);
            char ch = (char) intValue;
            sb.append(ch);
        }
        return sb.toString();
    }

}
