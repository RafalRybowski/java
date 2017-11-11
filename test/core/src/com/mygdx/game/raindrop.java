package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;


import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by epiklp on 08.11.17.
 */

public class raindrop implements Interface {
    private Rectangle drop;
    private float speed;
    private float movex;
    private float rand;
    private float rotation;

    public raindrop(float wind)
    {
        drop = new Rectangle();
        if(wind < 0)
            drop.x = random(screenWithStart, Width - (screenWithStart-64)-wind*2);
        else
            drop.x = random(screenWithStart-64-wind*2, Width -(screenWithStart+64));
        drop.y = Height + 40;
        drop.width = 64;
        drop.height = 64;
        rand = (random(50, 300));
        speed = rand * Gdx.graphics.getDeltaTime();

        if(wind > 0) {
            rotation = wind - rand/20;
            if(rotation <=0)
                rotation = 0;;
        }
        else if(wind < 0)
            {
                rotation = wind + rand/20;
                if(rotation >=0)
                    rotation = 0;
            }

        movex = wind*Gdx.graphics.getDeltaTime();
    }

    public float getX()
    {
        return drop.x;
    }

    public float getY()
    {
        return drop.y;
    }

    public void setY()
    {
        drop.y -= speed;
    }
    public void setX()
    {
        drop.x += movex;
    }
    public void setX(float value)
    {
        drop.x = value + movex;
    }


    public float getRotation() {
        return rotation;
    }

    public Rectangle getDrop()
    {
        return drop;
    }
}
