
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    private Player player;
    
    KeyboardInputs(Player player) {
        this.player = player;
    }

    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // case KeyEvent.VK_W:
            //    player.moveUp = true;
            //     break;
            case KeyEvent.VK_A:
               player.moveLeft= true;
                break;
            case KeyEvent.VK_SPACE:
               player.jump= true;
                break;
            // case KeyEvent.VK_S:
            //     player.moveDown = true;
            //     break;
            case KeyEvent.VK_D:
                player.moveRight = true;
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
          switch (e.getKeyCode()) {
            // case KeyEvent.VK_W:
            //    player.moveUp = false;
            //     break;
            case KeyEvent.VK_A:
               player.moveLeft= false;
                break;
            case KeyEvent.VK_SPACE:
               player.jump= false;
                break;
            // case KeyEvent.VK_S:
            //     player.moveDown = false;
            //     break;
            case KeyEvent.VK_D:
                player.moveRight = false;
                break;

            default:
                break;
        }
    }

}
