package com.poalim.hackathon.loginpage.overlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class LegacyLoginScreen implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JPasswordField passwordField;
    private JButton button;
    JLabel pinLabel;

    public LegacyLoginScreen() {
        // Create the login screen components
        frame = new JFrame("Custom Login Screen");
        panel = new JPanel(new GridBagLayout());
        label = new JLabel("AuthMe");
        pinLabel = new JLabel("Enter PIN code:");
        passwordField = new JPasswordField(10);
        button = new JButton("Submit");
        button.addActionListener(this);

        // Set the font of the components to Arial size 24
        Font font = new Font("Arial", Font.PLAIN, 24);
        pinLabel.setFont(font);
        passwordField.setFont(font);
        button.setFont(font);
        // Set the font of the "AuthMe" label to Arial size 48
        Font authMeFont = new Font("Arial", Font.PLAIN, 48);
        label.setFont(authMeFont);

        // Center the pincode field and submit button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        gbc.gridy = 1;
        panel.add(pinLabel, gbc);

        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.gridy = 3;
        panel.add(button, gbc);

        frame.add(panel);

        // Remove the frame's decorations
        frame.setUndecorated(true);
        // Prevent the user from resizing the frame
        frame.setResizable(false);
        // Set the login screen size to the current screen resolution
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        frame.setSize(screenWidth, screenHeight);
        // Set the login screen to always be on top
        frame.setAlwaysOnTop(true);
        // Disable Alt+Tab window switching
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                return e.getKeyCode() == KeyEvent.VK_TAB && e.isAltDown();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent event) {
        if (new String(passwordField.getPassword()).equals("112233")) {
            JOptionPane.showMessageDialog(frame, "Welcome!");
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid PIN code");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        LegacyLoginScreen loginScreen = new LegacyLoginScreen();
    }
}