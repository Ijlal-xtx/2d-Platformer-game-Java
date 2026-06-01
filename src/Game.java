public class Game implements Runnable {
 private GameWindow gameWindow;
 private GamePanel gamePanel;
 private Thread gameThread;
 private final int FPS = 120;
 private int frames = 0;
  Game(){
    gamePanel = new GamePanel();
    gameWindow = new GameWindow(gamePanel);
    gamePanel.setFocusable(true);
    gamePanel.requestFocus();
    startGameLoop();
  }
 void startGameLoop(){
    gameThread = new Thread(this);
    gameThread.start();
 }
 @Override
 public void run() {
   double timePerFrame = 1000000000 / FPS;
   long now = System.nanoTime();
   long lastTime = System.nanoTime();
   long lastCheck = System.currentTimeMillis();
   while(true){
      now = System.nanoTime();
  
      if(now - lastTime >= timePerFrame){
        gamePanel.updateGame();
        gamePanel.repaint();
        lastTime = System.nanoTime();
        frames++;
      }
      
       if(System.currentTimeMillis() - lastCheck >= 1000){
         lastCheck = System.currentTimeMillis();
         System.out.println("Fps: " + frames);
         frames = 0; 
       }
   }
 }
}
