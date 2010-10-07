package javademo.apache.digester.bean;

import java.util.ArrayList;

/**
 * @author Fan Hongtao
 * @created 2010-10-7
 */
public class ServicesBean
{
    private ArrayList<RequestBean> requestList = new ArrayList<RequestBean>();
    
    public void addRequest(RequestBean request)
    {
        requestList.add(request);
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        int i = 0;
        String CRLF = System.getProperty("line.separator");
        for (RequestBean req : requestList)
        {
            buf.append("Request ").append(++i);
            buf.append(CRLF);
            buf.append("\tinput: ").append(req.getInput());
            buf.append(CRLF);
            buf.append("\tservice: ").append(req.getService());
            buf.append(CRLF);
        }
        return buf.toString();
    }
    
}
