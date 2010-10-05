package algorithm.bsf.maze;

/**
 * @author Fan Hongtao
 * @created 2009-3-14
 */
public class Node implements Cloneable
{
    /**
     * 结点的行
     */
    private int x;
    
    /**
     * 结点的列
     */
    private int y;
    
    public static final int PATH_NUM = 4;
    
    public static final Node[] PATH = new Node[PATH_NUM];
    static
    {
        PATH[0] = new Node(-1, 0); // 上
        PATH[1] = new Node(1, 0); // 下
        PATH[2] = new Node(0, -1); // 左
        PATH[3] = new Node(0, 1); // 右
    }
    
    public Node(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
    }
    
    /**
     * 重载clone只为了修改方法的作用域及返回值类型，并不需要新增特殊处理
     */
    @Override
    public Node clone()
    {
        try
        {
            return (Node) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            return null; // 不可能的情况
        }
    }
    
    /**
     * 判断两个结点是否在同一位置
     * @param obj 需要比较的结点
     * @return true: 在同一位置; false: 不在同一位置 
     */
    @Override
    public boolean equals(Object obj)
    {
        Node objNode = (Node) obj;
        return ((this.x == objNode.x) && (this.y == objNode.y));
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append('[').append(x - 1).append(','); // -1 是因为在原始地图中增加了围墙
        buf.append(y - 1).append(']');
        return buf.toString();
    }
    
    /**
     * 移动到下一个位置
     * @param i : 移动的方向编号
     * @return 表示下一位置对应结点
     */
    public Node move(int i)
    {
        return new Node(this.x + PATH[i].x, this.y + PATH[i].y);
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
}
