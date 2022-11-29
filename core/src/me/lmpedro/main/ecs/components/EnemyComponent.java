package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public static enum Type { TEST, TEST1 };

    public boolean isDead = false;
    public float xPosCenter = -1;
    public boolean isGoingLeft = false;
    public float health = 0;
    public boolean isAggro = false;
    public Type enemyType = Type.TEST;

    @Override
    public void reset() {
        isDead = false;
        xPosCenter = -1;
        isGoingLeft = false;
        enemyType = Type.TEST;
        health = 0;
        isAggro = false;
    }
}
