package Learn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window {
    private int squareX = 100;  // Initial x position of the square
    private int squareY = 100;  // Initial y position of the square

    // Method to start a new window with the specified title
    public void StartWindow(String WindowTitle) throws InterruptedException {
        Logger.info("WindowHandler", "Starting window with title: " + WindowTitle);

        // Create a new JFrame instance
        JFrame frame = new JFrame(WindowTitle);
        Logger.info("WindowHandler", "JFrame created with title: " + WindowTitle);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Logger.info("WindowHandler", "Default close operation set.");
        System.out.println("If it says certain things twice, ignore its fine.");
        TimeUnit.SECONDS.sleep(3);

        // Create a JPanel to hold the components
        JPanel Mainpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Set the color for the cube
                g.setColor(Color.BLUE);
                // Draw the cube (rectangle) at position (squareX, squareY)
                g.fillRect(squareX, squareY, 50, 50);
            }
        };
        Logger.info("WindowHandler", "JPanel Mainpanel created.");

        // Set the JPanel to be focusable and request focus
        Mainpanel.setFocusable(true);
        Mainpanel.requestFocusInWindow();

        // Add a KeyListener to handle key events
        Mainpanel.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Update position based on arrow keys
                if (keyCode == KeyEvent.VK_RIGHT) {
                    squareX += 5;  // Move square to the right
                    Logger.info("WindowHandler - KeyListener", "VK_RIGHT Pressed.");
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    squareY += 5;  // Move square down
                    Logger.info("WindowHandler - KeyListener", "VK_DOWN Pressed.");
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    squareX -= 5;  // Move square left
                    Logger.info("WindowHandler - KeyListener", "VK_LEFT Pressed.");
                } else if (keyCode == KeyEvent.VK_UP) {
                    squareY -= 5;  // Move square up
                    Logger.info("WindowHandler - KeyListener", "VK_UP Pressed.");
                }

                // Repaint the panel to reflect the changes
                Mainpanel.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // No action needed on key release
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // No action needed on key typed
            }
        });

        Logger.info("WindowHandler", "ActionListener added to testButton.");

        // Create a JButton
        JButton testButton = new JButton("testing");
        Logger.info("WindowHandler", "JButton testButton created.");

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.info("ButtonHandler", "Test button was clicked.");
            }
        });

        // Add the button to the panel
        Mainpanel.add(testButton);
        Logger.info("WindowHandler", "JButton testButton added to JPanel Mainpanel.");

        // Add the panel to the frame
        frame.add(Mainpanel);
        Logger.info("WindowHandler", "JPanel Mainpanel added to JFrame.");

        // Set the size of the window
        frame.setSize(400, 300);
        Logger.info("WindowHandler", "JFrame size set to 400x300.");

        // Make the window visible
        frame.setVisible(true);
        Logger.info("WindowHandler", "JFrame set to visible.");
    }

    public static void main(String[] args) throws InterruptedException {
        new Window().StartWindow("KeyListener Example");
    }
}
