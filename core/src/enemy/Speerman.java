package enemy;

import com.badlogic.gdx.math.Vector2;
import com.stezido.carryown.CarryOwnGame;
import com.stezido.carryown.Direction;

/**
 * Created by stefan.ziffer on 26.02.2018.
 */

public class Speerman extends Enemy {

    private static final float WIDTH = 60;
    private static final float HEIGHT = 60;
    private static final float MOVEMENTSPEED = 50;

    private final float SPRITESIZE_WIDTH_IN_PX = 150.43f;
    private final float SPRITESIZE_HEIGHT_IN_PX = 117;
    private final float COLLISIONBOUNDS_POS_X = 44;
    //TODO: Fehler bei YPos, wie wird rectangle gezeichnet wo ist 0/0?
    private final float COLLISIONBOUNDS_POS_Y = 0;
    private final float COLLISIONBOUNDS_WIDTH = 47;
    private final float COLLISIONBOUNDS_HEIGHT = 100;

    //needed for Prototypes - which is needed for assets (animations)
    public Speerman() {
        this(0, 0);
    }

    public Speerman(float x, float y) {
        this(x, y, WIDTH, HEIGHT, MOVEMENTSPEED);
    }

    public Speerman(float x, float y, float width, float height, float movementspeed) {
        super(x, y, width, height, movementspeed);
        setRectangleCollisionBounds(SPRITESIZE_WIDTH_IN_PX, SPRITESIZE_HEIGHT_IN_PX,
                new Vector2(COLLISIONBOUNDS_POS_X, COLLISIONBOUNDS_POS_Y),
                new Vector2(COLLISIONBOUNDS_WIDTH, COLLISIONBOUNDS_HEIGHT));
        stateTime = 0;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setStandardMovement();
//        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
//        bounds.x = position.x - bounds.width / 2;
//        bounds.y = position.y - bounds.height / 2;

        stateTime += deltaTime;
    }

    private void setStandardMovement() {
        //movement Y-axis
        if (this.position.y < CarryOwnGame.world.player.position.y) {
            //moveup
            lookAt = Direction.UP;
            this.velocity.y = this.movementspeed;
        } else {
            if (this.position.y > CarryOwnGame.world.player.position.y) {
                //movedown
                lookAt = Direction.DOWN;
                this.velocity.y = -this.movementspeed;
            } else {
                this.velocity.y = 0;
            }
        }

        //movement X-axis
        if (this.position.x < CarryOwnGame.world.player.position.x - 2) {
            //moveright
            lookAt = Direction.RIGHT;
            this.velocity.x = this.movementspeed;
        } else {
            if (this.position.x > CarryOwnGame.world.player.position.x + 2) {
                //moveleft
                lookAt = Direction.LEFT;
                this.velocity.x = -this.movementspeed;
            } else {
                this.velocity.x = 0;
            }
        }


    }

    @Override
    public String toString() {
        return "Speerman{" +
                "movementspeed=" + movementspeed +
                '}';
    }
}
