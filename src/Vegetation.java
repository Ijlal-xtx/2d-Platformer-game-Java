import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Vegetation {
    public double x;
    public double y;
    public int width;
    public int height;
    private BufferedImage vegetationImg;
    Vegetation(double x, double y, int width, int height,String imgSrc) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        InputStream is = Vegetation.class.getResourceAsStream(imgSrc);
        try {
            vegetationImg = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(this.vegetationImg, (int) this.x, (int) this.y, this.width, this.height, null);
    }


    public void debugDraw(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) this.x, (int) this.y, this.width, this.height);
    }
}
