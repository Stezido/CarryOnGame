package com.stezido.carryown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enemy.Enemy;
import enemy.Speerman;
import enemy.Velocity;
import enemy.WaveGenerator;

/**
 * Created by stefan.ziffer on 03.03.2018.
 */

public class World {


    public boolean levelBeaten = false;

    public interface WorldListener {

        public void attack();

        public void getBuff();
    }

    public static final float WORLDS_WIDTH = Gdx.graphics.getWidth() * 2;
    public static final float WORLD_HEIGHT = Gdx.graphics.getHeight() * 2;
    public static final float TIME_BETWEEN_WAVES = 11;

    public final Player player;
    public final List<Enemy> enemiesSpawned;
    public List<AlienBullet> bullets;
    public WaveGenerator myWaveGenerator;

    public int score;
    public float timeBeforeWave;

    public World() {
        this.myWaveGenerator = new WaveGenerator(1);
        this.player = new Player(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getWidth() / 7, Gdx.graphics.getHeight() / 7);
        this.enemiesSpawned = new ArrayList<Enemy>();
        this.bullets = new ArrayList<AlienBullet>();
        timeBeforeWave = 5;
        this.score = 0;
    }

    public void addAlienBullet() {
        bullets.add(new AlienBullet(player.getPosition().x, player.getPosition().y, 10, 10, 300, player.lookAt));
    }


    public void update(float deltaTime) {
        player.update(deltaTime);
        checkNextWave(deltaTime);
        updateEnemies(deltaTime);
        updateBullets(deltaTime);
        checkCollisions();
//        checkGameOver();
    }

    private void checkNextWave(float deltaTime) {
        timeBeforeWave -= deltaTime;
        if (timeBeforeWave <= 0) {
            timeBeforeWave = TIME_BETWEEN_WAVES;
            Collection<Enemy> enemiesToAdd = myWaveGenerator.generateNextWave();
            if (enemiesToAdd != null) {
                enemiesSpawned.addAll(enemiesToAdd);
            } else {
                levelBeaten = true;
                System.out.println("No More Waves in this Level");
            }
        }
    }

    private void checkCollisions() {
        checkEnemyCollision();
    }

    private void checkEnemyCollision() {

        for (int i = enemiesSpawned.size(); --i >= 0; ) {
            Enemy enemy = enemiesSpawned.get(i);
            if (enemy.collisionBounds.overlaps(player.collisionBounds)) {
                System.out.println("--------------DEAD-------------");
                break;
                //TODO: Spieler state tot=true
            }
            for (int j = bullets.size(); --j >= 0; ) {
                AlienBullet alienBullet = bullets.get(j);
                if (enemy.collisionBounds.overlaps(alienBullet.collisionBounds)) {
                    //System.out.println("Collision between bullet and enemy");
                    enemiesSpawned.remove(i);
                    bullets.remove(j);
                    score++;
                }
            }


        }

//        Iterator<Enemy> iterEnemy = enemiesSpawned.iterator();
//        Iterator<AlienBullet> iterbullets = bullets.iterator();
//
//        while (iterEnemy.hasNext()) {
//            Enemy enemy = iterEnemy.next();
//            if (enemy.collisionBounds.overlaps(player.collisionBounds)) {
//                System.out.println("--------------DEAD-------------");
//                break;
//                //TODO: Spieler state tot=true
//            }
//            while (iterbullets.hasNext()) {
//                AlienBullet alienBullet = iterbullets.next();
//                if (enemy.collisionBounds.overlaps(alienBullet.collisionBounds)) {
//                    System.out.println("Collision between bullet and enemy");
//                    iterEnemy.remove();
//                    iterbullets.remove();
//                }
//            }
//
//        }
    }

    private void updateEnemies(float deltaTime) {
        for (Enemy enemy : enemiesSpawned) {
            enemy.update(deltaTime);
        }
    }

    private void updateBullets(float deltaTime) {

        Iterator<AlienBullet> iter = bullets.iterator();

        while (iter.hasNext()) {
            AlienBullet alienBullet = iter.next();
            if (alienBullet.position.x >= 0 && alienBullet.position.y >= 0 && alienBullet.position.x < WORLDS_WIDTH && alienBullet.position.y < WORLD_HEIGHT) {
                alienBullet.update(deltaTime);
            } else {
                iter.remove();
            }

        }
    }
}
