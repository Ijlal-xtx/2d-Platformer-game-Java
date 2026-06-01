import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Spike {
    public double x;
    public double y;
    public int width;
    public int height;
    public static BufferedImage spikeImage;
    static {
           InputStream is = Spike.class.getResourceAsStream("/res/spike.png");
        try {
            spikeImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Spike(double x, double y,int width,int height) {
        this.x = x;
        this.y = y; 
        this.width = width;
        this.height = height;
    
    }
    public void draw(Graphics g){
        g.drawImage(spikeImage, (int)this.x,(int)this.y,this.width,this.height, null);
    }
}
