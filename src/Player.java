import java.awt.Color;
import java.awt.Graphics;

public class Player {
  public double x;
  public double y;
  public int width;
  public int height;
  public double velocitX = 5;
  public double velocitY = 0;
  public Color color;
  public boolean moveDown = false;
  public boolean moveRight = false;
  public boolean moveLeft = false;
  public boolean moveUp = false;
  public boolean jump = false;
  public boolean onGround = false;
  private GamePanel gamePanel;
  public double previousY;
  public boolean alive = true;
  
  Player(int x, int y, int width, int height, Color color, GamePanel gamePanel) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
    this.gamePanel = gamePanel;
  }

  void draw(Graphics g) {
    g.setColor(color);
    g.fillRect((int) this.x, (int) this.y, this.width, this.height);
  }

  void update() {
    this.previousY = this.y;
    this.velocitY += gamePanel.gravity;
    this.y += this.velocitY;
   
    if (this.jump && this.onGround) {
      this.velocitY -= 12;
      this.onGround = false;
    }
    if (this.x + this.width > 1100) {
      this.x = 1100 - this.width;
    }
    if (this.y < 0) {
      this.y = 0;
    }

  }
}
