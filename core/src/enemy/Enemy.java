package enemy;

import com.badlogic.gdx.math.Vector2;
import com.stezido.carryown.DynamicGameObject;

/**
 * Created by stefan.ziffer on 26.02.2018.
 */

public abstract class Enemy extends DynamicGameObject {


    public Enemy(float x, float y, float width, float height, float movementspeed) {
        super(x, y, width, height, movementspeed);
    }
}
