package com.ful.invaders.space;

import javax.swing.*;

/** @author Ful Al Sayab */
public class Alien extends Sprite {
    private Bomb bomb;

  public Alien(int x, int y) {

    initAlien(x, y);
  }

  private void initAlien(int x, int y) {

    this.x = x;
    this.y = y;

    bomb = new Bomb(x, y);

    var alienImg = "src/images/alien.png";
    var ii = new ImageIcon(alienImg);

    setImage(ii.getImage());
  }

  /**
   * The act() method is called from the Board class. It is used to position an alien in horizontal direction.
   * @param direction is the alien in horizontal direction.
   */
  public void act(int direction) {

    this.x += direction;
  }

  /**
   * The getBomb() method is called when the alien is about to drop a bomb
   * @return bomb
   */
  public Bomb getBomb() {

    return bomb;
  }

  public class Bomb extends Sprite {

    private boolean destroyed;

    public Bomb(int x, int y) {

      initBomb(x, y);
    }

    private void initBomb(int x, int y) {

      setDestroyed(true);

      this.x = x;
      this.y = y;

      var bombImg = "src/images/bomb.png";
      var ii = new ImageIcon(bombImg);
      setImage(ii.getImage());
    }

    public boolean isDestroyed() {

      return destroyed;
    }

    public void setDestroyed(boolean destroyed) {

      this.destroyed = destroyed;
    }
  }
}
