package algorithm.bsf.maze.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JPanel;

import algorithm.bsf.maze.Map;
import algorithm.bsf.maze.Node;

/**
 * @author Fan Hongtao
 * @created 2009-3-16
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel
{
    private Map map;
    
    private Color[][] colors;
    
    private HashMap<String, Pair<Node>> lineMap = new HashMap<String, Pair<Node>>();
    
    public MapPanel(Map map)
    {
        super();
        setLayout(null);
        this.map = map;
        setSize(map.getWidth() + 20, map.getHeight() + 20);
        
        colors = new Color[map.getXSize()][map.getYSize()];
        for (int i = 0; i < map.getXSize(); i++)
        {
            for (int j = 0; j < map.getYSize(); j++)
            {
                if (map.getMap()[i][j] != 0)
                {
                    colors[i][j] = SystemColor.BLACK;
                }
                else
                {
                    colors[i][j] = SystemColor.WHITE;
                }
            }
        }
        
        Node start = map.getStart();
        Node stop = map.getStop();
        colors[start.getX()][start.getY()] = Color.YELLOW;
        colors[stop.getX()][stop.getY()] = Color.RED;
    }
    
    public void chageColor(int x, int y, Color color)
    {
        colors[x][y] = color;
        repaint();
    }
    
    /**
     * 增加from到to结点之间的连线，通常在搜索时使用
     * @param from
     * @param to
     */
    public void addLine(Node from, Node to)
    {
        lineMap.put(getLineKey(from, to), new Pair<Node>(from, to));
    }
    
    private String getLineKey(Node from, Node to)
    {
        return String.format("%1d_%2d_%3d_%4d", from.getX(), from.getY(), to.getX(), to.getY());
    }
    
    /**
     * 删除from到to结点之间的连线，通常在回退时使用
     * @param from
     * @param to
     */
    public void deleteLine(Node from, Node to)
    {
        lineMap.remove(getLineKey(to, from));
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        int length = map.getWidth();
        int height = map.getHeight();
        
        // 画网络
        for (int i = 0; i <= length; i += Map.BOX_WIDTH)
        {
            g.drawLine(i, 0, i, height);
        }
        
        for (int j = 0; j <= height; j += Map.BOX_HEIGHT)
        {
            g.drawLine(0, j, length, j);
        }
        
        Node stop = map.getStop();
        colors[stop.getX()][stop.getY()] = Color.RED;
        
        // 填充网格
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < map.getXSize(); i++)
        {
            for (int j = 0; j < map.getYSize(); j++)
            {
                fillBox(g2, i, j, colors[i][j]);
            }
        }
        
        // 绘制箭头
        for (Pair<Node> pair : lineMap.values())
        {
            drawLine(g2, pair.getFirst(), pair.getSecond(), Color.BLACK);
        }
    }
    
    private void fillBox(Graphics2D g2, int x, int y, Color color)
    {
        g2.setColor(color);
        Rectangle2D rect = new Rectangle2D.Double(x * 20 + 2, y * 20 + 2, 16, 16);
        g2.fill(rect);
    }
    
    private void drawLine(Graphics2D g2, Node from, Node to, Color color)
    {
        if (from.getX() == to.getX())
        {
            if (from.getY() < to.getY())
            {
                drawLineToDown(g2, from, to, color);
            }
            else
            {
                drawLineToUp(g2, from, to, color);
            }
        }
        else
        {
            if (from.getX() < to.getX())
            {
                drawLineToRight(g2, from, to, color);
            }
            else
            {
                drawLineToLeft(g2, from, to, color);
            }
        }
    }
    
    private void drawLineToRight(Graphics2D g2, Node from, Node to, Color color)
    {
        g2.setColor(color);
        GeneralPath path = new GeneralPath();
        int startX = from.getX() * 20 + 10;
        int startY = from.getY() * 20;
        path.moveTo(startX + 2, startY + 8);
        path.lineTo(startX + 12, startY + 8);
        path.lineTo(startX + 12, startY + 4);
        path.lineTo(startX + 16, startY + 10);
        path.lineTo(startX + 12, startY + 16);
        path.lineTo(startX + 12, startY + 12);
        path.lineTo(startX + 2, startY + 12);
        path.closePath();
        g2.fill(path);
    }
    
    private void drawLineToLeft(Graphics2D g2, Node from, Node to, Color color)
    {
        g2.setColor(color);
        GeneralPath path = new GeneralPath();
        int startX = from.getX() * 20 - 10;
        int startY = from.getY() * 20;
        path.moveTo(startX + 18, startY + 8);
        path.lineTo(startX + 8, startY + 8);
        path.lineTo(startX + 8, startY + 4);
        path.lineTo(startX + 4, startY + 10);
        path.lineTo(startX + 8, startY + 16);
        path.lineTo(startX + 8, startY + 12);
        path.lineTo(startX + 18, startY + 12);
        path.closePath();
        g2.fill(path);
    }
    
    private void drawLineToDown(Graphics2D g2, Node from, Node to, Color color)
    {
        g2.setColor(color);
        GeneralPath path = new GeneralPath();
        int startX = from.getX() * 20;
        int startY = from.getY() * 20 + 10;
        path.moveTo(startX + 8, startY + 2);
        path.lineTo(startX + 8, startY + 12);
        path.lineTo(startX + 4, startY + 12);
        path.lineTo(startX + 10, startY + 16);
        path.lineTo(startX + 16, startY + 12);
        path.lineTo(startX + 12, startY + 12);
        path.lineTo(startX + 12, startY + 2);
        path.closePath();
        g2.fill(path);
    }
    
    private void drawLineToUp(Graphics2D g2, Node from, Node to, Color color)
    {
        g2.setColor(color);
        GeneralPath path = new GeneralPath();
        int startX = from.getX() * 20;
        int startY = from.getY() * 20 - 10;
        path.moveTo(startX + 8, startY + 18);
        path.lineTo(startX + 8, startY + 8);
        path.lineTo(startX + 4, startY + 8);
        path.lineTo(startX + 10, startY + 4);
        path.lineTo(startX + 16, startY + 8);
        path.lineTo(startX + 12, startY + 8);
        path.lineTo(startX + 12, startY + 18);
        path.closePath();
        g2.fill(path);
    }
}

class Pair<T>
{
    private T first;
    
    private T second;
    
    public Pair(T first, T second)
    {
        super();
        this.first = first;
        this.second = second;
    }
    
    public T getFirst()
    {
        return first;
    }
    
    public T getSecond()
    {
        return second;
    }
}
