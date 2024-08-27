package Learn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private final int SIZE = 20, WIDTH = 400, HEIGHT = 400;
    private int[] x = {100, 80, 60}, y = {100, 100, 100};
    private int appleX = 200, appleY = 200, direction = KeyEvent.VK_RIGHT;
    private Timer timer = new Timer(100, this);

    public SnakeGame() {
        try {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.BLACK);
            setFocusable(true);
            addKeyListener(this);
            timer.start();
            Logger.info("Learn.SnakeGame", "Game started.");
        } catch (Exception e) {
            Logger.fatal("Learn.SnakeGame", "Critical error during initialization: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(appleX, appleY, SIZE, SIZE);
        g.setColor(Color.GREEN);
        for (int i = 0; i < x.length; i++) g.fillRect(x[i], y[i], SIZE, SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int prevX = x[0], prevY = y[0];
            int tempX, tempY;
            switch (direction) {
                case KeyEvent.VK_LEFT: x[0] -= SIZE; break;
                case KeyEvent.VK_RIGHT: x[0] += SIZE; break;
                case KeyEvent.VK_UP: y[0] -= SIZE; break;
                case KeyEvent.VK_DOWN: y[0] += SIZE; break;
            }

            // Handle border wrap
            if (x[0] < 0) x[0] = WIDTH - SIZE;
            if (x[0] >= WIDTH) x[0] = 0;
            if (y[0] < 0) y[0] = HEIGHT - SIZE;
            if (y[0] >= HEIGHT) y[0] = 0;

            // Move the snake body
            for (int i = 1; i < x.length; i++) {
                tempX = x[i]; tempY = y[i];
                x[i] = prevX; y[i] = prevY;
                prevX = tempX; prevY = tempY;
            }

            // Check for collision with itself
            for (int i = 1; i < x.length; i++) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    Logger.info("Learn.SnakeGame", "Game over: Snake collided with itself.");
                    JOptionPane.showMessageDialog(this, "You Lose!", "Snake Game", JOptionPane.INFORMATION_MESSAGE);

                    System.exit(0);
                }
            }

            // Check for apple collision
            if (x[0] == appleX && y[0] == appleY) {
                appleX = (int) (Math.random() * (WIDTH / SIZE)) * SIZE;
                appleY = (int) (Math.random() * (HEIGHT / SIZE)) * SIZE;
                x = java.util.Arrays.copyOf(x, x.length + 1);
                y = java.util.Arrays.copyOf(y, y.length + 1);
                Logger.info("Learn.SnakeGame", "Apple eaten: New apple position (" + appleX + "," + appleY + ")");
            }

            repaint();
        } catch (Exception ex) {
            Logger.fatal("Learn.SnakeGame", "Critical error during game update: " + ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newDirection = e.getKeyCode();
        if (newDirection == KeyEvent.VK_W) newDirection = KeyEvent.VK_UP;
        if (newDirection == KeyEvent.VK_S) newDirection = KeyEvent.VK_DOWN;
        if (newDirection == KeyEvent.VK_A) newDirection = KeyEvent.VK_LEFT;
        if (newDirection == KeyEvent.VK_D) newDirection = KeyEvent.VK_RIGHT;

        // Check if the new direction is valid
        if (Math.abs(newDirection - direction) != KeyEvent.VK_UP - KeyEvent.VK_DOWN &&
                Math.abs(newDirection - direction) != KeyEvent.VK_LEFT - KeyEvent.VK_RIGHT &&
                !willHitSnake(newDirection)) {
            direction = newDirection;
            Logger.info("Learn.SnakeGame", "Direction changed to: " + direction);
        }
    }

    private boolean willHitSnake(int newDirection) {
        int newX = x[0], newY = y[0];
        switch (newDirection) {
            case KeyEvent.VK_LEFT: newX -= SIZE; break;
            case KeyEvent.VK_RIGHT: newX += SIZE; break;
            case KeyEvent.VK_UP: newY -= SIZE; break;
            case KeyEvent.VK_DOWN: newY += SIZE; break;
        }
        for (int i = 0; i < x.length; i++) {
            if (newX == x[i] && newY == y[i]) return true;
        }
        return false;
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SnakeGame());
            frame.pack();
            frame.setVisible(true);
            Logger.info("Learn.SnakeGame", "Game window initialized and visible.");
        } catch (Exception e) {
            Logger.fatal("Learn.SnakeGame", "Critical error during window initialization: " + e.getMessage());
            System.exit(1);
        }
    }
}
