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

class MainMenuScreen implements Screen, Interface {
    final Rain game;
    private final BitmapFont font;
    private Texture ButtonTexture;

    OrthographicCamera camera;

    public MainMenuScreen(final Rain game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Width, Height);
        font = new BitmapFont();
        font.getData().setScale(2, 2);

        CreateTexture();

    }
    private void CreateTexture()
    {
        Pixmap pixmap = new Pixmap(800, 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, 800, 50);
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

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(ButtonTexture, 100,115);
        font.draw(game.batch, "My first game created thakns to libgdx and this tutorial:", 100, 200);
        font.setColor(Color.BLUE);
        font.draw(game.batch, "https://github.com/libgdx/libgdx/wiki/Wiki-Style-Guide", 100, 150);
        font.setColor(Color.WHITE);
        font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
                game.setScreen(new GameScreen(game));
                dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
