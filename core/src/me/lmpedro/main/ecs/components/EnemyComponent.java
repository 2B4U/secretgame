package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public boolean isDead;
    public float xPosCenter;
    public boolean isGoingLeft;
    public float health;
    public boolean isAggro;

    @Override
    public void reset() {
        isDead = false;
        xPosCenter = 0f;
        isGoingLeft = false;
        health = 0;
        isAggro = false;
    }
}
