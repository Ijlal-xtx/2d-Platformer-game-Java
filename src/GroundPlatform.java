import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GroundPlatform extends Platform {
    public int repeat = 1;
    public static BufferedImage platformMidImg;;
    public static BufferedImage platformLeftImg;
    public static BufferedImage platformRightImg;
    public int ImgWidth = 70;
    public int ImgHeight = 70;
   static {
       try {
            InputStream is1 = GroundPlatform.class.getResourceAsStream("/res/grassLeft.png");
            InputStream is2 = GroundPlatform.class.getResourceAsStream("/res/grassMid.png");
            InputStream is3 = GroundPlatform.class.getResourceAsStream("/res/grassRight.png");
            platformLeftImg = ImageIO.read(is1);
            platformMidImg = ImageIO.read(is2);
            platformRightImg = ImageIO.read(is3);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
    GroundPlatform(double x, double y, int repeat) {
        super(x, y);
        // this.height = height;
        this.repeat = repeat;
     
        this.width = this.ImgWidth * this.repeat;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < repeat; i++) { // middle 
            g.drawImage(this.platformMidImg, (int) this.x + (i * this.ImgWidth), (int) this.y, this.ImgWidth,
                    this.ImgHeight,
                    null);
        }
    }

}
