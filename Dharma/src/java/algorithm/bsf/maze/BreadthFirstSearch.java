package algorithm.bsf.maze;

import java.util.ArrayList;

import algorithm.bsf.maze.logger.ConsoleLogger;
import algorithm.bsf.maze.logger.IMapLogger;
import algorithm.bsf.maze.logger.IMapLogger.MoveType;

/**
 * 维基百科定义：<br>
 * 广度优先搜索算法（Breadth-First-Search），又译作宽度优先搜索，或横
 * 向优先搜索，简称BFS，是一种图形搜索演算法。简单的说，BFS是从根节点开
 * 始，沿着树的宽度遍历树的节点，如果发现目标，则算法中止。广度优先搜索
 * 的实现一般采用open-closed表。<p>
 * 
 * @author Fan Hongtao
 * @created 2009-3-15
 */
public class BreadthFirstSearch
{
    private IMapLogger logger = null;
    
    public BreadthFirstSearch(IMapLogger logger)
    {
        super();
        this.logger = logger;
    }
    
    /**
     * 寻找出路（最短路径）
     * @param map 表示地图的数组（要求地图的最外一圈都是围墙）
     * @param start 开始结点
     * @param stop  终止结点
     * @return 找到的路径。如果找不到路径，则返回 null
     */
    public ArrayList<Node> searchPath(int[][] map, final Node start, final Node stop)
    {
        logger.logMap(map);
        
        ArrayList<Node> list = new ArrayList<Node>();
        
        Node curr = start.clone();
        list.add(curr);
        
        Node next = curr;
        map[next.getX()][next.getY()] = 2;
        while (!next.equals(stop))
        {
            if (list.isEmpty())
            {
                return null; // 找不到路径
            }
            
            curr = list.remove(0); // 永远取第一个结点进行判断
            for (int i = 0; i < Node.PATH_NUM; i++)
            {
                next = curr.move(i);
                if (map[next.getX()][next.getY()] != PathState.EMPTY)
                {
                    continue;
                }
                
                map[next.getX()][next.getY()] = map[curr.getX()][curr.getY()] + 1;
                logger.logMove(curr, next, MoveType.SEARCH);
                if (next.equals(stop))
                {
                    break;
                }
                list.add(next);
            }
        }
        
        logger.logMap(map);
        
        // 已经找到出口, 从终点反向找回起点
        ArrayList<Node> path = new ArrayList<Node>();
        path.add(0, stop);
        Node pre = curr;
        for (int i = map[curr.getX()][curr.getY()]; i > 2; i--)
        {
            path.add(0, curr);
            for (int j = 0; j < Node.PATH_NUM; j++)
            {
                pre = curr.move(j);
                if (map[pre.getX()][pre.getY()] == i - 1)
                {
                    break;
                }
            }
            curr = pre;
        }
        path.add(0, start);
        
        return path;
    }
    
    /**
    * @param args
    */
    public static void main(String[] args)
    {
        // String fileName = (args.length > 0) ? args[0] : "maze.txt";
        String fileName = (args.length > 0) ? args[0] : null;
        Env.setInputFileName(fileName);
        
        IMapLogger logger = new ConsoleLogger();
        BreadthFirstSearch search = new BreadthFirstSearch(logger);
        
        Map map = new Map();
        map.initMap();
        
        ArrayList<Node> path = search.searchPath(map.getMap(), map.start, map.stop);
        logger.logPath(path);
    }
}
