import java.awt.Color;
import java.awt.Graphics;
public abstract class Platform {
    public double x;
    public double y;
    public int width;
    public int height;
    // public int height;

    public Platform(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
        // this.height = height;
    }

    public abstract void draw(Graphics g);

    public void update() {
        // empty by default
    }
    public void debugDraw(Graphics g){
        g.setColor(Color.RED);
        g.drawRect((int)this.x,(int)this.y,this.width,this.height);
    } 
   
}