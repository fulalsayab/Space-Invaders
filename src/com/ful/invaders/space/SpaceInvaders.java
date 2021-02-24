package com.ful.invaders.space;

import javax.swing.*;
import java.awt.*;

public class SpaceInvaders extends JFrame {

  public SpaceInvaders() {

    initUI();
  }
  /**
   * Main function that starts the program.
   */
  public static void main(String[] args) {

    EventQueue.invokeLater(
        () -> {
          var ex = new SpaceInvaders();
          ex.setVisible(true);
        });
  }

  private void initUI() {

    add(new Board());

    setTitle("Space Invaders");
    setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
  }
}
