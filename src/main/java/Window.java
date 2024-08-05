import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    // Method to start a new window with the specified title
    public void StartWindow(String WindowTitle) {
        Logger.info("WindowHandler", "Starting window with title: " + WindowTitle);

        // Create a new JFrame instance
        JFrame frame = new JFrame(WindowTitle);
        Logger.info("WindowHandler", "JFrame created with title: " + WindowTitle);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Logger.info("WindowHandler", "Default close operation set.");

        // Create a JPanel to hold the components
        JPanel Mainpanel = new JPanel();
        Logger.info("WindowHandler", "JPanel Mainpanel created.");

        // Create a JButton
        JButton testButton = new JButton("testing");
        Logger.info("WindowHandler", "JButton testButton created.");

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.info("ButtonHandler", "Test button was clicked.");
            }
        });
        Logger.info("WindowHandler", "ActionListener added to testButton.");

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
}