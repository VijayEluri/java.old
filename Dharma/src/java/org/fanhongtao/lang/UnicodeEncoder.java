package org.fanhongtao.lang;

public class UnicodeEncoder
{
    /**
     * ��һ��Unicode�ַ���ת����Сд��Hex��ʽ���ַ�����<br>
     * �磺��"A"ת����"0041", "��"ת����"4f60"
     * 
     * @param unicodeStr Unicode�ַ���
     * @return ��Ӧ��Hex��ʽ���ַ���
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
     * ��Hex��ʽ���ַ���ת�������Ӧ��Unicode��
     * 
     * @param hexStr Hex��ʽ���ַ���
     * @return ��Ӧ��Unicode�ַ���
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
