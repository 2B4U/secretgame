package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BulletComponent implements Component, Pool.Poolable {
    public float xVel = 0;
    public float yVel = 0;
    public boolean isDead = false;

    @Override
    public void reset() {
        xVel = 0;
        yVel = 0;
        isDead = false;
    }
}