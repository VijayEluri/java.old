package algorithm.bsf.maze.logger;

import java.util.ArrayList;

import algorithm.bsf.maze.Node;

/**
 * @author Fan Hongtao
 * @created 2009-3-14
 */
public class ConsoleLogger implements IMapLogger
{
    
    @Override
    public void logMove(Node from, Node to, int moveType)
    {
        if (moveType == MoveType.SEARCH)
        {
            System.out.println("Search from " + from + " to " + to);
        }
        else
        {
            System.out.println("Retrieve from " + from + " to " + to);
        }
    }
    
    @Override
    public void logPath(ArrayList<Node> path)
    {
        if (path == null)
        {
            System.out.println("Can't find path.");
        }
        else
        {
            for (Node node : path)
            {
                System.out.print(node);
            }
            System.out.println();
        }
    }
    
    @Override
    public void logMap(int[][] map)
    {
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                System.out.print(String.format("%1$3d ", map[i][j]));
            }
            System.out.println();
        }
    }
    
}
