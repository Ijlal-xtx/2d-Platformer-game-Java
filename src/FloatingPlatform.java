import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class FloatingPlatform extends Platform {
    public static BufferedImage right1Image;
    public static BufferedImage left1Image;
    public static BufferedImage right2Image;
    public static BufferedImage left2Image;
    public int ImgWidth;
    public int ImgHeight;
    private boolean randomImgVariant;
    private Random rand;
   static {
       try {
            InputStream is1 = FloatingPlatform.class.getResourceAsStream("/res/floatingPlatformRight.png");
            InputStream is2 = FloatingPlatform.class.getResourceAsStream("/res/floatingPlatformLeft.png");
            InputStream is3 = FloatingPlatform.class.getResourceAsStream("/res/grassCliffLeft.png");
            InputStream is4 = FloatingPlatform.class.getResourceAsStream("/res/grassCliffRight.png");
            right1Image = ImageIO.read(is1);
            left1Image = ImageIO.read(is2);
            left2Image = ImageIO.read(is3);
            right2Image = ImageIO.read(is4);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
    FloatingPlatform(double x, double y) {
        super(x, y);
        rand = new Random();
        randomImgVariant = rand.nextBoolean();
        this.ImgWidth = 90;
        this.ImgHeight = 90;
        this.width = ImgWidth * 2;
        this.height = ImgHeight;
    }
    
    public void draw(Graphics g) {
        if (randomImgVariant) {
            g.drawImage(this.left1Image, (int) this.x, (int) this.y, this.ImgWidth, this.ImgHeight, null);
            g.drawImage(this.right1Image, (int) this.x + this.ImgWidth, (int) this.y, this.ImgWidth, this.ImgHeight,null);
        } else {
            g.drawImage(this.left2Image, (int) this.x, (int) this.y, this.ImgWidth, this.ImgHeight, null);
            g.drawImage(this.right2Image, (int) this.x + this.ImgWidth, (int) this.y, this.ImgWidth, this.ImgHeight,null);
        }
    }
}
