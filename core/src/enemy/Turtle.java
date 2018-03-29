package enemy;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by stefan.ziffer on 27.02.2018.
 */

public class Turtle extends Enemy {

    public Turtle(float x, float y, float width, float height, float movementspeed) {
        super(x, y, width, height, movementspeed);
        stateTime = 0;
    }


    @Override
    public void update(float deltaTime) {
        System.out.println("update Turtle");
    }
}
