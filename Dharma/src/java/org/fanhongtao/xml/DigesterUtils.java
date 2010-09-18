package org.fanhongtao.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.fanhongtao.log.RunLogger;
import org.xml.sax.SAXException;


/**
 * @author Dharma
 * @created 2009-6-3
 */
public final class DigesterUtils
{

    /**
     * ��XML�ļ��ж�ȡ����
     * @param obj �������ݵĶ���
     * @param inputUrl ��Ҫ��ȡ��XML�ļ���Ӧ��URL
     * @param rulesUrl XML�ļ���ȡ�����Ӧ��URL
     * @return ��ȡ���ݺ��Ӧ�Ķ��������ȡʧ�ܣ��򷵻�null
     */
    public static Object parse(Object obj, URL inputUrl, URL rulesUrl)
    {
        // Digester 1.8 ��Ҫ�Ĺرյ���־
        Logger.getLogger("org.apache.commons.digester.Digester").setLevel(Level.OFF);
        Logger.getLogger("org.apache.commons.beanutils.BeanUtils").setLevel(Level.OFF);
        Logger.getLogger("org.apache.commons.beanutils.ConvertUtils").setLevel(Level.OFF);

        // Digester 2.0 ��������Ҫ�Ĺرյ���־
        Logger.getLogger("org.apache.commons.beanutils.converters").setLevel(Level.OFF);

        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(inputUrl.getFile());
            obj = parse(obj, inputStream, rulesUrl);
        }
        catch (Exception e)
        {
            RunLogger.warn("Failed to read XML", e);
        }
        finally
        {
            if (null != inputStream)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        return obj;
    }

    public static Object parse(Object obj, InputStream inputStream, URL rulesUrl) throws IOException, SAXException
    {
        Digester digester = DigesterLoader.createDigester(rulesUrl);
        digester.push(obj);
        digester.setValidating(false);
        return digester.parse(inputStream);
    }
}
