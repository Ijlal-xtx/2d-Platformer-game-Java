import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private Player player;
    private GroundPlatform groundPlatform; // starting ground platform
    private Flag flag;
    private ArrayList<GroundPlatform> groundPlatforms = new ArrayList<>();
    private ArrayList<FloatingPlatform> floatingPlatforms = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Saw> saws = new ArrayList<>();
    private ArrayList<Blade> blades = new ArrayList<>();
    private ArrayList<JumpPad> jumpPads = new ArrayList<>();
    private ArrayList<Crate> crates = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<RotatingBlade> rotatingBlades = new ArrayList<>();
    private BufferedImage tree1Img;
    private BufferedImage tree2Img;
    private BufferedImage tree3Img;
    private BufferedImage bush1Img;
    private BufferedImage bush2Img;
    private BufferedImage cactus1Img;
    private BufferedImage cactus2Img;
    private ArrayList<BufferedImage> vegetationImags = new ArrayList<>(
        Arrays.asList(tree1Img,tree2Img,tree3Img,cactus1Img,cactus2Img,bush1Img,bush2Img)
    );
    private BufferedImage playerImg;
    // private RepeatingBackground backgroundImg;
    public double gravity = 0.3;
    private Random rand;
    private int scrollOffsetX = 0;
    private int windowWidth = 1250;;
    private int windowHeight = 600;
    public boolean gameOver = false;

    GamePanel() {
        rand = new Random();
        player = new Player(100, 0, 40, 40, Color.BLUE, this);
        // backgroundImg = new RepeatingBackground(0, 0, "/res/backgroundEmpty.png");
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.addKeyListener(new KeyboardInputs(player));
        this.setBackground(new Color(198, 255, 254));
        init();
        flag = new Flag(
                platforms.get(platforms.size() - 1).x + platforms.get(platforms.size() - 1).width - 80,
                platforms.get(platforms.size() - 1).y - 80,
                80, 80);
    }

    public boolean isPlayerOnPlatform(Platform platform) {
        return (player.previousY + player.height <= platform.y &&
                player.y + player.height + player.velocitY >= platform.y &&
                player.x <= platform.x + platform.width &&
                player.x + player.width >= platform.x);
    }

    public boolean isPlayerOnTopOfJumpPads(JumpPad jumpPad) {
        return (player.previousY + player.height <= jumpPad.y &&
                player.y + player.height + player.velocitY >= jumpPad.y &&
                player.x <= jumpPad.x + jumpPad.width &&
                player.x + player.width >= jumpPad.x);
    }

    public boolean isPlayerOnTopOfCrate(Crate crate) {
        return (player.previousY + player.height <= crate.y &&
                player.y + player.height + player.velocitY >= crate.y &&
                player.x <= crate.x + crate.width &&
                player.x + player.width >= crate.x);
    }

    private void init() {
        // procedural generator function

        platforms.clear();
        gameOver = false;
        particles.clear();
        player.alive = true;
        scrollOffsetX = 0;
        // backgroundImg.x = 0;
        player.x = 100;
        player.y = 0;
        player.velocitY = 0;
        player.moveLeft = false;
        player.moveRight = false;
        player.onGround = false;
        groundPlatforms.clear();
        floatingPlatforms.clear();
        spikes.clear();
        saws.clear();
        blades.clear();
        rotatingBlades.clear();
        jumpPads.clear();
        crates.clear();
        coins.clear();
        // flag = null;

        groundPlatform = new GroundPlatform(0, windowHeight - 70, 4);
        platforms.add(groundPlatform);
        // ground platforms
        double lastGroundPlatformX = platforms.get(0).x + platforms.get(0).width;
        for (int i = 0; i < 19; i++) {
            double gap = rand.nextInt(50) + 300;
            double x = lastGroundPlatformX + gap;
            double y = windowHeight - 70;
            int repeat = rand.nextInt(2) + 4;
            Platform platform;
            platform = new GroundPlatform(x, y, repeat);
            platforms.add(platform);
            lastGroundPlatformX = x + platform.width;
        }
        double lastFloatingPlatformX = 100;
        // floating platforms
        for (int i = 0; i < 13; i++) {
            double gap = rand.nextInt(100) + 680;
            double x = lastFloatingPlatformX + gap;
            double y = rand.nextDouble(50) + 320;
            Platform platform;
            platform = new FloatingPlatform(x, y);
            platforms.add(platform);
            lastFloatingPlatformX = x + platform.width;
        }
        for (Platform p : platforms) {
            if (p instanceof GroundPlatform) {
                groundPlatforms.add((GroundPlatform) p);
            } else if (p instanceof FloatingPlatform) {
                floatingPlatforms.add((FloatingPlatform) p);
            }
        }

        // two spikes next to each other on random ground platforms
        ArrayList<Integer> groundPlatformsWithSpike = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int maxIndex = groundPlatforms.size();
            int minIndex = 2;
            int randIndex = rand.nextInt(maxIndex - minIndex) + minIndex;
            if (!groundPlatformsWithSpike.contains(randIndex)) {
                GroundPlatform randomGroundPlatform = groundPlatforms.get(randIndex);
                double maxX = randomGroundPlatform.x + randomGroundPlatform.width - 140;
                double minX = randomGroundPlatform.x + 50;
                double x = rand.nextDouble(maxX - minX) + minX;
                double y = randomGroundPlatform.y - 50;
                Spike s1 = new Spike(x, y, 50, 50);
                Spike s2 = new Spike(s1.x + 50, y, 50, 50);
                spikes.add(s1);
                spikes.add(s2);
                groundPlatformsWithSpike.add(randIndex);
            }
        }
        // create one moving saw on a random ground platform
        ArrayList<Integer> groundPlatformsWithSaws = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int maxIndex = groundPlatforms.size();
            int minIndex = 1;
            int randIndex = rand.nextInt(maxIndex - minIndex) + minIndex;
            if (groundPlatformsWithSaws.contains(randIndex))
                continue;
            GroundPlatform groundPlatform = groundPlatforms.get(randIndex);
            Saw saw = new Saw(0, 0, 40, 40);
            double maxX = groundPlatform.x + groundPlatform.width;
            double minX = groundPlatform.x;
            double x = rand.nextDouble(maxX - minX) + minX;
            double y = groundPlatform.y - saw.height * 0.6;
            saw.x = x;
            saw.y = y;
            saw.maxX = (int) maxX;
            saw.minX = (int) minX;
            saws.add(saw);
            groundPlatformsWithSaws.add(randIndex);
        }
        // place moving vertical blades between the floating platform gaps
        ArrayList<Integer> floatingPlatformGapsWithBlade = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int maxIndex = floatingPlatforms.size() - 2;
            int minIndex = 2;
            int randIndex = rand.nextInt(maxIndex - minIndex) + minIndex;
            if (floatingPlatformGapsWithBlade.contains(randIndex)) {
                continue;
            }

            FloatingPlatform floatingPlatformLeft = floatingPlatforms.get(randIndex);
            FloatingPlatform floatingPlatformRight = floatingPlatforms.get(randIndex + 1);
            Blade blade = new Blade(0, 0, 60, 60);
            double distance = Math.abs(floatingPlatformLeft.x + floatingPlatformLeft.width - floatingPlatformRight.x);
            if (distance < blade.width * 2) {
                continue;
            }
            double maxY = floatingPlatformLeft.y + floatingPlatformLeft.height > floatingPlatformRight.y
                    + floatingPlatformRight.height
                            ? floatingPlatformLeft.y + floatingPlatformLeft.height
                            : floatingPlatformRight.y + floatingPlatformRight.height;
            double minY = blade.height / 2;
            double x = floatingPlatformRight.x - blade.width * 0.5 - distance * 0.5;
            double y = blade.height / 2;
            blade.x = x;
            blade.y = y;
            blade.maxY = (int) maxY;
            blade.minY = (int) minY;
            blades.add(blade);
            floatingPlatformGapsWithBlade.add(randIndex);
        }
        // place jump pad at any random platform
        ArrayList<Integer> platformsWithJumpPads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int maxIndex = platforms.size() - 1;
            int minIndex = 1;
            int randomIndex = rand.nextInt(maxIndex - minIndex) + minIndex;
            if (platformsWithJumpPads.contains(randomIndex)) {
                continue;
            }
            Platform randomPlatform = platforms.get(randomIndex);
            JumpPad jumpad = new JumpPad(0, 0, 40, 40);
            double maxX = randomPlatform.x + randomPlatform.width - jumpad.width;
            double minX = randomPlatform.x + jumpad.width;
            double x = rand.nextDouble(maxX - minX) + minX;
            double y = randomPlatform.y - jumpad.height;
            jumpad.x = x;
            jumpad.y = y;
            jumpPads.add(jumpad);
            platformsWithJumpPads.add(randomIndex);
        }
        // create crates between gears
        ArrayList<Integer> floatingPlatormGapsWithCrates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int maxIndex = floatingPlatforms.size() - 2;
            int minIndex = 1;
            int randIndex = rand.nextInt(maxIndex - minIndex) + minIndex;
            if (floatingPlatormGapsWithCrates.contains(randIndex))
                continue;
            Crate crateLeft = new Crate(0, 0, 60, 60);
            Crate crateRight = new Crate(0, 0, 60, 60);
            FloatingPlatform platformLeft = floatingPlatforms.get(randIndex);
            FloatingPlatform platformRight = floatingPlatforms.get(randIndex + 1);
            int space = 80;
            double crateLeftX = platformRight.x - crateLeft.width - space;
            double crateRightX = platformLeft.x + platformLeft.width + crateRight.width + space;
            double crateLeftY = rand.nextDouble(100) + 200;
            double crateRightY = rand.nextDouble(100) + 200;
            // double x = rand.nextDouble(maxX - minX) + minX;
            crateLeft.x = crateLeftX;
            crateLeft.y = crateLeftY;
            crateRight.x = crateRightX;
            crateRight.y = crateRightY;
            crates.add(crateLeft);
            crates.add(crateRight);
            floatingPlatormGapsWithCrates.add(randIndex);
        }
        // create coins on any random floating platform
        for (int i = 0; i < 8; i++) {
            int maxIndex = floatingPlatforms.size();
            int minIndex = 3;
            int randomIndex = rand.nextInt(maxIndex - minIndex) + minIndex;
            FloatingPlatform floatingPlatform = floatingPlatforms.get(randomIndex);
            Coin coin = new Coin(0, 0, 40, 40);
            double minCoinX = floatingPlatform.x + coin.width;
            double maxCoinX = floatingPlatform.x + floatingPlatform.width - coin.width;
            double x = rand.nextDouble(maxCoinX - minCoinX) + minCoinX;
            double y = floatingPlatform.y - coin.height;
            coin.x = x;
            coin.y = y;
            coins.add(coin);
        }
        // rotating circular motion - blades
        ArrayList<Integer> floatingPlatormWithRotatingBaldes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int randIndex = rand.nextInt(floatingPlatforms.size());
            if (floatingPlatormWithRotatingBaldes.contains(randIndex)) {
                continue;
            }
            FloatingPlatform randomFloatingPlatform = floatingPlatforms.get(randIndex);
            RotatingBlade rotatingBlade = new RotatingBlade(0, 0, 60, 60);
            double x = randomFloatingPlatform.x + 500;
            double y = randomFloatingPlatform.y - rand.nextDouble(100) - 250;
            rotatingBlade.x = x;
            rotatingBlade.y = y;
            rotatingBlades.add(rotatingBlade);
            floatingPlatormWithRotatingBaldes.add(randIndex);
        }

        flag = new Flag(
                platforms.get(platforms.size() - 1).x + platforms.get(platforms.size() - 1).width - 80,
                platforms.get(platforms.size() - 1).y - 80,
                80, 80);
    }

    public void handlePlayerCollsionWithSpike() {
        for (Spike s : spikes) {
            Rectangle spikeRect = new Rectangle((int) s.x, (int) s.y, s.width, s.height);
            Rectangle playerRect = new Rectangle((int) player.x, (int) player.y, player.width, player.height);
            if (spikeRect.intersects(playerRect) && player.alive) {
                player.alive = false;
                for (int i = 0; i < 20; i++) {
                    particles.add(new Particle(player.x, player.y, 10, 10, Color.RED));
                }
                gameOver = true;
            }
        }
    }

    public void handlePlayerCollsionWithJumpPads() {
        for (int i = 0; i < jumpPads.size(); i++) {
            JumpPad jumpPad = jumpPads.get(i);
            if (isPlayerOnTopOfJumpPads(jumpPad)) {
                player.velocitY = -13;
                jumpPad.markedForDeletion = true;
                for (int j = 0; j < 20; j++) {
                    particles.add(new Particle(jumpPad.x, jumpPad.y, 5, 5, Color.blue));
                }
                break;
            }
        }
    }

    public void handlePlayerCollsionWithBlades() {
        for (Blade b : blades) {
            Rectangle bladeRect = new Rectangle((int) b.x, (int) b.y, b.width, b.height);
            Rectangle playerRect = new Rectangle((int) player.x, (int) player.y, player.width, player.height);
            if (bladeRect.intersects(playerRect) && player.alive) {
                player.alive = false;
                for (int i = 0; i < 20; i++) {
                    particles.add(new Particle(player.x, player.y, 10, 10, Color.RED));
                }
                gameOver = true;
            }
        }
    }

    public void handlePlayerCollsionWithFlag() {
        Rectangle flagRect = new Rectangle((int) flag.x, (int) flag.y, flag.width, flag.height);
        Rectangle playerRect = new Rectangle((int) player.x, (int) player.y, player.width, player.height);
        if (flagRect.intersects(playerRect) && player.alive) {
            // add other stuff
            System.out.println("You won the game!!");
        }
    }

    public void handlePlayerCollsionWithCoin() {
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            Rectangle coinRect = new Rectangle((int) coin.x, (int) coin.y, coin.width, coin.height);
            Rectangle playerRect = new Rectangle((int) player.x, (int) player.y, player.width, player.height);
            if (coinRect.intersects(playerRect)) {
                coin.markedForDeletion = true;
                for (int j = 0; j < 20; j++) {
                    particles.add(new Particle(coin.x, coin.y, 5, 5, Color.YELLOW));
                }
            }
        }
    }

    public void handlePlayerCollsionWithSaw() {
        for (Saw s : saws) {
            Rectangle sawRect = new Rectangle((int) s.x, (int) s.y, s.width, s.height);
            Rectangle playerRect = new Rectangle((int) player.x, (int) player.y, player.width, player.height);
            if (sawRect.intersects(playerRect) && player.alive) {
                player.alive = false;
                for (int i = 0; i < 20; i++) {
                    particles.add(new Particle(player.x, player.y, 10, 10, Color.RED));
                }
                gameOver = true;
            }
        }
    }
        public void handlePlayerCollsionWithRotatingBlade() {
        for (RotatingBlade rb : rotatingBlades) {
            Rectangle rotatingBladeRect = new Rectangle((int) rb.x, (int) rb.y, rb.width, rb.height);
            Rectangle playerRect = new Rectangle((int) player.x, (int) player.y, player.width, player.height);
            if (rotatingBladeRect.intersects(playerRect) && player.alive) {
                player.alive = false;
                for (int i = 0; i < 20; i++) {
                    particles.add(new Particle(player.x, player.y, 10, 10, Color.RED));
                }
                gameOver = true;
            }
        }
    }

    public void handlePlayerOnPlatforms() {
        player.onGround = false;
        for (int i = 0; i < platforms.size(); i++) {
            Platform platform = platforms.get(i);
            if (isPlayerOnPlatform(platform)) {
                player.onGround = true;
                player.velocitY = 0;
                player.y = platform.y - player.height;
                break;
            }
        }
    }

    public void handlePlayerOnCrates() {
        for (int i = 0; i < crates.size(); i++) {
            Crate crate = crates.get(i);
            if (isPlayerOnTopOfCrate(crate)) {
                player.onGround = true;
                player.velocitY = 0;
                player.y = crate.y - player.height;
                break;
            }
        }
    }

    public void updateGame() {
        // backgroundImg.update();
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update();
        }
        for (int i = 0; i < saws.size(); i++) {
            Saw saw = saws.get(i);
            saw.update();
        }
        for (int i = 0; i < blades.size(); i++) {
            Blade blade = blades.get(i);
            blade.update();
        }
        for (int i = 0; i < rotatingBlades.size(); i++) {
            RotatingBlade rotatingBlade = rotatingBlades.get(i);
            rotatingBlade.update();
        }

        handlePlayerCollsionWithSpike();
        handlePlayerCollsionWithSaw();
        handlePlayerCollsionWithBlades();
        handlePlayerCollsionWithRotatingBlade();
        handlePlayerCollsionWithJumpPads();
        handlePlayerCollsionWithFlag();
        handlePlayerCollsionWithCoin();
        particles.removeIf(particle -> particle.markedForDeletion);
        jumpPads.removeIf(jumpPad -> jumpPad.markedForDeletion);
        coins.removeIf(coin -> coin.markedForDeletion);
        if (player.alive) {
            player.update();
        }

        handlePlayerOnPlatforms();
        handlePlayerOnCrates();
        if ((particles.size() == 0 && gameOver) || player.y > windowHeight) {
            init();
        }
        if (player.moveLeft && player.x >= 100 || (player.moveLeft && scrollOffsetX == 0 && player.x > 0)) {
            player.x -= player.velocitX;
        } else if (player.moveRight && player.x <= 350) {
            player.x += player.velocitX;
        } else {
            if (player.moveLeft && scrollOffsetX > 0) {
                scrollOffsetX -= player.velocitX;
                platforms.forEach(platform -> {
                    platform.x += player.velocitX;
                });
                spikes.forEach(spike -> {
                    spike.x += player.velocitX;
                });
                saws.forEach(saw -> {
                    saw.x += player.velocitX;
                    saw.maxX += player.velocitX;
                    saw.minX += player.velocitX;
                });
                blades.forEach(blade -> {
                    blade.x += player.velocitX;
                });
                rotatingBlades.forEach(rotatingBlade -> {
                    rotatingBlade.x += player.velocitX;
                });
                jumpPads.forEach(jumpPad -> {
                    jumpPad.x += player.velocitX;
                });
                crates.forEach(crate -> {
                    crate.x += player.velocitX;
                });
                coins.forEach(coin -> {
                    coin.x += player.velocitX;
                });
                flag.x += player.velocitX;
                // backgroundImg.x += player.velocitX * 0.66;
            } else if (player.moveRight) {
                scrollOffsetX += player.velocitX;
                platforms.forEach(platform -> {
                    platform.x -= player.velocitX;
                });
                spikes.forEach(spike -> {
                    spike.x -= player.velocitX;
                });
                saws.forEach(saw -> {
                    saw.x -= player.velocitX;
                    saw.maxX -= player.velocitX;
                    saw.minX -= player.velocitX;
                });
                blades.forEach(blade -> {
                    blade.x -= player.velocitX;
                });
                rotatingBlades.forEach(rotatingBlade -> {
                    rotatingBlade.x -= player.velocitX;
                });
                jumpPads.forEach(jumpPad -> {
                    jumpPad.x -= player.velocitX;
                });
                crates.forEach(crate -> {
                    crate.x -= player.velocitX;
                });
                coins.forEach(coin -> {
                    coin.x -= player.velocitX;
                });
                flag.x -= player.velocitX;
                // backgroundImg.x -= player.velocitX * 0.66;
            }

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // backgroundImg.draw(g);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // g2d.setRenderingHint(
        // RenderingHints.KEY_INTERPOLATION,
        // RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (player.alive) {
            player.draw(g2d);
        }

        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.draw(g2d);
        }
        for (int i = 0; i < saws.size(); i++) {
            Saw saw = saws.get(i);
            saw.draw(g2d);
            // saw.debugDraw(g2d);
        }
        for (int i = 0; i < blades.size(); i++) {
            Blade blade = blades.get(i);
            blade.draw(g2d);
            // blade.debugDraw(g2d);
        }
        for (int i = 0; i < rotatingBlades.size(); i++) {
            RotatingBlade rotatingBlade = rotatingBlades.get(i);
            rotatingBlade.draw(g2d);
            // blade.debugDraw(g2d);
        }
        for (int i = 0; i < jumpPads.size(); i++) {
            JumpPad jumpPad = jumpPads.get(i);
            jumpPad.draw(g2d);
        }
        for (int i = 0; i < crates.size(); i++) {
            Crate crate = crates.get(i);
            crate.draw(g2d);
        }
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            coin.draw(g2d);
        }
        if (flag != null) {
            flag.draw(g2d);
        }
        platforms.forEach(platform -> {
            platform.draw(g2d);
            // platform.debugDraw(g2d);
            // platform.debugDraw(g);
        });
        spikes.forEach(spike -> {
            spike.draw(g2d);
        });

    }
}
