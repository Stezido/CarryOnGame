package com.stezido.carryown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Map;

import enemy.Enemy;

/**
 * Created by stefan.ziffer on 03.03.2018.
 */

public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    public OrthographicCamera cam;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont fontStatusbar = new BitmapFont();
    private BitmapFont fontlevelComplete = new BitmapFont();

    private GlyphLayout gLayout = new GlyphLayout();

    public WorldRenderer(SpriteBatch batch) {
        this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.cam.setToOrtho(false);
        this.cam.position.set(cam.position.x = CarryOwnGame.world.player.getPosition().x + CarryOwnGame.world.player.bounds.getWidth() / 2,
                cam.position.y = CarryOwnGame.world.player.getPosition().y + CarryOwnGame.world.player.bounds.getHeight() / 2, 0);
        this.batch = batch;
        setFonts();
    }

    private void setFonts() {
        fontStatusbar.setColor(Color.WHITE);
        fontlevelComplete.setColor(Color.GOLD);
        fontlevelComplete.getData().setScale(2.5f);
    }

    public void draw() {
        //if (world.bob.position.y > cam.position.y) cam.position.y = world.bob.position.y;
        if (((CarryOwnGame.world.player.getPosition().x - Gdx.graphics.getWidth() / 2 - CarryOwnGame.world.player.bounds.getWidth() / 2) >= 0) &&
                ((CarryOwnGame.world.player.getPosition().x + Gdx.graphics.getWidth() / 2 + CarryOwnGame.world.player.bounds.getWidth() / 2) <= CarryOwnGame.world.WORLDS_WIDTH)) {
            cam.position.x = CarryOwnGame.world.player.getPosition().x + CarryOwnGame.world.player.bounds.getWidth() / 2;
        }
        if (((CarryOwnGame.world.player.getPosition().y - Gdx.graphics.getHeight() / 2 - CarryOwnGame.world.player.bounds.getHeight() / 2) >= 0) &&
                ((CarryOwnGame.world.player.getPosition().y + Gdx.graphics.getHeight() / 2 + CarryOwnGame.world.player.bounds.getHeight() / 2) <= CarryOwnGame.world.WORLD_HEIGHT)) {
            cam.position.y = CarryOwnGame.world.player.getPosition().y + CarryOwnGame.world.player.bounds.getHeight() / 2;
        }
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        drawBackground();
        drawObjects();
    }

    public void drawBackground() {
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.background, 0, 0, World.WORLDS_WIDTH, World.WORLD_HEIGHT);
        batch.end();
    }

    public void drawObjects() {
        batch.enableBlending();
        batch.begin();
        drawBullets();
//        drawCollisionBoundsRectangles();
        drawPlayer();
        drawEnemies();
        drawStatusbar();
        if (CarryOwnGame.world.levelBeaten == true && CarryOwnGame.world.enemiesSpawned.isEmpty()) {
            drawLevelComplete();
        }

        batch.end();
    }

    private void drawLevelComplete() {
        fontlevelComplete.draw(batch, "Level Complete",
                cam.position.x - Gdx.graphics.getWidth() / 4,
                cam.position.y);
    }

    private void drawStatusbar() {
        batch.draw(Assets.background_statusbar,
                cam.position.x - Gdx.graphics.getWidth() / 2,
                cam.position.y + Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 14,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() / 14);
        drawScore();
        drawTimeBeforeNextWave();
        drawCurrentWaveCount();
    }

    private void drawCurrentWaveCount() {
        gLayout.setText(fontStatusbar, "Wave: " + (CarryOwnGame.world.myWaveGenerator.waveCount));
        fontStatusbar.draw(batch, gLayout,
                cam.position.x + Gdx.graphics.getWidth() / 2 - gLayout.width,
                cam.position.y + Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 36);
    }

    private void drawTimeBeforeNextWave() {
        String str;
        if (CarryOwnGame.world.levelBeaten == true) {
            str = "-";
        } else {
            str = String.valueOf((int) CarryOwnGame.world.timeBeforeWave);
        }
        fontStatusbar.draw(batch, "Next Wave in " + str,
                cam.position.x,
                cam.position.y + Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 36);
    }

    private void drawScore() {
        fontStatusbar.draw(batch, "Score: " + String.valueOf(CarryOwnGame.world.score),
                cam.position.x - Gdx.graphics.getWidth() / 2,
                cam.position.y + Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 36);
    }

    private void drawBullets() {
        for (AlienBullet alienBullet : CarryOwnGame.world.bullets) {
            batch.draw(Assets.alienBullet,
                    alienBullet.bounds.x,
                    alienBullet.bounds.y,
                    alienBullet.bounds.getWidth(),
                    alienBullet.bounds.getHeight());
        }
    }

    private void drawCollisionBoundsRectangles() {
        //System.out.println(getClass() + " renderer " + CarryOwnGame.world.player.collisionBounds);
        batch.draw(Assets.testCollisionRectangle, CarryOwnGame.world.player.collisionBounds.x, CarryOwnGame.world.player.collisionBounds.y, CarryOwnGame.world.player.collisionBounds.width, CarryOwnGame.world.player.collisionBounds.height);
        for (Enemy e : CarryOwnGame.world.enemiesSpawned) {
            batch.draw(Assets.testCollisionRectangle, e.collisionBounds.x, e.collisionBounds.y, e.collisionBounds.width, e.collisionBounds.height);
        }
        for (AlienBullet a : CarryOwnGame.world.bullets) {
            batch.draw(Assets.testCollisionRectangle, a.collisionBounds.x, a.collisionBounds.y, a.collisionBounds.width, a.collisionBounds.height);
        }
    }

    private void drawPlayer() {
        TextureRegion keyFrame;
//        System.out.println("PlayerState = " + CarryOwnGame.world.player.state);
        switch (CarryOwnGame.world.player.state) {
            case MOVING:
                switch (CarryOwnGame.world.player.lookAt) {
                    case RIGHT:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.RIGHT).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    case LEFT:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.LEFT).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    case DOWN:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.DOWN).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    case UP:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.UP).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    default:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.DOWN).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;

                }
                break;
            case ATTACKING:
                switch (CarryOwnGame.world.player.lookAt) {
                    case RIGHT:
                        keyFrame = (TextureRegion) Assets.playerAttackAnimations.get(Direction.RIGHT).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    case LEFT:
                        keyFrame = (TextureRegion) Assets.playerAttackAnimations.get(Direction.LEFT).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    case DOWN:
                        keyFrame = (TextureRegion) Assets.playerAttackAnimations.get(Direction.DOWN).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    case UP:
                        keyFrame = (TextureRegion) Assets.playerAttackAnimations.get(Direction.UP).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                    default:
                        keyFrame = (TextureRegion) Assets.playerAttackAnimations.get(Direction.DOWN).getKeyFrame(CarryOwnGame.world.player.stateTime, true);
                        break;
                }
                break;
            case NO_MOVE:
                switch (CarryOwnGame.world.player.lookAt) {
                    case RIGHT:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.RIGHT).getKeyFrames()[1];
                        break;
                    case LEFT:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.LEFT).getKeyFrames()[1];
                        break;
                    case DOWN:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.DOWN).getKeyFrames()[1];
                        break;
                    case UP:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.UP).getKeyFrames()[1];
                        break;
                    default:
                        keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.DOWN).getKeyFrames()[1];
                        break;
                }
                break;
            default:
                keyFrame = (TextureRegion) Assets.playerMoveAnimations.get(Direction.DOWN).getKeyFrames()[1];//TODO: [1] bad coded
                break;
        }
        batch.draw(keyFrame,
                CarryOwnGame.world.player.bounds.x,
                CarryOwnGame.world.player.bounds.y,
                CarryOwnGame.world.player.bounds.getWidth(),
                CarryOwnGame.world.player.bounds.getHeight());
    }

    private void drawEnemies() {
        for (Enemy enemy : CarryOwnGame.world.enemiesSpawned) {
            draw(enemy, AnimationType.Move);
        }
    }

    private void draw(DynamicGameObject dynamicGameObject, AnimationType animationType) {
        TextureRegion keyFrame;
        Map<Direction, Animation> animations = Assets.getAnimations(dynamicGameObject, animationType);
        switch (dynamicGameObject.lookAt) {
            case RIGHT:
                keyFrame = (TextureRegion) animations.get(Direction.RIGHT).getKeyFrame(dynamicGameObject.stateTime, true);
                break;
            case LEFT:
                keyFrame = (TextureRegion) animations.get(Direction.LEFT).getKeyFrame(dynamicGameObject.stateTime, true);
                break;
            case DOWN:
                keyFrame = (TextureRegion) animations.get(Direction.DOWN).getKeyFrame(dynamicGameObject.stateTime, true);
                break;
            case UP:
                keyFrame = (TextureRegion) animations.get(Direction.UP).getKeyFrame(dynamicGameObject.stateTime, true);
                break;
            default:
                keyFrame = (TextureRegion) animations.get(Direction.DOWN).getKeyFrame(dynamicGameObject.stateTime, true);
                break;

        }
        batch.draw(keyFrame,
                dynamicGameObject.bounds.x,
                dynamicGameObject.bounds.y,
                dynamicGameObject.bounds.getWidth(),
                dynamicGameObject.bounds.getHeight());
    }

}
