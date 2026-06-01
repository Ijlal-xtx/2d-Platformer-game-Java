import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class Blade {
    public double x;
    public double y;
    public int width;
    public int height;
    public static BufferedImage bladeImage;
    public double velocitY = 0.8;
    public int maxY;
    public int minY;
    public double angle = 0;
    private Random rand;
     static {
        try {
            InputStream is = Blade.class.getResourceAsStream("/res/blade_3.png");

            if (is == null) {
                System.out.println("Blade image not found");
            } else {
                bladeImage= ImageIO.read(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Blade(double x, double y,int width,int height) {
        this.x = x;
        this.y = y; 
        this.width = width;
        this.height = height;
        rand = new Random();

    }
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();
        double centerX = this.x + this.width / 2;
        double centerY = this.y + this.height / 2;
        g2d.rotate(Math.toRadians(this.angle),centerX,centerY);
        g2d.drawImage(bladeImage, (int)this.x,(int)this.y,this.width,this.height, null);
        g2d.setTransform(originalTransform);
    }
    public void update(){
        this.y += this.velocitY;
        this.angle += rand.nextInt(5) + 2;
        if(this.angle > 360){
            this.angle= 0;
        }
        if(this.y + this.height > this.maxY || this.y < this.minY){
           this.velocitY *= -1;
        }
        if(this.y + this.height > this.maxY){
          this.y = this.maxY - this.height;
        }
        if(this.y < this.minY){
           this.y = this.minY;
        }

    }
    public void debugDraw(Graphics g){
        g.setColor(Color.RED);
        g.drawRect((int)this.x,(int)this.y,this.width,this.height);
    } 
}
