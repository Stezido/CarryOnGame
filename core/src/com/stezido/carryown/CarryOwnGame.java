package com.stezido.carryown;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CarryOwnGame extends ApplicationAdapter {

    private MyInputProcessor inputProcessor;
    public static World world;
    private WorldRenderer worldRenderer;
    public SpriteBatch batcher;
    public World.WorldListener worldListener;


    @Override
    public void create() {
        batcher = new SpriteBatch();
        System.out.println("width = " + Gdx.graphics.getWidth());
        worldListener = new World.WorldListener() {

            @Override
            public void attack() {
                //Assets.playsound attack
            }

            @Override
            public void getBuff() {

            }
        };
        world = new World();
        worldRenderer = new WorldRenderer(batcher);
        Assets.load();
        world.player.setUpAfterAssetsLoaded();

        inputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);


    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void render() {
        /*
        * set up Background to be black
        */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        handleKeyInputs();
        world.update(Gdx.graphics.getDeltaTime());
        worldRenderer.draw();

    }

    /**
     * handle all Inputs when a Key is pressed
     */
    private void handleKeyInputs() {

        //zoom in and out with P and -
        //TODO: Zoom in and out mybe not wanted
        if (Gdx.input.isKeyPressed(Input.Keys.P)) worldRenderer.cam.zoom -= 0.1;
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) worldRenderer.cam.zoom += 0.1;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Buttons.LEFT) || world.player.state == Player.PlayerState.ATTACKING) {
            //world.player.attack();
        } else {

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                world.player.velocity.x = -world.player.movementspeed;
                world.player.lookAt = Direction.LEFT;
                world.player.state = Player.PlayerState.MOVING;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                world.player.velocity.y = world.player.movementspeed;
                world.player.lookAt = Direction.UP;
                world.player.state = Player.PlayerState.MOVING;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                world.player.velocity.x = world.player.movementspeed;
                world.player.lookAt = Direction.RIGHT;
                world.player.state = Player.PlayerState.MOVING;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                world.player.velocity.y = -world.player.movementspeed;
                world.player.lookAt = Direction.DOWN;
                world.player.state = Player.PlayerState.MOVING;
            }
        }
    }

    @Override
    public void dispose() {

    }
}
