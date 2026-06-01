import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Crate {
    public double x;
    public double y;
    public int width;
    public int height;
    public static BufferedImage crateImg;
     static {
        try {
            InputStream is = Crate.class.getResourceAsStream("/res/box2.png");
            if (is == null) {
                System.out.println("Crate image not found");
            } else {
                crateImg = ImageIO.read(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Crate(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
   
    }

    public void draw(Graphics g) {
        g.drawImage(this.crateImg, (int) this.x, (int) this.y, this.width, this.height, null);
    }

}
