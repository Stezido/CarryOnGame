package enemy;

/**
 * Created by stefan.ziffer on 27.02.2018.
 */

public enum Velocity {

    VERY_SLOW(30), SLOW(50), NORMAL(100), FAST(130), VERY_FAST(150);

    private int num;

    Velocity(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
