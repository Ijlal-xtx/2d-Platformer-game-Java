import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particle {
    public double x;
    public double y;
    public int width;
    public int height;
    public Color color;
    public int maxSpeed = 5;
    public int minSpeed = 3;
    public double velocityX;
    public double velocityY;
    boolean markedForDeletion;
    Random rand;

    Particle(double x, double y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        rand = new Random();
        this.width = width;
        this.height = height;
        this.color = color;
        this.velocityX = rand.nextBoolean() ? rand.nextInt(this.maxSpeed - this.minSpeed) + this.minSpeed
                : -(rand.nextInt(this.maxSpeed - this.minSpeed) + this.minSpeed);
        this.velocityY = rand.nextBoolean() ? rand.nextInt(this.maxSpeed - this.minSpeed) + this.minSpeed
                : -(rand.nextInt(this.maxSpeed - this.minSpeed) + this.minSpeed);
        this.markedForDeletion = false;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval((int) this.x, (int) this.y, this.width, this.height);
    }

    public void update() {
        this.y += this.velocityY;
        this.x += this.velocityX;
        this.width *= 0.99;
        this.height *= 0.99;
        if(this.width < 0.5 || this.height < 0.5){
            this.markedForDeletion = true;
        }
    }
}