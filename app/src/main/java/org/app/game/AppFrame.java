package org.app.game;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    private CardLayout cardLayout;

    private GameOverPanel gameOverPanel;
    private JPanel mainPanel;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;

    public AppFrame() {

        this.setTitle("Snake Game");
        this.setSize(new Dimension(600, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);

        mainPanel.add(menuPanel, "Menu");

        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        showMenuPanel();
    }

    public void showMenuPanel() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void showGamePanel() {
        gamePanel = new GamePanel(this);
        mainPanel.add(gamePanel, "Game");

        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
        gamePanel.startGame();
    }

    public void showGameOverPanel(int score) {

        gameOverPanel = new GameOverPanel(this, score);
        mainPanel.add(gameOverPanel, "GameOver");

        cardLayout.show(mainPanel, "GameOver");
    }
}
