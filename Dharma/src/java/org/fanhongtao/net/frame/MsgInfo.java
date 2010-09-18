package org.fanhongtao.net.frame;

import org.fanhongtao.lang.StringUtils;

/**
 * @author Dharma
 * @created 2008-10-20
 */
public class MsgInfo
{
    /**
     * 消息的编号
     */
    private int serial = 0;

    /**
     * 接收（发送）消息的时间
     */
    private long time = 0;

    /**
     * 接收或发送的消息
     */
    private byte[] msg = null;

    /**
     * 发送端的IP
     */
    private String srcIP = null;

    /**
     * 发送端的Port
     */
    private int srcPort = 0;

    /**
     * 接收端的IP
     */
    private String destIP = null;

    /**
     * 接收端的Port
     */
    private int destPort = 0;

    public MsgInfo()
    {
    }

    /**
     * @param msg
     * @param srcIP
     * @param srcPort
     * @param destIP
     * @param destPort
     */
    public MsgInfo(byte[] msg, String srcIP, int srcPort, String destIP, int destPort)
    {
        super();
        this.msg = msg;
        this.srcIP = srcIP;
        this.srcPort = srcPort;
        this.destIP = destIP;
        this.destPort = destPort;
    }

    public byte[] getMsg()
    {
        return msg;
    }

    public int getSerial()
    {
        return serial;
    }

    public void setSerial(int serial)
    {
        this.serial = serial;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public void setMsg(byte[] bytes)
    {
        this.msg = bytes;
    }

    public void setMsg(byte[] bytes, int startIndex, int length)
    {
        this.msg = new byte[length];
        System.arraycopy(bytes, startIndex, this.msg, 0, length);
    }

    public String getSrcIP()
    {
        return srcIP;
    }

    public void setSrcIP(String srcIP)
    {
        this.srcIP = srcIP;
    }

    public int getSrcPort()
    {
        return srcPort;
    }

    public void setSrcPort(int srcPort)
    {
        this.srcPort = srcPort;
    }

    public String getDestIP()
    {
        return destIP;
    }

    public void setDestIP(String destIP)
    {
        this.destIP = destIP;
    }

    public int getDestPort()
    {
        return destPort;
    }

    public void setDestPort(int destPort)
    {
        this.destPort = destPort;
    }

    public static MsgInfo getResponseMsg(MsgInfo req)
    {
        MsgInfo res = new MsgInfo();

        res.setSrcIP(req.getDestIP());
        res.setSrcPort(req.getDestPort());
        res.setDestIP(req.getSrcIP());
        res.setDestPort(req.getSrcPort());
        res.setTime(System.currentTimeMillis());

        return res;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("Src [");
        buf.append(srcIP);
        buf.append(':');
        buf.append(srcPort);
        buf.append("]");
        buf.append(StringUtils.CRLF);
        buf.append(StringUtils.toHexString(getMsg()));
        return buf.toString();
    }

}
