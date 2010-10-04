package org.fanhongtao.net.frame;

/**
 * @author Fan Hongtao
 * @created 2010-10-4
 */
public class MsgDirection
{
    /** 发送端的IP */
    private String srcIP = null;
    
    /** 发送端的Port */
    private int srcPort = 0;
    
    /** 接收端的IP */
    private String destIP = null;
    
    /** 接收端的Port */
    private int destPort = 0;
    
    public MsgDirection(String srcIP, int srcPort, String destIP, int destPort)
    {
        super();
        this.srcIP = srcIP;
        this.srcPort = srcPort;
        this.destIP = destIP;
        this.destPort = destPort;
    }
    
    public String getSrcIP()
    {
        return srcIP;
    }
    
    public int getSrcPort()
    {
        return srcPort;
    }
    
    public String getDestIP()
    {
        return destIP;
    }
    
    public int getDestPort()
    {
        return destPort;
    }
}
