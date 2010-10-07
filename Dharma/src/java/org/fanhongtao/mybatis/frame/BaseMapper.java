package org.fanhongtao.mybatis.frame;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * @author Fan Hongtao
 * @created 2010-08-17
 */
public interface BaseMapper<T>
{
    /**
     * 获取满足上一次分页查询条件的记录总数
     * @return 满足上一次分页查询条件的记录总数
     */
    public int getRecordNum();
    
    /**
     * 设置满足上一次分页查询条件的记录总数<br>
     * 本方法仅供框架内部使用
     * @param recordNum 满足上一次分页查询条件的记录总数
     */
    void setRecordNum(int recordNum);
    
    /**
     * 查询表中的所有记录
     * @return 表中的记录, List的Size为0表示没有记录
     */
    public List<T> queryAll();
    
    /**
     * 查询表中的所有记录
     * @param rowBounds 所要查询的范围
     * @return 表中的记录, List的Size为0表示没有记录
     */
    public List<T> queryAll(RowBounds rowBounds);
}
