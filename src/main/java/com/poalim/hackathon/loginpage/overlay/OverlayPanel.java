package com.poalim.hackathon.loginpage.overlay;

import javax.swing.*;
import java.awt.*;

public class OverlayPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final Robot robot;

    public OverlayPanel() {
        // Create Robot to capture screen events
        Robot robot;
        try {
            robot = new Robot();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.robot = robot;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Load the background image from file
        Image backgroundImage = new ImageIcon("C:\\Users\\temp06\\IdeaProjects\\LockScreen\\src\\main\\resources\\logo.png").getImage();
        // Draw the image on the panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void startRobot() {
        // Start robot thread to capture screen events
        new Thread(() -> {
            while (true) {
                repaint();
            }
        }).start();
    }
}
