package com.ful.invaders.space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/** @author Ful Al Sayab */
public class Board extends JPanel {
  private final String explosionImg = "src/images/explosion.png";
  private int direction = -1;
  private int death = 0;
  private boolean isInGame = true;
  private String message = "Game Over";
  private Dimension dimension;
  private List<Alien> aliens;
  private Player player;
  private Shot shot;
  private Timer timer;

  public Board() {
    initBoard();
    gameInit();
  }

  private void initBoard() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    dimension = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
    setBackground(Color.BLACK);
    timer = new Timer(Commons.DELAY, new GameCycle());
    gameInit();
  }

  /**
   * In the gameInit() method we create 24 aliens. The alien image size is 12x12px. We put 6px space
   * among the aliens. We also create the player and the shot objects.
   */
  private void gameInit() {
    aliens = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 6; j++) {
        var alien = new Alien(Commons.ALIEN_INIT_X + 18 * j, Commons.ALIEN_INIT_Y + 18 * i);
        aliens.add(alien);
      }
    }
    player = new Player();
    shot = new Shot();
  }

  /**
   * Draws the alien graphics
   *
   * @param graphics the alien graphics
   */
  private void drawAliens(Graphics graphics) {
    for (Alien alien : aliens) {
      if (alien.isVisible()) {
        graphics.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
      }

      if (alien.isDying()) alien.die();
    }
  }

  /**
   * Draws the player graphics
   *
   * @param graphics the player graphics
   */
  private void drawPlayer(Graphics graphics) {
    if (player.isVisible()) {
      graphics.drawImage(player.getImage(), player.getX(), player.getY(), this);

      if (player.isDying()) player.die();
    }
  }

  /**
   * Draws the shot graphics
   *
   * @param graphics the shot graphics
   */
  private void drawShot(Graphics graphics) {
    if (shot.isVisible()) {
      graphics.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
    }
  }

  /**
   * The drawBombing() method draws bombs launched by the aliens.
   *
   * @param graphics the bomb graphics
   */
  private void drawBombing(Graphics graphics) {
    for (Alien alien : aliens) {
      Alien.Bomb bomb = alien.getBomb();
      if (!bomb.isDestroyed()) {
        graphics.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
      }
    }
  }

  /**
   * Inside the doDrawing() method, we draw the ground, the aliens, the player, the shot, and the
   * bombs.
   *
   * @param graphics to be done
   */
  private void doDrawing(Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    graphics.setColor(Color.GREEN);
    if (isInGame) {

      graphics.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
      drawAliens(graphics);
      drawPlayer(graphics);
      drawShot(graphics);
      drawBombing(graphics);

    } else {
      if (timer.isRunning()) {
        timer.stop();
      }
      gameOver(graphics);
    }
    Toolkit.getDefaultToolkit().sync();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    doDrawing(g);
  }

  /**
   * Game over graphics
   *
   * @param graphics for game over
   */
  private void gameOver(Graphics graphics) {
    graphics.setColor(Color.black);
    graphics.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

    graphics.setColor(new Color(0, 32, 48));
    graphics.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
    graphics.setColor(Color.white);
    graphics.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

    var small = new Font("Helvetica", Font.BOLD, 14);
    var fontMetrics = this.getFontMetrics(small);

    graphics.setColor(Color.white);
    graphics.setFont(small);
    graphics.drawString(
        message,
        (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
        Commons.BOARD_WIDTH / 2);
  }

  /**
   * Inside the update() method we check the number of destroyed aliens. If we destroy all aliens,
   * we win the game.
   */
  private void update() {
    if (death == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
      isInGame = false;
      timer.stop();
      message = "YOU WIN!!";
    }
    // player
    player.act();

    // shot
    if (shot.isVisible()) {
      int shotX = shot.getX();
      int shotY = shot.getY();

      for (Alien alien : aliens) {
        int alienX = alien.getX();
        int alienY = alien.getY();

        if (alien.isVisible() && shot.isVisible()) {
          if (shotX >= (alienX)
              && shotX <= (alienX + Commons.ALIEN_WIDTH)
              && shotY >= (alienY)
              && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

            var ii = new ImageIcon(explosionImg);
            alien.setImage(ii.getImage());
            alien.setDying(true);
            death++;
            shot.die();
          }
        }
      }

      int y = shot.getY();
      y -= 4;

      if (y < 0) {
        shot.die();
      } else {
        shot.setY(y);
      }
    }

    // aliens

    for (Alien alien : aliens) {

      int x = alien.getX();

      if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {

        direction = -1;

        // If the shot triggered by the player collides with an alien, the alien ship is destroyed.
        // More precisely, the dying flag is set. We use it to display an explosion. The deaths
        // variable increases and the shot sprite is destroyed.
        Iterator<Alien> i1 = aliens.iterator();

        while (i1.hasNext()) {

          Alien a2 = i1.next();
          a2.setY(a2.getY() + Commons.GO_DOWN);
        }
      }

      // If the aliens reach the right end of the Board, they move down and change their direction
      // to
      // the left.
      if (x <= Commons.BORDER_LEFT && direction != 1) {

        direction = 1;

        Iterator<Alien> i2 = aliens.iterator();

        while (i2.hasNext()) {

          Alien a = i2.next();
          a.setY(a.getY() + Commons.GO_DOWN);
        }
      }
    }

    // This code moves aliens. If they reach the bottom, the invasion begins.
    Iterator<Alien> it = aliens.iterator();

    while (it.hasNext()) {

      Alien alien = it.next();

      if (alien.isVisible()) {

        int y = alien.getY();

        if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
          isInGame = false;
          message = "Invasion!";
        }

        alien.act(direction);
      }
    }

    // bombs
    var generator = new Random();

    for (Alien alien : aliens) {

      int shot = generator.nextInt(15);
      Alien.Bomb bomb = alien.getBomb();

      // This is the code that determines whether the alien will drop a bomb. The alien must not be
      // destroyed; i.e. he must be visible. The bomb's destroyed flag must be set. In other words,
      // it is the alien's first bomb dropping or the previous dropped bomb already hit the ground.
      // If these two conditions are fulfilled, the bombing is left to the chance.
      if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

        bomb.setDestroyed(false);
        bomb.setX(alien.getX());
        bomb.setY(alien.getY());
      }

      int bombX = bomb.getX();
      int bombY = bomb.getY();
      int playerX = player.getX();
      int playerY = player.getY();

      if (player.isVisible() && !bomb.isDestroyed()) {

        if (bombX >= (playerX)
            && bombX <= (playerX + Commons.PLAYER_WIDTH)
            && bombY >= (playerY)
            && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

          var ii = new ImageIcon(explosionImg);
          player.setImage(ii.getImage());
          player.setDying(true);
          bomb.setDestroyed(true);
        }
      }

      // If the bomb is not destroyed, it goes 1 px to the ground. If it hits the bottom, the
      // destroyed flag is set. The alien is now ready to drop another bomb.
      if (!bomb.isDestroyed()) {

        bomb.setY(bomb.getY() + 1);

        if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

          bomb.setDestroyed(true);
        }
      }
    }
  }

  private void doGameCycle() {

    update();
    repaint();
  }

  /** Inner Class for TAdapter that implements KeyListener */
  private class TAdapter implements KeyListener {
    /**
     * Invoked when a key has been typed. See the class description for {@link KeyEvent} for a
     * definition of a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Invoked when a key has been pressed. See the class description for {@link KeyEvent} for a
     * definition of a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
      player.keyPressed(e);

      int x = player.getX();
      int y = player.getY();

      int key = e.getKeyCode();

      if (key == KeyEvent.VK_SPACE) {
        if (isInGame) {
          if (!shot.isVisible()) {
            shot = new Shot(x, y);
          }
        }
      }
    }

    /**
     * Invoked when a key has been released. See the class description for {@link KeyEvent} for a
     * definition of a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
      player.keyReleased(e);
    }
  }

  private class GameCycle implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      doGameCycle();
    }
  }
}
