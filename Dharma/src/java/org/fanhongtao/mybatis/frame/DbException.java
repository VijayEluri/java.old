package org.fanhongtao.mybatis.frame;


/**
 * @author Fan Hongtao
 * @created 2010-8-17
 */
public class DbException extends RuntimeException
{
    private static final long serialVersionUID = 6851784944341450009L;

    public DbException(String message)
    {
        super(message);
    }

    public DbException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DbException(Throwable cause)
    {
        super(cause);
    }
}
