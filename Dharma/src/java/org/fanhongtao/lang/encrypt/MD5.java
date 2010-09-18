package org.fanhongtao.lang.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.fanhongtao.lang.StringUtils;


/**
 * @author Dharma
 * @created 2008-10-18
 * @deprecated MD5算法不安全，建议使用SHA-256
 */
public class MD5
{
    private MD5()
    {
    }

    /**
     * 获取一个字符串的MD5摘要
     * 
     * @param input
     * @return
     */
    public static String md5Str(String input)
    {
        String md5Result = null;
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            md5Result = StringUtils.bytesToHexString(md5.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            md5Result = input;
        }
        return md5Result;
    }
}
