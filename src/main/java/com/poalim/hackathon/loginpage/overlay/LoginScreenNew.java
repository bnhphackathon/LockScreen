package com.poalim.hackathon.loginpage.overlay;

import com.poalim.hackathon.loginpage.service.AuthService;
import com.poalim.hackathon.loginpage.service.AuthServiceHttpImpl;
import com.sun.jna.platform.win32.User32;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class LoginScreenNew extends JFrame implements MouseMotionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private final OverlayPanel overlayPanel;
    private final JPasswordField passwordField;
    private final JTextField userField;
    private final JButton unlockButton;
    private AuthService authService;
    private Timer timer;

    public LoginScreenNew() {
        // Set JFrame properties
        super("Overlay Frame");

        // Construct authentication service
        authService = new AuthServiceHttpImpl();

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 128));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set OverlayPanel as content pane
        overlayPanel = new OverlayPanel();
        setContentPane(overlayPanel);

        // Get screen size and set OverlayFrame size to match it
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(new Rectangle(screenSize));

        // Make OverlayFrame always on top
        setAlwaysOnTop(true);

        // Add mouse and keyboard listeners
        addMouseMotionListener(this);
        addKeyListener(this);

        // Add user field
        JPanel userPanel = new JPanel();
        JLabel userLabel = new JLabel("User Name: ");
        userField = new JTextField(10);
        userField.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font size
        userPanel.add(userLabel);
        userPanel.add(userField);

        // Add password field
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("OTP: ");
        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font size
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Addd unlock button
        unlockButton = new JButton("Unlock");
        unlockButton.addActionListener(e -> unlockOverlay());
        unlockButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font size
        passwordPanel.add(unlockButton);

        // Add passwordPanel to Box and center it vertically on screen
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalGlue());
        box.add(userPanel);
        box.add(passwordPanel);
        box.add(unlockButton);
        box.add(Box.createVerticalGlue());
        add(box, BorderLayout.CENTER);

        // Center OverlayFrame on screen horizontally
        setLocationRelativeTo(null);

        // Start robot to capture screen events
        overlayPanel.startRobot();
    }

    private void unlockOverlay() {
        String username = userField.getText();
        char[] passwordChars = passwordField.getPassword();
        String otp = new String(passwordChars);
        if (username.isBlank()) {
            JOptionPane.showMessageDialog(this, "Must provide username", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                if (authService.isValidUserAndOtp(username, otp)) {
                    setVisible(false);
                    JOptionPane.showMessageDialog(this, "Welcome", "Hello!", JOptionPane.INFORMATION_MESSAGE);

                    // Stop and restart the timer when the screen is unlocked
                    if (timer != null) {
                        timer.stop();
                    }
                    timer = new Timer(3600000, e -> {
                        setVisible(true);
                        passwordField.setText("");
                    });
                    timer.setRepeats(false);
                    timer.start();

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Reset the timer when the mouse is moved
        System.out.println("EVENT + " + System.currentTimeMillis());
        if (timer != null) {
            timer.restart();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Reset the timer when the mouse is dragged
        if (timer != null) {
            timer.restart();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Reset the timer when a key is pressed
        if (timer != null) {
            timer.restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (timer != null) {
            timer.restart();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }
}
