package enemy;

import java.util.Collection;

/**
 * Created by stefan.ziffer on 07.03.2018.
 */

class Wave {
    private Collection<Enemy> enemies;

    public Wave(Collection<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Wave() {

    }

    public Collection<Enemy> getEnemies() {
        return enemies;
    }
}
