package com.epiklp.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Menu extends ApplicationAdapter implements Interface {
	/*private Stage stage;
	private Image[] character;
	private Image[] LayerImage;*/
	private OrthographicCamera camera;
	private Controller controller;
	private TextureGame textureGame;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player, platform;
	private FPSLogger logger;
	private boolean ground = true;
	private float horizontalForce = 0;
	@Override
	public void create () {
		camera = new OrthographicCamera(width/PPM/2, height/PPM/2);

		textureGame = new TextureGame();
		controller = new Controller();

		world = new World(new Vector2(0,-10),false);
		b2dr = new Box2DDebugRenderer();
		logger = new FPSLogger();

		player = CreateBox(0, 64,32 , 32, false);
		platform = CreateBox(0, 0,128, 32, true);
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Gdx.graphics.getDeltaTime());
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				ground = true;
			}

			@Override
			public void endContact(Contact contact) {
				ground = false;
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
		});
		textureGame.draw();
		b2dr.render(world, camera.combined);
		controller.draw();
	}
	public void update(float delta)
	{
		world.step(1/60f, 6, 2);
		inputUpdate();
		cameraUpdate();
	}
	
	@Override
	public void dispose () {
		world.dispose();
		b2dr.dispose();
	}
	private void cameraUpdate() {
		Vector3 poition = camera.position;
		poition.x = player.getPosition().x;
		poition.y = height/PPM/4;
		camera.position.set(poition);
		camera.update();
	}

	private Body CreateBox(int x, int y, int width, int height, boolean isStatic) {
		Body pBody;
		BodyDef def = new BodyDef();
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = true;
		pBody = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/PPM,height/PPM);

		pBody.createFixture(shape,1);
		shape.dispose();
		return pBody;
	}

	private void inputUpdate() {

		if(Gdx.input.isTouched()) {
			if (controller.isLeftPressed()) {
				if (horizontalForce > -3)
					horizontalForce -= 0.2f;
			} else if (controller.isRightPressed()) {
				if (horizontalForce < 3)
					horizontalForce += 0.2f;
			}
		}
		else
		{
			if(horizontalForce > 0.1)
			{
				horizontalForce -= 0.2f;
			}
			else
				if(horizontalForce < -0.1)
				{
					horizontalForce += 0.2f;
				}
				else
				{
					horizontalForce = 0;
				}
		}

		player.setLinearVelocity(horizontalForce, player.getLinearVelocity().y);

		if (controller.isUpPressed() && ground)
			player.applyForceToCenter(0, 1000, true);
	}

}
