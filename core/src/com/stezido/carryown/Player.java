package com.stezido.carryown;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by stefan.ziffer on 19.02.2018.
 */

public class Player extends DynamicGameObject {
    private static final float MOVEMENTSPEED_STANDARD = 200;
    private final float SPRITESIZE_WIDTH_IN_PX = 57;
    private final float SPRITESIZE_HEIGHT_IN_PX = 57;
    private final float COLLISIONBOUNDS_POS_X = 21;
    private final float COLLISIONBOUNDS_POS_Y = 6;
    private final float COLLISIONBOUNDS_WIDTH = 16;
    private final float COLLISIONBOUNDS_HEIGHT = 48;


    public enum PlayerState {
        MOVING, ATTACKING, NO_MOVE;
    }

//    public PlayerState state = PlayerState.PLAYER_STATE_NO_MOVE;

    public float attackspeed = 150f;
    public float timeForOneAttack, attackTimePast = 0;
    //private Direction lookAt = Direction.DOWN;
    public PlayerState state = PlayerState.NO_MOVE;

    public Player(float x, float y, float width, float height) {
        this(x, y, width, height, MOVEMENTSPEED_STANDARD);

    }

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Player(float x, float y, float width, float height, float movementspeed) {
        super(x, y, width, height, movementspeed);
        setRectangleCollisionBounds(SPRITESIZE_WIDTH_IN_PX, SPRITESIZE_HEIGHT_IN_PX, new Vector2(COLLISIONBOUNDS_POS_X, COLLISIONBOUNDS_POS_Y), new Vector2(COLLISIONBOUNDS_WIDTH, COLLISIONBOUNDS_HEIGHT));
//        currentFrame = walkAnimationDown.getKeyFrame(STATETIME, true);

    }

    public void setUpAfterAssetsLoaded() {
        timeForOneAttack = Assets.frameDurationAttack * Assets.NUMBER_OF_PLAYERSPRITES_ROW;
    }

//    public void move(Direction direction) {
//        lookAt = direction;
//        switch (lookAt) {
//            case UP:
//                this.position.y += velocity.y;
//                currentFrame = walkAnimationUp.getKeyFrame(STATETIME, true);
//                break;
//            case DOWN:
//                this.position.y -= velocity.y;
//                currentFrame = walkAnimationDown.getKeyFrame(STATETIME, true);
//                break;
//            case LEFT:
//                this.position.x -= velocity.x;
//                currentFrame = walkAnimationLeft.getKeyFrame(STATETIME, true);
//                break;
//            case RIGHT:
//                this.position.x += velocity.x;
//                currentFrame = walkAnimationRight.getKeyFrame(STATETIME, true);
//                break;
//        }
//    }

    public Vector2 getPosition() {
        return position;
    }

//    public void attack() {
//        if (isAttacking != true) {
//            this.isAttacking = true;
//            this.attackBegin = STATETIME;
//        } else {
//            if (STATETIME - this.attackBegin >= 1 / attackspeed) {
//                //System.out.println("attack ends\n");
//                switch (lookAt) {
//
//                    case UP:
//                        currentFrame = walkAnimationUp.getKeyFrame(STATETIME, true);
//                        break;
//                    case DOWN:
//                        currentFrame = walkAnimationDown.getKeyFrame(STATETIME, true);
//                        break;
//                    case LEFT:
//                        currentFrame = walkAnimationLeft.getKeyFrame(STATETIME, true);
//                        break;
//                    case RIGHT:
//                        currentFrame = walkAnimationRight.getKeyFrame(STATETIME, true);
//                        break;
//                }
//                this.isAttacking = false;
//                return;
//            }
//        }
//        switch (lookAt) {
//            case UP:
//                currentFrame = attackAnimationUp.getKeyFrame(STATETIME, true);
//                break;
//            case DOWN:
//                currentFrame = attackAnimationDown.getKeyFrame(STATETIME, true);
//                break;
//            case LEFT:
//                currentFrame = attackAnimationLeft.getKeyFrame(STATETIME, true);
//                break;
//            case RIGHT:
//                currentFrame = attackAnimationRight.getKeyFrame(STATETIME, true);
//                break;
//        }
//    }


    public void dispose() {
        //TODO: dispose all created objects in Playerclass
    }

    @Override
    public void update(float deltaTime) {
        if (state == PlayerState.ATTACKING && attackTimePast < timeForOneAttack) {
            attackTimePast += deltaTime;
            if (attackTimePast >= timeForOneAttack) {
                attackTimePast = 0;
                state = PlayerState.NO_MOVE;

            }

        } else {
            if (state != PlayerState.ATTACKING) {
                attackTimePast = 0;

            }

        }
        super.update(deltaTime);

    }

}
