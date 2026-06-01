import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Flag {
    public double x;
    public double y;
    public int width;
    public int height;
    public static BufferedImage flagImg;
     static {
        try {
            InputStream is = Flag.class.getResourceAsStream("/res/flagGreen.png");

            if (is == null) {
                System.out.println("Flag image not found");
            } else {
                flagImg= ImageIO.read(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Flag(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
     
    }

    public void draw(Graphics g) {
        g.drawImage(this.flagImg, (int) this.x, (int) this.y, this.width, this.height, null);
    }

}
