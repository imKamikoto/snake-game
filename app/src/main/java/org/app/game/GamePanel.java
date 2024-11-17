package org.app.game;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

  // window
  private final int WINDOW_WIDTH = 600;
  private final int WINDOW_HEIGHT = 600;
  private final int POINT_WINDOW_WIDTH = 600;
  private final int POINT_WINDOW_HEIGHT = 500;

  // game units
  private final int UNIT_SIZE = 20;
  private final int MAX_UNITS = (WINDOW_WIDTH * WINDOW_HEIGHT) / UNIT_SIZE;

  // delay
  private final static int DELAY = 75;

  // snake
  int snakeX[] = new int[MAX_UNITS];
  int snakeY[] = new int[MAX_UNITS];
  int snakeLength = 4;

  // point that we gonna collect
  int counter;
  boolean newLenght = false;
  int pointX;
  int pointY;

  boolean running = false;
  char direction = 'D';

  private Timer timer;
  private Random random;

  private AppFrame mainFrame;

  public GamePanel(AppFrame frame) {
    random = new Random();
    mainFrame = frame;

    this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    this.setBackground(new Color(75, 80, 80));
    this.setFocusable(true);
    this.addKeyListener(new MyKeyListener());

  }

  public void startGame() {

    createPoint();
    running = true;
    timer = new Timer(DELAY, this);
    timer.start();
    snakeX[0] = 300;
    snakeY[0] = 300;
    this.setRequestFocusEnabled(true);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }

  private void draw(Graphics graphics) {

    graphics.drawString("SCORE: " + counter, WINDOW_WIDTH - 327, 15);

    graphics.setColor(new Color(50, 50, 50));
    if (running) {
      for (int i = 0; i < snakeLength; i++) {
        graphics.setColor(new Color(148, 143, 132));
        if (i == 0) {
          drawHead(graphics, i);
        } else if (i == snakeLength - 1 && newLenght) {
          graphics.setColor(new Color(123, 165, 59));
          graphics.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
        } else {
          graphics.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
        }
      }

      newLenght = false;

      graphics.setColor(new Color(245, 242, 237));
      graphics.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);
    } else {
      gameOver(graphics);
    }
  }

  private void move() {
    for (int i = snakeLength; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }

    switch (direction) {
      case 'U':
        snakeY[0] = snakeY[0] - UNIT_SIZE;
        break;
      case 'D':
        snakeY[0] = snakeY[0] + UNIT_SIZE;
        break;
      case 'L':
        snakeX[0] = snakeX[0] - UNIT_SIZE;
        break;
      case 'R':
        snakeX[0] = snakeX[0] + UNIT_SIZE;
        break;
    }
  }

  private void createPoint() {
    pointX = random.nextInt((int) (POINT_WINDOW_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    pointY = random.nextInt((int) (POINT_WINDOW_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
  }

  private void checkPoint() {
    if (snakeX[0] == pointX && snakeY[0] == pointY) {
      counter++;
      snakeLength++;
      newLenght = true;
      createPoint();
    }
  }

  private void checkCollision() {
    // right and left side collision
    if ((snakeX[0] > WINDOW_WIDTH - 20) || (snakeX[0] < 0)) {
      running = false;
    }

    // top and bottom side collision
    if ((snakeY[0] > WINDOW_HEIGHT - 40) || (snakeY[0] < 20)) {
      running = false;
    }

    // collision with part of body
    for (int i = 1; i < snakeLength; i++) {
      if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
        running = false;
        break;
      }
    }

    if (!running) {
      timer.stop();
    }
  }

  private void gameOver(Graphics g) {
    mainFrame.showGameOverPanel(counter);
  }

  private void drawHead(Graphics graphics, int i) {
    graphics.fillOval(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE); // oval head

    int squareSize = UNIT_SIZE / 2;

    switch (direction) {
      case 'U':
        graphics.fillRect(snakeX[i], snakeY[i] + squareSize, squareSize, UNIT_SIZE - squareSize); // bottom left
        graphics.fillRect(snakeX[i] + squareSize, snakeY[i] + squareSize, squareSize, UNIT_SIZE - squareSize); // bottom
                                                                                                               // right
        break;
      case 'D':
        graphics.fillRect(snakeX[i], snakeY[i], squareSize, UNIT_SIZE - squareSize); // up left
        graphics.fillRect(snakeX[i] + squareSize, snakeY[i], squareSize, UNIT_SIZE - squareSize); // up right
        break;
      case 'L':
        graphics.fillRect(snakeX[i] + squareSize, snakeY[i], UNIT_SIZE - squareSize, squareSize); // top right
        graphics.fillRect(snakeX[i] + squareSize, snakeY[i] + squareSize, UNIT_SIZE - squareSize, squareSize); // bottom
                                                                                                               // right
        break;
      case 'R':
        graphics.fillRect(snakeX[i], snakeY[i], UNIT_SIZE - squareSize, squareSize); // top left
        graphics.fillRect(snakeX[i], snakeY[i] + squareSize, UNIT_SIZE - squareSize, squareSize); // bottom left
        break;
    }
  }

  // detect action
  @Override
  public void actionPerformed(ActionEvent event) {
    if (running) {
      move();
      checkPoint();
      checkCollision();
    }
    repaint();
  }

  public class MyKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
          if (direction != 'D') {
            direction = 'U';
          }
          break;
        case KeyEvent.VK_DOWN:
          if (direction != 'U') {
            direction = 'D';
          }
          break;
        case KeyEvent.VK_LEFT:
          if (direction != 'R') {
            direction = 'L';
          }
          break;
        case KeyEvent.VK_RIGHT:
          if (direction != 'L') {
            direction = 'R';
          }
          break;
      }
    }

  }
}
