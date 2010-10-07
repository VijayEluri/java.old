package org.fanhongtao.mybatis.frame;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 * @author Fan Hongtao
 * @created 2010-8-17
 */
public abstract class BaseService<T> implements BaseMapper<T>
{
    /** 是否自动提交事务，缺省为自动提交 */
    private boolean autoCommit = true;
    
    /**  */
    private SqlSession session = null;
    
    /** 满足上一次分页查询条件的记录总数，用于实现辅助分页 */
    private int recordNum = 0;
    
    public BaseService()
    {
    }
    
    public BaseService(SqlSession session)
    {
        this.session = session;
        this.autoCommit = false;
    }
    
    public boolean isAutoCommit()
    {
        return autoCommit;
    }
    
    public void setAutoCommit(boolean autoCommit)
    {
        this.autoCommit = autoCommit;
    }
    
    public SqlSession getSession()
    {
        return session;
    }
    
    abstract BaseMapper<T> getMapper();
    
    public void setSession(SqlSession session)
    {
        this.session = session;
    }
    
    public int getRecordNum()
    {
        return recordNum;
    }
    
    public void setRecordNum(int recordNum)
    {
        this.recordNum = recordNum;
    }
    
    public List<T> queryAll()
    {
        return getMapper().queryAll();
    }
    
    public List<T> queryAll(RowBounds rowBounds)
    {
        return getMapper().queryAll(rowBounds);
    }
}
