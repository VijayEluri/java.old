package org.fanhongtao.net.frame;

import org.fanhongtao.lang.StringUtils;

/**
 * @author Dharma
 * @created 2008-10-20
 */
public class MsgInfo
{
    /** 消息的编号  */
    private int serial = 0;
    
    /** 接收（发送）消息的时间   */
    private long time = 0;
    
    /** 接收或发送的消息  */
    private byte[] msg = null;
    
    private MsgDirection direction;
    
    public MsgInfo()
    {
    }
    
    public MsgInfo(byte[] msg, MsgDirection direction)
    {
        super();
        this.msg = msg;
        this.direction = direction;
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
        return direction.getSrcIP();
    }
    
    public int getSrcPort()
    {
        return direction.getSrcPort();
    }
    
    public String getDestIP()
    {
        return direction.getDestIP();
    }
    
    public int getDestPort()
    {
        return direction.getDestPort();
    }
    
    public void setDirection(MsgDirection direction)
    {
        this.direction = direction;
    }
    
    public static MsgInfo getResponseMsg(MsgDirection direction)
    {
        MsgInfo res = new MsgInfo();
        res.setDirection(direction);
        res.setTime(System.currentTimeMillis());
        
        return res;
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("Src [");
        buf.append(getSrcIP());
        buf.append(':');
        buf.append(getSrcPort());
        buf.append("]");
        buf.append(StringUtils.CRLF);
        buf.append(StringUtils.toHexString(getMsg()));
        return buf.toString();
    }
    
}
