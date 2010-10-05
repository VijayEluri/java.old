package algorithm.bsf.maze.frame;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import algorithm.bsf.maze.BreadthFirstSearch;
import algorithm.bsf.maze.Env;
import algorithm.bsf.maze.Map;
import algorithm.bsf.maze.Node;
import algorithm.bsf.maze.logger.MapPanelLogger;

/**
 * @author Fan Hongtao
 * @created 2009-3-16
 */
@SuppressWarnings("serial")
public class MazeFrame extends JFrame implements ActionListener
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String fileName = (args.length > 0) ? args[0] : null;
        Env.setInputFileName(fileName);
        
        Map map = new Map();
        map.initMap();
        
        JFrame frame = new MazeFrame(map);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private Button button = null;
    
    private MapPanel canvas;
    
    private Map map;
    
    public MazeFrame(Map map)
    {
        this.map = map;
        
        setTitle("Map Frame");
        setLayout(null); // 必需要有此语句！
        
        button = new Button("Start");
        button.addActionListener(this);
        add(button);
        button.setBounds(10, 1, 80, 26);
        
        canvas = new MapPanel(map);
        add(canvas);
        canvas.setBounds(10, 35, map.getWidth() + 20, map.getHeight() + 20);
        
        pack();
        setSize(map.getWidth() + 30, map.getHeight() + 80);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Runnable r = new Runnable()
        {
            
            @Override
            public void run()
            {
                search();
            }
            
        };
        Thread thread = new Thread(r);
        thread.start();
        canvas.repaint();
    }
    
    private void search()
    {
        MapPanelLogger logger = new MapPanelLogger(canvas);
        BreadthFirstSearch search = new BreadthFirstSearch(logger);
        ArrayList<Node> path = search.searchPath(map.getMap(), map.getStart(), map.getStop());
        logger.logPath(path);
    }
    
    public void chageColor(int x, int y, Color color)
    {
        canvas.chageColor(x, y, color);
    }
}
