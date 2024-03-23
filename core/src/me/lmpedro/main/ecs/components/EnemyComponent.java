package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public enum Type { TEST, TEST1, HEALTH }

    public boolean isDead = false;
    public float xPosCenter = -1;
    public boolean isGoingLeft = false;
    public float health = 0;
    public boolean isAggro = false;
    public Type enemyType = Type.TEST;
    public float shootDelay = 0.2f;
    public float timeSinceLastShot = 0f;

    @Override
    public void reset() {
        shootDelay = 0.5f;
        timeSinceLastShot = 0;
        isDead = false;
        xPosCenter = -1;
        isGoingLeft = false;
        enemyType = Type.TEST;
        health = 0;
        isAggro = false;
    }
}
