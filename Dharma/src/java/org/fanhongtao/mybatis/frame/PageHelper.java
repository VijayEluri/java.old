package org.fanhongtao.mybatis.frame;

import org.apache.ibatis.session.RowBounds;

/**
 * @author Fan Hongtao
 * @created 2010-8-27
 */
final public class PageHelper
{
    /** total record number */
    private int recordNum;
    
    /** record number in each page */
    private int pageSize;
    
    /** Max page, maxPage = recordNum / pageSize */
    private int maxPage;
    
    /** Current page, start from 1 */
    private int currPage;
    
    public PageHelper(int pageSize)
    {
        this(Integer.MAX_VALUE, pageSize);
    }
    
    public PageHelper(int recordNum, int pageSize)
    {
        this.pageSize = pageSize;
        this.currPage = 1;
        setRecordNum(recordNum);
    }
    
    public int getRecordNum()
    {
        return recordNum;
    }
    
    public void setRecordNum(int recordNum)
    {
        this.recordNum = recordNum;
        if (recordNum % pageSize == 0)
        {
            this.maxPage = recordNum / pageSize;
        }
        else
        {
            this.maxPage = recordNum / pageSize + 1;
        }
        setCurrPage(currPage);
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getMaxPage()
    {
        return maxPage;
    }
    
    // Do no provide a method to set maxPage
    // public void setMaxPage(int maxPage)
    // {
    // this.maxPage = maxPage;
    // }
    
    public int getCurrPage()
    {
        return currPage;
    }
    
    /**
     * Jump to the specified page<br>
     * If the new page is less than 1, set it to 1<br>
     * If the new page is great than maxPage, set it to maxPage<br>
     * @param currPage new page number
     */
    public void setCurrPage(int currPage)
    {
        if (currPage < 1)
        {
            this.currPage = 1;
        }
        else if (currPage > maxPage)
        {
            this.currPage = maxPage;
        }
        else
        {
            this.currPage = currPage;
        }
    }
    
    public int nextPage()
    {
        if (currPage < maxPage)
        {
            currPage++;
        }
        return currPage;
    }
    
    public int prevPage()
    {
        if (currPage > 1)
        {
            currPage--;
        }
        return currPage;
    }
    
    /**
     * If current page is the first page
     * @return true: Yes, it's the first page; false: no, it's not
     */
    public boolean isFirstPage()
    {
        return (currPage == 1);
    }
    
    /**
     * If current page is the last page
     * @return true: Yes, it's the last page; false: no, it's not
     */
    public boolean isLastPage()
    {
        return (currPage == maxPage);
    }
    
    public RowBounds getRowBounds()
    {
        return new RowBounds(currPage * pageSize, pageSize);
    }
}
