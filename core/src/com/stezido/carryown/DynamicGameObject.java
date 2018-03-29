package com.stezido.carryown;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import enemy.Velocity;

/**
 * Created by stefan.ziffer on 03.03.2018.
 */

public abstract class DynamicGameObject extends GameObject {
    protected Vector2 velocity;
    public float movementspeed;
    public Direction lookAt = Direction.DOWN;
    public float stateTime;

    /**
     * @param x      Position
     * @param y      Position
     * @param width  Width
     * @param height Height
     */
    public DynamicGameObject(float x, float y, float width, float height, float movementspeed) {
        super(x, y, width, height);
        this.velocity = new Vector2(0, 0);
        this.movementspeed = movementspeed;
        stateTime = 0;
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;
        //TODO: if abfrage außerhalb update
        if (relativeCollisionBoundsPosition == null) {
            throw new NullPointerException("CollisionBounds not set in " + this.getClass());
        }
        collisionBounds.x = bounds.x + relativeCollisionBoundsPosition.x;
        collisionBounds.y = bounds.y + relativeCollisionBoundsPosition.y;
        //System.out.println(getClass() + " update " + collisionBounds);

        stateTime += deltaTime;
    }

    ;

}
