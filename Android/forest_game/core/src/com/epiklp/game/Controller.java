package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by epiklp on 18.11.17.
 */

public class Controller implements Interface {
    private Stage stage;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private Table tabr;

    public Controller()
    {
        stage = new Stage(new ExtendViewport(width, height));
        Gdx.input.setInputProcessor(stage);

        Table tabl = new Table();
        tabl.bottom().left();
        Image imu = new Image(new Texture("button/U.png"));
        imu.setSize(width/10,width/10);
        imu.addListener(new InputListener() {

                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                upPressed = true;
                                return true;
                            }

                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                upPressed = false;
                            }
                        });

        Image iml = new Image(new Texture("button/L.png"));
        iml.setSize(width/10,width/10);
        iml.addListener(new InputListener() {

                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                leftPressed = true;
                                return true;
                            }

                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                leftPressed = false;
                            }
                        });
        tabl.add(imu).size(iml.getWidth(), iml.getHeight());
        tabl.row().padBottom(10);
        tabl.add(iml).size(iml.getWidth(), iml.getHeight());
        stage.addActor(tabl);

        tabr = new Table();
        tabr.setPosition(width-(width/10)/2, (width/10));
        Image imr = new Image(new Texture("button/R.png"));
        imr.setSize(width/10,width/10);
        imr.addListener(new InputListener() {

                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                rightPressed = true;
                                return true;
                            }

                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                rightPressed = false;
                            }
                        });

        Image ime = new Image(new Texture("button/D.png"));
        ime.setSize(width/10,width/10);
        ime.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });
        tabr.add(ime).size(iml.getWidth(), iml.getHeight());
        tabr.row().padBottom(10);
        tabr.add(imr).size(iml.getWidth(), iml.getHeight());
        stage.addActor(tabr);
    }

    public void draw()
    {
        stage.act();
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void dispose()
    {
        stage.dispose();
    }
}
