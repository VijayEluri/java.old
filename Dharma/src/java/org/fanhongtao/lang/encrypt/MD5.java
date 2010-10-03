package org.fanhongtao.lang.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.fanhongtao.lang.StringUtils;

/**
 * @author Dharma
 * @created 2008-10-18
 * @deprecated MD5 is not safe enough, please use SHA-256
 */
public class MD5
{
    private MD5()
    {
    }
    
    /**
     * Get md5 digest of a string
     * @param input
     * @return md5 digest
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
