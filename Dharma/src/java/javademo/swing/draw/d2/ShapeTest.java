package javademo.swing.draw.d2;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 直接在Panel上绘制不同的图像
 * @author Dharma
 * @created 2009-3-14
 */
public class ShapeTest
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        JFrame frame = new ShapeTestFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

@SuppressWarnings("serial")
class ShapeTestFrame extends JFrame implements ActionListener
{
    private Button button = null;
    
    private PaintPanel canvas;
    
    public ShapeTestFrame()
    {
        setTitle("Shape test");
        setLayout(null); // 必需要有此语句！
        button = new Button("Start");
        button.addActionListener(this);
        add(button);
        button.setBounds(10, 1, 100, 26);
        // add(button, BorderLayout.NORTH);
        canvas = new PaintPanel();
        // add(canvas, BorderLayout.CENTER);
        add(canvas);
        canvas.setBounds(10, 35, 420, 420);
        pack();
        setSize(430, 480);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        canvas.addBox();
    }
}

class PaintPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private int[][] map = new int[20][20];
    
    public PaintPanel()
    {
        super();
        setLayout(null); // 一定要加上这一句，新增的Box才不会在“最小化”再“还原”后在屏幕上乱跑
        setSize(440, 440);
        
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                map[i][j] = 0;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // 画网络
        for (int i = 0; i <= 400; i = i + 20)
        {
            g.drawLine(0, i, 400, i);
        }
        
        for (int j = 0; j <= 400; j = j + 20)
        {
            g.drawLine(j, 0, j, 400);
        }
        
        // 画图像
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map.length; j++)
            {
                if (map[i][j] != 0)
                {
                    Rectangle2D rect = new Rectangle2D.Double(i * 20 + 2, j * 20 + 2, 16, 16);
                    g2.fill(rect);
                }
            }
        }
    }
    
    public void addBox()
    {
        int i = new Random().nextInt(map.length * map[0].length);
        int row = i / map.length;
        int column = i - (row * map.length);
        map[row][column] = 1;
        repaint();
    }
    
}

class Box
{
    
    public Box(Color color, double x, double y)
    {
        super();
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
    public Ellipse2D getShape()
    {
        return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
    }
    
    public Color getColor()
    {
        return color;
    }
    
    Color color;
    
    double x = 0;
    
    double y = 0;
    
    static final int XSIZE = 18;
    
    static final int YSIZE = 18;
}
