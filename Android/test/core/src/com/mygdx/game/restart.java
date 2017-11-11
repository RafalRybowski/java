package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by epiklp on 11.11.17.
 */

class restart implements Screen, Interface {
    final Rain game;

    private OrthographicCamera camera;
    private Rectangle Button;
    private BitmapFont font;
    private Texture ButtonTexture;
    private int score;

    public restart(Rain game , int score) {
        this.game = game;
        this.score = score;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Width, Height);
        Button = new Rectangle();
        Button.width = Width/6;
        Button.height = Height/8;
        Button.x= Width/2 - Button.getWidth()/2;
        Button.y= Height/2 - Button.getHeight()/2;
        font=new BitmapFont();
        font.getData().setScale(4, 4);


        CreateTexture();
    }

    private void CreateTexture()
    {
        Pixmap pixmap = new Pixmap((int)Button.getWidth(), (int)Button.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, (int)Button.getWidth(), (int)Button.getHeight());
        ButtonTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(ButtonTexture, Button.getX(), Button.getY(), Button.getWidth(), Button.getHeight());
        font.draw(game.batch, "Restart",(Button.getX()), (Button.getY()+Button.getHeight()-Button.getHeight()/4));
        font.draw(game.batch, "score: " + score,(Button.getX()), (Button.getY()+Button.getHeight()+Button.getHeight()));

        game.batch.end();
        if(Gdx.input.isTouched())
            if((Gdx.input.getX() > Button.getX() && Gdx.input.getX() < Button.getX() + Button.getWidth()) && (Gdx.input.getY() > Button.getY() && Gdx.input.getY() < Button.getY() + Button.getHeight()))
                pause();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        dispose();
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ButtonTexture.dispose();
        font.dispose();
    }
}
