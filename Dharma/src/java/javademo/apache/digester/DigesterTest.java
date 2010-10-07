package javademo.apache.digester;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javademo.apache.digester.bean.ServicesBean;

import org.apache.log4j.BasicConfigurator;
import org.fanhongtao.xml.DigesterUtils;
import org.xml.sax.SAXException;

/**
 * @author Fan Hongtao
 * @created 2010-10-7
 */
public class DigesterTest
{
    
    public static void main(String args[])
        throws IOException, SAXException
    {
        BasicConfigurator.configure();
        new DigesterTest().parse();
    }
    
    public void parse()
        throws IOException, SAXException
    {
        ServicesBean servicesBean = new ServicesBean();
        InputStream input = servicesBean.getClass().getResourceAsStream("test.xml");
        URL url = servicesBean.getClass().getResource("test_rules.xml");
        servicesBean = (ServicesBean) DigesterUtils.parse(servicesBean, input, url);
        System.out.println(servicesBean);
    }
    
}
