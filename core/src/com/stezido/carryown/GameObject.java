package com.stezido.carryown;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by stefan.ziffer on 03.03.2018.
 */

public abstract class GameObject implements Cloneable {

    public final Vector2 position;
    public final Rectangle bounds;
    public Rectangle collisionBounds;
    /**
     * shift from bounds Position to actual collisionBoundsPosition
     */
    Vector2 relativeCollisionBoundsPosition;

    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    /**
     * @param spriteWidthInPx             Width in Px of each Sprite of the GameObject
     * @param spriteHeightInPx            Height in Px of each Sprite of the GameObject
     * @param collisionBoundsPositionInPx where should the CollisionRectangle be drawn inside the Sprite
     * @param collisionBoundsSizeInPx     Px Size of collisionBounds itself
     */
    public void setRectangleCollisionBounds(float spriteWidthInPx, float spriteHeightInPx, Vector2 collisionBoundsPositionInPx, Vector2 collisionBoundsSizeInPx) {

        relativeCollisionBoundsPosition = new Vector2(this.bounds.width * (collisionBoundsPositionInPx.x / spriteWidthInPx), this.bounds.height * (collisionBoundsPositionInPx.y / spriteHeightInPx));
//        System.out.println(getClass() + " " + bounds);

        Vector2 collisionPosition = new Vector2(bounds.x + relativeCollisionBoundsPosition.x, bounds.y + relativeCollisionBoundsPosition.y);
        Vector2 collisionSize = new Vector2(this.bounds.width * (collisionBoundsSizeInPx.x / spriteWidthInPx), this.bounds.height * (collisionBoundsSizeInPx.y / spriteHeightInPx));
        this.collisionBounds = new Rectangle(collisionPosition.x, collisionPosition.y, collisionSize.x, collisionSize.y);
    }
}
