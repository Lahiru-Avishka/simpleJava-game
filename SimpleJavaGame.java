import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleJavaGame extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private int playerX = 50;
    private int playerY = 250;
    private int enemyX = 500;
    private int enemyY = 250;
    private int playerSpeed = 15; // Increased player speed to make it easier
    private int enemySpeed = 3;  // Reduced enemy speed to make it easier
    private boolean gameRunning = true;

    public SimpleJavaGame() {
        this.setPreferredSize(new Dimension(600, 400));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameRunning) {
            // Draw the player
            g.setColor(Color.GREEN);
            g.fillRect(playerX, playerY, 30, 30);

            // Draw the enemy
            g.setColor(Color.RED);
            g.fillRect(enemyX, enemyY, 30, 30);

            // Draw boundaries
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, 599, 399);
        } else {
            // Game Over screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over!", 200, 200);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            // Move enemy towards the player
            if (enemyX > playerX) {
                enemyX -= enemySpeed;
            } else if (enemyX < playerX) {
                enemyX += enemySpeed;
            }

            if (enemyY > playerY) {
                enemyY -= enemySpeed;
            } else if (enemyY < playerY) {
                enemyY += enemySpeed;
            }

            // Check for collision
            if (new Rectangle(playerX, playerY, 30, 30).intersects(new Rectangle(enemyX, enemyY, 30, 30))) {
                gameRunning = false;
                timer.stop();
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameRunning) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    playerY = Math.max(playerY - playerSpeed, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    playerY = Math.min(playerY + playerSpeed, this.getHeight() - 30);
                    break;
                case KeyEvent.VK_LEFT:
                    playerX = Math.max(playerX - playerSpeed, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    playerX = Math.min(playerX + playerSpeed, this.getWidth() - 30);
                    break;
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No actions on key release
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No actions on key typed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Java Game");
        SimpleJavaGame game = new SimpleJavaGame();

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
