import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Coin {
    public double x;
    public double y;
    public int width;
    public int height;
    public static BufferedImage coinImg;
    public boolean markedForDeletion = false;
    static {
        try {
            InputStream is = Coin.class.getResourceAsStream("/res/coin.png");

            if (is == null) {
                System.out.println("Coin image not found");
            } else {
                coinImg = ImageIO.read(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Coin(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.drawImage(this.coinImg, (int) this.x, (int) this.y, this.width, this.height, null);
    }

}
