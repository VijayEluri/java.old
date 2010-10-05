package algorithm.bsf.maze.logger;

import java.util.ArrayList;

import algorithm.bsf.maze.Node;

/**
 * @author Fan Hongtao
 * @created 2009-3-14
 */
public interface IMapLogger
{
    /**
     * 记录结点的移动情况（从from移动到to）
     * @param from 源结点
     * @param to 目的结点
     * @param moveType 移动类型
     */
    public void logMove(Node from, Node to, int moveType);
    
    public void logPath(ArrayList<Node> path);
    
    public void logMap(int[][] map);
    
    class MoveType
    {
        /**
         * 搜索状态下的移动 
         */
        public static final int SEARCH = 0;
        
        /**
         * 回退状态下的移动
         */
        public static final int RETRIEVE = 1;
    }
}
