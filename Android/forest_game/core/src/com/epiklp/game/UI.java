package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by epiklp on 19.11.17.
 */

public class UI implements Interface{
    private BitmapFont bitmapFont;
    private Label text;
    private Label.LabelStyle labelStyle;
    private Stage stage;
    private Texture liveTexture, magicTexture;
    private SpriteBatch batch;

    public UI()
    {
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        text = new Label("fps:", labelStyle);
        stage = new Stage();
        text.setPosition(0, height-100);
        stage.addActor(text);
        liveTexture = CreateTexture(100, 32, 1,0,0,1);
        magicTexture =  CreateTexture(100,32,0,0,1,1);
    }

    private Texture CreateTexture(int width, int hight, int r,int g,int b,int a)
    {
        Texture tmp;
        Pixmap pixmap = new Pixmap(width*4, hight, Pixmap.Format.RGB888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fillRectangle(0, 0, width*4, hight);
        tmp = new Texture(pixmap);
        pixmap.dispose();
        return tmp;
    }

    public void draw(int live, int magic)
    {
        update(live, magic);
        batch.begin();
        batch.draw(liveTexture,0,  height - 64 );
        batch.draw(magicTexture,0,  height - 112 );
        batch.end();
        text.setText("fps: " + Gdx.graphics.getFramesPerSecond());
        stage.act();
        stage.draw();
    }

    public void update(int live, int magic)
    {
        liveTexture.dispose();
        magicTexture.dispose();
        liveTexture = CreateTexture(live, 32, 1,0,0,1);
        magicTexture =  CreateTexture(magic,32,0,0,1,1);
    }

    public void dispose()
    {
        liveTexture.dispose();
        magicTexture.dispose();
        stage.dispose();

    }

}
