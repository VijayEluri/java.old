package org.fanhongtao.lang;

import java.io.UnsupportedEncodingException;

public final class StringEncoder
{

    public static String encode(String input, String destCharSet)
    {
        String result = null;
        try
        {
            result = new String(input.getBytes(), destCharSet);
        }
        catch (UnsupportedEncodingException e)
        {
            result = input;
        }
        return result;
    }

    public static String decode(String input, String srcCharSet)
    {
        String result = null;
        try
        {
            result = new String(input.getBytes(srcCharSet));
        }
        catch (UnsupportedEncodingException e)
        {
            result = input;
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String a = "ƒ„∫√£¨≤‚ ‘";
        String charSet = "ISO-8859-1";
        String b = StringEncoder.encode(a, charSet);
        System.out.println(b);
        a = StringEncoder.decode(b, charSet);
        System.out.println(a);
    }

}
