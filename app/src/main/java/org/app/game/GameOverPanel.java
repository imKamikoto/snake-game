package org.app.game;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

  private JButton backInMenu;
  private JButton saveRes;

  private JLabel gameOverMsg;
  private JLabel gameScoreMsg;

  private ResultSaver resSaver;

  public GameOverPanel(AppFrame frame, int score) {

    resSaver = new ResultSaver();

    this.setLayout(new GridBagLayout());
    this.setBackground(new Color(75, 80, 80));

    saveRes = new JButton("Save Result");
    backInMenu = new JButton("Menu");

    gameOverMsg = new JLabel("Game Over");
    gameScoreMsg = new JLabel("Score: " + score);

    backInMenu.addActionListener(e -> frame.showMenuPanel());
    saveRes.addActionListener(e -> resSaver.saveResult(score));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    this.add(gameOverMsg, gbc);
    gbc.gridy = 1;
    this.add(gameScoreMsg, gbc);

    gbc.gridwidth = 1;
    gbc.gridy = 2;
    gbc.insets = new Insets(5, 10, 5, 10);

    gbc.gridx = 0;
    this.add(backInMenu, gbc);
    gbc.gridx = 1;
    this.add(saveRes, gbc);
  }
}
