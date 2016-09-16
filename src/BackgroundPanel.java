import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class BackgroundPanel extends JPanel
{
    Image image;
    public BackgroundPanel(String path)
    {
        try
        {
            image = ImageIO.read(new File(path));
        }
        catch (Exception e) { /*handled in paintComponent()*/ }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (image != null) g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
    }

}