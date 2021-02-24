package com.ful.invaders.space;

import javax.swing.*;
import java.awt.EventQueue;


/** @author Ful Al Sayab */
public class SpaceInvaders extends JFrame {
    public SpaceInvaders(){
        initUI();
    }

    private void initUI() {
        add(new Board());
    }
}
