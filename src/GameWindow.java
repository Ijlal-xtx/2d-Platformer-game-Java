import javax.swing.JFrame;

public class GameWindow {
  private JFrame jframe;
   GameWindow(GamePanel gamePanel){
    jframe = new JFrame();
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.add(gamePanel);
    jframe.pack();
    jframe.setLocationRelativeTo(null);
    jframe.setVisible(true);
   }
}
