package com.logorigm;

import com.badlogic.gdx.Input;

public class PlayerControls {
    final int up;
    final int down;
    final int left;
    final int right;

    public PlayerControls(int up, int down, int left, int right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
    public static final PlayerControls p1 = new PlayerControls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D);
}
