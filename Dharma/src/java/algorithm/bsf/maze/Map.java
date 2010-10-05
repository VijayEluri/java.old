package algorithm.bsf.maze;

/**
 * @author Fan Hongtao
 * @created 2009-3-14
 */
public class Map
{
    
    public static final int BOX_HEIGHT = 20;
    
    public static final int BOX_WIDTH = 20;
    
    /**
     * 保存地图。<br>
     * 在生成地图时，将原始的地图周围增加一圈围墙，这样后继判断时就不用考虑越界。
     */
    int[][] map;
    
    /**
     * 地图的开始结点
     */
    Node start;
    
    /**
     * 地图的结束结点
     */
    Node stop;
    
    public Map()
    {
    }
    
    public Map(int[][] map)
    {
        this.map = map;
    }
    
    public int getValue(int x, int y)
    {
        return map[x][y];
    }
    
    public void setValue(int x, int y, int value)
    {
        map[x][y] = value;
    }
    
    public int[][] getMap()
    {
        return map;
    }
    
    public Node getStart()
    {
        return start;
    }
    
    public Node getStop()
    {
        return stop;
    }
    
    public int getXSize()
    {
        return map.length;
    }
    
    public int getYSize()
    {
        return map[0].length;
    }
    
    public int getWidth()
    {
        return map.length * BOX_WIDTH;
    }
    
    public int getHeight()
    {
        return map[0].length * BOX_HEIGHT;
    }
    
    /**
     * 生成地图
     */
    public void initMap()
    {
        int rowNum = Env.getInteger("rowNum");
        int columnNum = Env.getInteger("columnNum");
        
        map = new int[rowNum + 2][columnNum + 2]; // 加2是为了增加围墙
        
        // 增加围墙
        for (int i = 0; i < rowNum + 2; i++)
        {
            if ((i == 0) || (i == rowNum + 1))
            {
                for (int j = 0; j < columnNum + 2; j++)
                {
                    map[i][j] = PathState.BLOCK;
                }
            }
            else
            {
                map[i][0] = PathState.BLOCK;
                map[i][columnNum + 1] = PathState.BLOCK;
            }
        }
        
        // 设置路障，在获取时 +1 是因为增加了围墙
        int blockNum = Env.getInteger("blockNum");
        for (int i = 1; i <= blockNum; i++)
        {
            int blockRow = Env.getInteger("blockRow" + i) + 1;
            int blockColumn = Env.getInteger("blockColumn" + i) + 1;
            map[blockRow][blockColumn] = PathState.BLOCK;
        }
        
        // 获取起点和终点，在获取时 +1 是因为增加了围墙
        int startRow = Env.getInteger("startRow") + 1;
        int startColumn = Env.getInteger("startColumn") + 1;
        int stopRow = Env.getInteger("stopRow") + 1;
        int stopColumn = Env.getInteger("stopColumn") + 1;
        
        start = new Node(startRow, startColumn);
        stop = new Node(stopRow, stopColumn);
    }
    
}
