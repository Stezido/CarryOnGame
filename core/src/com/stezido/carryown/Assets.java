package com.stezido.carryown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.stezido.carryown.CarryOwnGame.world;


/**
 * Created by stefan.ziffer on 19.02.2018.
 */

public class Assets {

    public static final int NUMBER_OF_PLAYERSPRITES_ROW = 9;
    private static final int NUMBER_OF_PLAYERSPRITES_COLUMN = 8;

    private static final int NUMBER_OF_SPEERMANSPRITES_ROW = 7;
    private static final int NUMBER_OF_SPEERMANSPRITES_COLUMN = 4;

    public static Texture background;
    public static Texture testCollisionRectangle;
    public static Texture alienBullet;
    public static Texture background_statusbar;

    public static Map<Direction, Animation> playerMoveAnimations = new HashMap<Direction, Animation>();
    public static Map<Direction, Animation> playerAttackAnimations = new HashMap<Direction, Animation>();


    public static Map<Direction, Animation> speermanMoveAnimations = new HashMap<Direction, Animation>();
    public static float frameDurationAttack = 10 / world.player.attackspeed;


    //public static Map<Direction, Animation> speermanAnimations = new HashMap<Direction, Animation>();


    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        background = loadTexture("World/backgroundgreen.png");
        testCollisionRectangle = loadTexture("CollisionRectangle.png");
        alienBullet = loadTexture("Bullets/alienBullet_texture.jpg");
        background_statusbar = loadTexture("statusbar_baclground.png");

        /*
        create Player Animations
         */
        TextureRegion[][] tmp = getMultiArrayFromPNG("Player/AlienPlayer.png",
                NUMBER_OF_PLAYERSPRITES_ROW,
                NUMBER_OF_PLAYERSPRITES_COLUMN);
        playerMoveAnimations = createAnimation(tmp, 1, 4, 2, 3, world.player);
        playerAttackAnimations = createAnimation(tmp, 5, 8, 6, 7, world.player);
        playerAttackAnimations = setFrameDuration(playerAttackAnimations);
        /*
        create Speerman Animations
         */
        tmp = getMultiArrayFromPNG("Enemies/Speerman.png",
                NUMBER_OF_SPEERMANSPRITES_ROW,
                NUMBER_OF_SPEERMANSPRITES_COLUMN);
        speermanMoveAnimations = createAnimation(tmp, 1, 2, 3, 4, world.myWaveGenerator.enemyPrototypes.get("Speerman"));//Wavegenerator fügt die Prototypes hinzu

//        createAttackAnimation(tmp);


    }

    private static Map<Direction, Animation> setFrameDuration(Map<Direction, Animation> animationsMap) {
        Iterator iterator = animationsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Animation a = (Animation) pair.getValue();
            a.setFrameDuration(frameDurationAttack);
        }
        return animationsMap;
    }

    private static TextureRegion[][] getMultiArrayFromPNG(String filePath, int numberOfRows, int numberOfColumns) {
        Texture texture = loadTexture(filePath);
        int width = texture.getWidth() / numberOfRows;
        int height = texture.getHeight() / numberOfColumns;
        return TextureRegion.split(texture, width, height);
    }

    private static Map<Direction, Animation> createAnimation(TextureRegion[][] tmp, int downRow, int upRow, int leftRow, int rightRow, DynamicGameObject dynGameObj) {
        //walkAnimation
        TextureRegion[] downwards = getSpritesAsArray(tmp, downRow);

        TextureRegion[] upwards = getSpritesAsArray(tmp, upRow);
        TextureRegion[] left = getSpritesAsArray(tmp, leftRow);
        TextureRegion[] walkRight = getSpritesAsArray(tmp, rightRow);
        Animation moveDown = new Animation<TextureRegion>((float) 10 / dynGameObj.movementspeed, downwards);
        Animation moveUp = new Animation<TextureRegion>((float) 10 / dynGameObj.movementspeed, upwards);
        Animation moveLeft = new Animation<TextureRegion>((float) 10 / dynGameObj.movementspeed, left);
        Animation moveRight = new Animation<TextureRegion>((float) 10 / dynGameObj.movementspeed, walkRight);
        HashMap<Direction, Animation> animationBundle = new HashMap<Direction, Animation>();
        animationBundle.put(Direction.DOWN, moveDown);
        animationBundle.put(Direction.UP, moveUp);
        animationBundle.put(Direction.LEFT, moveLeft);
        animationBundle.put(Direction.RIGHT, moveRight);
        return animationBundle;
    }

    private static TextureRegion[] getSpritesAsArray(TextureRegion[][] tmp, int row) {
        row -= 1;
        TextureRegion[] tr = new TextureRegion[tmp[row].length];
        int index = 0;
        for (int j = 0; j < tmp[row].length; j++) {
            tr[index++] = tmp[row][j];
        }
        return tr;
    }

    public static Map<Direction, Animation> getAnimations(DynamicGameObject dynamicGameObject, AnimationType animationType) {

        String varName = dynamicGameObject.getClass().getSimpleName().toLowerCase() + animationType.name() + "Animations";
//        System.out.println("FieldName of Animations: = " + varName);
        try {
            return (Map<Direction, Animation>) Assets.class.getField(varName).get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("Animations: \'" + varName + "\' don't exist-> create a variable called \'" + varName + "Animations\' in Assets.java");
            e.printStackTrace();
        }
        return null;
    }
}
