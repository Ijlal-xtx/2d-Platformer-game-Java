import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class JumpPad {
    public double x;
    public double y;
    public int width;
    public int height;
    public double jumpStrength = 5.0;
    public static BufferedImage jumpPadImg;
    public boolean markedForDeletion;
     static {
        try {
            InputStream is = JumpPad.class.getResourceAsStream("/res/jumpPad.png");

            if (is == null) {
                System.out.println("Jump pad image not found");
            } else {
                jumpPadImg = ImageIO.read(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    JumpPad(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.markedForDeletion = false;
        
    }

    public void draw(Graphics g) {
        g.drawImage(this.jumpPadImg, (int) this.x, (int) this.y, this.width, this.height, null);
    }

}
