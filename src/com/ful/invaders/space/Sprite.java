package com.ful.invaders.space;

import java.awt.*;

/**
 * This is the Shot sprite. The shot is triggered with the Space key. The H_SPACE and the V_SPACE
 * constants are used to position the missile appropriately.
 *
 * @author Ful Al Sayab
 */
public class Sprite {
  int x;
  int y;
  int dx;
  private boolean visible;
  private Image image;
  private boolean dying;

  /** Constructor to create Sprite opject */
  public Sprite() {
    visible = true;
  }

  public boolean isVisible() {

    return visible;
  }

  /**
   * Sets setVisible
   *
   * @param visible boolean value
   */
  protected void setVisible(boolean visible) {

    this.visible = visible;
  }

  /**
   * Gets image
   *
   * @return image
   */
  public Image getImage() {

    return image;
  }

  /**
   * Sets Image
   *
   * @param image to be set
   */
  public void setImage(Image image) {

    this.image = image;
  }

  /**
   * Gets y
   *
   * @return y
   */
  public int getY() {

    return y;
  }

  /**
   * Set y
   *
   * @param y to be set
   */
  public void setY(int y) {

    this.y = y;
  }

  /**
   * Gets x
   *
   * @return x
   */
  public int getX() {

    return x;
  }

  /**
   * Sets x
   *
   * @param x to be set
   */
  public void setX(int x) {

    this.x = x;
  }

  /**
   * Gets the boolean value of dying
   *
   * @return true if dying, false otherwise\
   */
  public boolean isDying() {

    return this.dying;
  }

  /**
   * Sets dying
   *
   * @param dying boolean value for dying
   */
  public void setDying(boolean dying) {

    this.dying = dying;
  }

  public void die() {

    visible = false;
  }
}
