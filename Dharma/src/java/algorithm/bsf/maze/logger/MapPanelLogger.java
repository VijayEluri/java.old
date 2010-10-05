package algorithm.bsf.maze.logger;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.fanhongtao.utils.Utils;

import algorithm.bsf.maze.Node;
import algorithm.bsf.maze.frame.MapPanel;

/**
 * 在JPanel上记录走的路径
 * @author Fan Hongtao
 * @created 2009-3-16
 */
public class MapPanelLogger implements IMapLogger
{
    
    /**
     * 用于记录的JPanel<br>
     * 在其它地方创建，这里只是使用。
     */
    private MapPanel panel;
    
    public MapPanelLogger(MapPanel panel)
    {
        super();
        this.panel = panel;
    }
    
    @Override
    public void logMove(Node from, Node to, int moveType)
    {
        if (moveType == MoveType.SEARCH)
        {
            panel.addLine(from, to);
            panel.chageColor(to.getX(), to.getY(), Color.LIGHT_GRAY);
        }
        else
        {
            panel.deleteLine(from, to);
            panel.chageColor(from.getX(), from.getY(), Color.WHITE);
        }
        Utils.sleep(100);
    }
    
    @Override
    public void logPath(ArrayList<Node> path)
    {
        if (path == null)
        {
            JOptionPane.showMessageDialog(panel, "Can't find path.");
        }
        else
        {
            // 将路径中除开始结点和结束结点之外的结点颜色修改成蓝色
            for (int i = 1, n = path.size() - 1; i < n; i++)
            {
                Node node = path.get(i);
                panel.chageColor(node.getX(), node.getY(), Color.BLUE);
                Utils.sleep(300);
            }
        }
    }
    
    @Override
    public void logMap(int[][] map)
    {
        // panel.repaint();
        // 不需要做特殊处理
    }
    
}
