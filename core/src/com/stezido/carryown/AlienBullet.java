package com.stezido.carryown;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by stefan.ziffer on 25.03.2018.
 */

public class AlienBullet extends DynamicGameObject {


    private final float SPRITESIZE_WIDTH_IN_PX = 10;
    private final float SPRITESIZE_HEIGHT_IN_PX = 10;
    private final float COLLISIONBOUNDS_POS_X = 1;
    //TODO: Fehler bei YPos, wie wird rectangle gezeichnet wo ist 0/0?
    private final float COLLISIONBOUNDS_POS_Y = 1;
    private final float COLLISIONBOUNDS_WIDTH = 8;
    private final float COLLISIONBOUNDS_HEIGHT = 8;


    /**
     * @param x             Position
     * @param y             Position
     * @param width         Width
     * @param height        Height
     * @param movementspeed
     */
    public AlienBullet(float x, float y, float width, float height, float movementspeed, Direction direction) {
        this(x, y, width, height, movementspeed);
        switch (direction) {
            case UP:
                velocity = new Vector2(0, movementspeed);
                break;
            case DOWN:
                velocity = new Vector2(0, -movementspeed);
                break;
            case LEFT:
                velocity = new Vector2(-movementspeed, 0);
                break;
            case RIGHT:
                velocity = new Vector2(movementspeed, 0);
                break;
        }

    }


    public AlienBullet(float x, float y, float width, float height, float movementspeed) {
        super(x, y, width, height, movementspeed);
        setRectangleCollisionBounds(SPRITESIZE_WIDTH_IN_PX, SPRITESIZE_HEIGHT_IN_PX,
                new Vector2(COLLISIONBOUNDS_POS_X, COLLISIONBOUNDS_POS_Y),
                new Vector2(COLLISIONBOUNDS_WIDTH, COLLISIONBOUNDS_HEIGHT));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        //TODO: wenn aus map raus dann dispose()
    }

}
