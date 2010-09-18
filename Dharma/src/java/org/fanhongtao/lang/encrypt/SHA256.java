package org.fanhongtao.lang.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.fanhongtao.lang.StringUtils;


/**
 * @author Dharma
 * @created 2008-10-18
 */
public class SHA256
{
    private SHA256()
    {
    }

    /**
     * 获取一个字符串的SHA-256摘要
     * 
     * @param input
     * @return
     */
    public static String encrypt(String input)
    {
        String result = null;
        try
        {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(input.getBytes());
            result = StringUtils.bytesToHexString(sha256.digest());
        }
        catch (NoSuchAlgorithmException e)
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
        System.out.println(SHA256.encrypt("hello"));
    }

}
