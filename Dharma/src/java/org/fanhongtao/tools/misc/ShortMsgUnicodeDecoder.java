package org.fanhongtao.tools.misc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

/**
 * 实现将短信的UNL数据中的 UNICODE 编码后的字符转换成可见的字符
 * @author Fan Hongtao
 * @created 2008-10-23
 */
public class ShortMsgUnicodeDecoder
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new ShortMsgUnicodeDecoder().run();
    }
    
    private void run()
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("input.unl"));
            String line;
            while ((line = in.readLine()) != null)
            {
                decode(line);
            }
            
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void decode(String line)
    {
        // String fields[] = line.split("\0x7c");
        // String fields[] = StringUtils.splitPreserveAllTokens(line, '|');
        String fields[] = StringUtils.split(line, '|');
        // System.out.println("Field's num is " + fields.length);
        for (int i = 0; i < fields.length; i++)
        {
            // System.out.println(fields[i]);
            String str = fields[i];
            if (!str.startsWith("``"))
            {
                System.out.println(str);
                continue;
            }
            
            // decode
            String input = str.substring(2, str.length() - 2);
            // System.out.println(str);
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < input.length(); j = j + 4)
            {
                String charStr = input.substring(j, j + 4);
                int intValue = Integer.parseInt(charStr, 16);
                char ch = (char) intValue;
                sb.append(ch);
            }
            System.out.println(sb.toString());
        }
    }
}
