package javademo.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Dharma
 * @created 2009-5-17
 */
public class ImageTest
{
    public static void main(String[] args)
    {
        ImageFrame frame = new ImageFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

/**
    A frame with an image panel
*/
class ImageFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    public ImageFrame()
    {
        setTitle("ImageTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        // add panel to frame
        
        ImagePanel panel = new ImagePanel(new File("4_6.jpg"));
        add(panel);
    }
    
    public static final int DEFAULT_WIDTH = 300;
    
    public static final int DEFAULT_HEIGHT = 200;
}

/**
   A panel that displays a tiled image
*/
class ImagePanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    public ImagePanel(File file)
    {
        try
        {
            image = ImageIO.read(file);
            init();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public ImagePanel(byte[] imageContent)
    {
        try
        {
            image = ImageIO.read(new ByteArrayInputStream(imageContent));
            init();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void init()
    {
        if (image == null)
        {
            return;
        }
        
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);
        this.setSize(imageWidth, imageHeight);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        g.drawImage(image, 0, 0, null);
    }
    
    private Image image;
}
