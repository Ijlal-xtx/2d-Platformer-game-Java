import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Saw {
    public double x;
    public double y;
    public int width;
    public int height;
    public static BufferedImage sawImage;
    public double velocitX = 0.7;
    public int maxX;
    public int minX;
    public double angle = 0;
    static {
         InputStream is = Saw.class.getResourceAsStream("/res/saw.png");
        try {
            sawImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Saw(double x, double y,int width,int height) {
        this.x = x;
        this.y = y; 
        this.width = width;
        this.height = height;
       
    }
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();
        double centerX = this.x + this.width / 2;
        double centerY = this.y + this.height / 2;
        g2d.rotate(Math.toRadians(this.angle),centerX,centerY);
        g2d.drawImage(sawImage, (int)this.x,(int)this.y,this.width,this.height, null);
        g2d.setTransform(originalTransform);
    }
    public void update(){
        this.x += this.velocitX;
        this.angle += 10;
        if(this.angle > 360){
            this.angle= 0;
        }
        if(this.x + this.width > maxX || this.x < minX){
           this.velocitX *= -1;
        }
        if(this.x + this.width > maxX){
          this.x = maxX - this.width;
        }
        if(this.x < minX){
           this.x = minX;
        }

    }
    public void debugDraw(Graphics g){
        g.setColor(Color.RED);
        g.drawRect((int)this.x,(int)this.y,this.width,this.height);
    } 
}
