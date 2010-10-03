package org.fanhongtao.ui.swing.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 一个专门显示图片的Panel
 * @author Dharma
 * @created 2009-5-17
 */
public class ImagePanel extends JPanel
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

    /** 所要显示的图片 */
    private Image image;
}
