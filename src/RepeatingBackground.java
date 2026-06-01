import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class RepeatingBackground {
    private BufferedImage backgroundImage;
    public int width;
    public int height;
    public int x;
    public int y;

    RepeatingBackground(int x, int y,String imagePath) {
       this.x = x;
       this.y = y;
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            backgroundImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = this.backgroundImage.getWidth();
        this.height = this.backgroundImage.getHeight();
    }

    void draw(Graphics g) {
        g.drawImage(backgroundImage, this.x, this.y, this.width, this.height, null);
        g.drawImage(backgroundImage, this.x + this.width, this.y, this.width, this.height, null);
        g.drawImage(backgroundImage, this.x + this.width * 2, this.y, this.width, this.height, null);
    }

    void update() {
        if (this.x < -this.width) {
            this.x = 0;
        }

    }

}
