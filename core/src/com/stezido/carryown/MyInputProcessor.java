package com.stezido.carryown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import static com.stezido.carryown.CarryOwnGame.world;

/**
 * Created by stefan.ziffer on 20.02.2018.
 */


//doing nothing because a movement will only be executed ONCE :( so it might work for menu or sth
public class MyInputProcessor implements InputProcessor {

    boolean right = false;
    boolean left = false;
    boolean down = false;
    boolean up = false;

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.LEFT:
                moveLeft();
                break;
            case Input.Keys.UP:
                moveUp();
                break;
            case Input.Keys.RIGHT:
                moveRight();
                break;
            case Input.Keys.DOWN:
                moveDown();
                break;
            case Input.Keys.SPACE:
                attack();
                break;

        }
        return true;
    }

    private void attack() {
        world.player.state = Player.PlayerState.ATTACKING;
        //TODO: logik gehört woanders hin
        world.addAlienBullet();
    }

    private void moveDown() {
        world.player.velocity.y = -world.player.movementspeed;
        world.player.lookAt = Direction.DOWN;
        world.player.state = Player.PlayerState.MOVING;
        down = true;
    }

    private void moveRight() {
        world.player.velocity.x = world.player.movementspeed;
        world.player.lookAt = Direction.RIGHT;
        world.player.state = Player.PlayerState.MOVING;
        right = true;
    }

    private void moveUp() {
        world.player.velocity.y = world.player.movementspeed;
        world.player.lookAt = Direction.UP;
        world.player.state = Player.PlayerState.MOVING;
        up = true;
    }

    private void moveLeft() {
        world.player.velocity.x = -world.player.movementspeed;
        world.player.lookAt = Direction.LEFT;
        world.player.state = Player.PlayerState.MOVING;
        left = true;
    }

    @Override
    public boolean keyUp(int keycode) {


        switch (keycode) {
            case Input.Keys.LEFT:
                if (right == false) {
                    world.player.velocity.x += world.player.movementspeed;
                } else {
                    moveRight();
                }
                left = false;
                break;
            case Input.Keys.UP:
                if (down == false) {
                    world.player.velocity.y -= world.player.movementspeed;
                } else {
                    moveDown();
                }
                up = false;
                break;
            case Input.Keys.RIGHT:
                if (left == false) {
                    world.player.velocity.x -= world.player.movementspeed;
                } else {
                    moveLeft();
                }
                right = false;
                break;
            case Input.Keys.DOWN:
                if (up == false) {
                    world.player.velocity.y += world.player.movementspeed;
                } else {
                    moveUp();
                }
                down = false;
                break;


        }
        if (left && !right && !up && !down) world.player.lookAt = Direction.LEFT;
        if (!left && right && !up && !down) world.player.lookAt = Direction.RIGHT;
        if (!left && !right && up && !down) world.player.lookAt = Direction.UP;
        if (!left && !right && !up && down) world.player.lookAt = Direction.DOWN;
        if (world.player.state != Player.PlayerState.ATTACKING && !left && !right && !up && !down) {
            world.player.state = Player.PlayerState.NO_MOVE;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
