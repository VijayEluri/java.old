package javademo.swing.draw.d1;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 通过生成新的Canvas来实现显示不同的图像
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
        canvas = new PaintPanel();
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
    
    public PaintPanel()
    {
        super();
        setLayout(null); // 一定要加上这一句，新增的Box才不会在“最小化”再“还原”后在屏幕上乱跑
        setSize(440, 440);
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
        
    }
    
    public void addBox()
    {
        int i = new Random().nextInt(20 * 20);
        int row = i / 20;
        int column = i - (row * 20);
        
        Box box = new Box(Color.BLACK);
        this.add(box);
        box.setBounds(20 * row + 1, column * 20 + 1, 18, 18);
    }
    
}

class Box extends Canvas
{
    private static final long serialVersionUID = 1L;
    
    private Color color;
    
    Box(Color color)
    {
        setSize(20, 20);
        this.color = color;
    }
    
    public void paint(Graphics g)
    {
        g.setColor(color);
        g.fillOval(0, 0, 18, 18);
    }
}
