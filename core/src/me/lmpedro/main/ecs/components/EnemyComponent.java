package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public boolean isDead = false;
    public float xPosCenter = -1;
    public boolean isGoingLeft = false;
    public float health = 0;
    public boolean isAggro = false;

    @Override
    public void reset() {
        isDead = false;
        xPosCenter = -1;
        isGoingLeft = false;
        health = 0;
        isAggro = false;
    }
}
