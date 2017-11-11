package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


import java.awt.Label;

import static com.badlogic.gdx.math.MathUtils.random;


/**
 * Created by epiklp on 11.11.17.
 */

    public class GameScreen implements Screen, Interface {
    final Rain game;

    private Texture dropImage;
    private Texture bucketImage;
    private Texture thunderImage;
    private Texture background;

    private Sprite dropSprite;
    private Sprite bucketSprite;
    private Sprite thunderSprite;
    private Sprite backgroundSprite;

    private float wind;
    private Sound dropSound;
    private Sound lightingSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private Rectangle bucket;
    private Array<raindrop> raindrops;
    private Array<raindrop> thunders;
    private long lastDropTime;
    private long time;
    private int num;
    private BitmapFont font;
    private int timer;
    private float timerHelper;
    private int score;

    private Rectangle Left;
    private Rectangle Right;
    private Texture ButtonTexture;

    public GameScreen(Rain game) {
        this.game = game;
        //control
        Left = new Rectangle();
        Left.x=0;
        Left.y=0;
        Left.height = Height;
        Left.width = Width/5;
        Right = new Rectangle();
        Right.height = Height;
        Right.width = Width/5;
        Right.y =0;
        Right.x = Width - Width/5;
        CreateTexture();
        //texture
        dropImage = new Texture("droplet.png");
        bucketImage = new Texture("pot.png");
        thunderImage = new Texture("thunder.png");
        background = new Texture("wall.jpg");
        dropSprite = new Sprite(dropImage);
        thunderSprite = new Sprite(thunderImage);
        bucketSprite = new Sprite(bucketImage);
        bucketSprite.setSize(64, 64);
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(Width, Height);
        //sound
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        lightingSound = Gdx.audio.newSound(Gdx.files.internal("thunder.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("raintree.mp3"));
        rainMusic.setLooping(true);
        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Width, Height);


        //rectangle to bucket
        bucket = new Rectangle();
        bucket.x = Width / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        //create array rain and thunder
        raindrops = new Array<raindrop>();
        thunders = new Array<raindrop>();
        //font
        font = new BitmapFont();
        font.getData().setScale(2, 2);

        this.timer = Interface.timer;
        score = scores;
        time = times;
        num = nums;
        wind = random(-30,30);
        timerHelper = 0;

        spawnRaindrop();
    }
    private void CreateTexture()
    {
        Pixmap pixmap = new Pixmap((int)(Width/5), (int)(Height), Pixmap.Format.RGBA8888);
        pixmap.setColor(240,240,240, 0.5f);
        pixmap.fillRectangle(0, 0, (Width/5), Height);
        ButtonTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    private void spawnRaindrop() {
        raindrop r = new raindrop(wind);
        raindrops.add(r);
        lastDropTime = TimeUtils.nanoTime();
    }
    private void spawnThunder() {
        raindrop r = new raindrop(wind);
        thunders.add(r);
    }

    @Override
    public void show() {
        rainMusic.play();
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        if(timer > 0) {
            game.batch.begin();
            backgroundSprite.draw(game.batch);
            bucketSprite.draw(game.batch);
            bucketSprite.setPosition(bucket.x, bucket.y);
            for (raindrop r : raindrops) {
                dropSprite.draw(game.batch);
                dropSprite.setPosition( r.getX(), r.getY());
                dropSprite.setRotation(r.getRotation());
            }
            for (raindrop r : thunders) {
                thunderSprite.draw(game.batch);
                thunderSprite.setPosition( r.getX(), r.getY());
                thunderSprite.setRotation(r.getRotation());
            }
            game.batch.draw(ButtonTexture, Left.getX(), Left.getY(), Left.getWidth(), Left.getHeight());
            game.batch.draw(ButtonTexture, Right.getX(), Right.getY(), Right.getWidth(), Right.getHeight());
            font.draw(game.batch, "Ilosc zebranych kropel: " + score + " time: " + timer, (Left.getX()+Left.getWidth()), Gdx.graphics.getHeight());
            game.batch.end();
        }
        else
        {
            pause();        //end game
        }
        //control
        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX() > screenWithEnd)
                bucket.x += 500*Gdx.graphics.getDeltaTime();
            if(Gdx.input.getX() < screenWithStart)
                bucket.x -= 500*Gdx.graphics.getDeltaTime();
        }
        if(bucket.x > screenWithEnd - 64) bucket.x = screenWithEnd - 64;
        if(bucket.x  < screenWithStart) bucket.x = screenWithStart;

        //spawn drop and thunder
        if(TimeUtils.nanoTime() - lastDropTime > time)
        {
            spawnRaindrop();
            if(random(0, 100) > 50)
            {
                spawnThunder();
            }
        }

        if(time > 508000000) {
            time = time - num;
        }

        //move and check rain if is out screen and touch bucket
        for (raindrop rain:raindrops) {
            rain.setY();
            if(rain.getY() + 64 < 0)
            {
                raindrops.removeValue(rain, false);
                continue;
            }
            if(rain.getDrop().overlaps(bucket)) {
                score++;
                dropSound.play();
                raindrops.removeValue(rain, false);
                continue;
            }
            if(wind > 0)
                if(rain.getX() > screenWithEnd)
                {
                    rain.setX(screenWithStart-64);
                    continue;
                }
            else
                {
                    if(rain.getX() < screenWithStart-64)
                    {
                        rain.setX(screenWithEnd+64);
                        continue;
                    }
                }
            rain.setX();
        }

        //move and check thunder if is out screen and touch bucket
        for (raindrop thunder:thunders) {
            thunder.setY();
            if(thunder.getY() + 64 < 0)
            {
                thunders.removeValue(thunder, false);
                continue;
            }
            if(thunder.getDrop().overlaps(bucket)) {
                timer-=5;
                lightingSound.play();
                thunders.removeValue(thunder, false);
                continue;
            }
            if(wind > 0)
            {
                if(thunder.getX() > screenWithEnd)
                {
                    thunder.setX(screenWithStart-64);
                    continue;
                }
            }
            else
            {
                if(thunder.getX() < screenWithStart-64)
                {
                    thunder.setX(screenWithEnd+64);
                    continue;
                }
            }
            thunder.setX();

        }

        timerHelper += Gdx.graphics.getDeltaTime();
        if(timerHelper > 1)
        {
            timer--;
            timerHelper = 0;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        dispose();
        game.setScreen(new restart(game, score));
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        font.dispose();
        thunders.clear();
        raindrops.clear();
    }
}
