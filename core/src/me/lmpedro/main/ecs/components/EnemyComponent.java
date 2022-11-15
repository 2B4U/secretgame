package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public boolean isDead = false;
    public float xPosCenter;
    public boolean isGoingLeft = false;
    public float health;
    public boolean isAggro = false;

    @Override
    public void reset() {
        isDead = false;
        xPosCenter = 0f;
        isGoingLeft = false;
        health = 0;
        isAggro = false;
    }
}
