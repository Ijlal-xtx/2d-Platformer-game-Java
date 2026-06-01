import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class RotatingBlade {
    public double x;
    public double y;
    public int width;
    public int height;
    public double angle = 0;
    public static BufferedImage rotatingBladeImage;
    static {
           InputStream is = RotatingBlade.class.getResourceAsStream("/res/blade_1.png");
        try {
           rotatingBladeImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    RotatingBlade(double x, double y,int width,int height) {
        this.x = x;
        this.y = y; 
        this.width = width;
        this.height = height;
    
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform originalTransform = g2d.getTransform();
        double centerX = this.x + this.width * 0.5;
        double centerY = this.y + this.height * 0.5;
        g2d.rotate(Math.toRadians(this.angle * 2),centerX,centerY);
        g2d.drawImage(this.rotatingBladeImage, (int)this.x,(int)this.y,this.width,this.height, null);
        g2d.setTransform(originalTransform);
    }
    public void update(){
        this.x += Math.cos(Math.toRadians(this.angle)) * 2;
        this.y += Math.sin(Math.toRadians(this.angle)) * 2;
        this.angle++;
        if(this.angle > 360){
            this.angle = 0;
        }
    }

}
