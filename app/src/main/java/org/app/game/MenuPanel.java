package org.app.game;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(AppFrame frame) {

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(75, 80, 80));

        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(e -> frame.showGamePanel());
        exitButton.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        this.add(startButton, gbc);

        gbc.gridx = 1;
        this.add(exitButton, gbc);
    }
}
