package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by epiklp on 11.11.17.
 */

public interface Interface {
    int Width = Gdx.graphics.getWidth();
    int Height =  Gdx.graphics.getHeight();
    int scores = 0;
    long times = 2147483647;
    int nums = 1000000;
    int timer = 110;
    float screenWithStart = Width/5;
    float screenWithEnd = Width - screenWithStart;

}
