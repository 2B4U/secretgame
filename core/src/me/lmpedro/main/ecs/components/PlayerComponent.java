package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable{
    public boolean hasAxe;
    public Vector2 speed = new Vector2();
    public int health;
    public boolean isDead;
    public float shootDelay;
    public float timeSinceLastShot;

    @Override
    public void reset() {

        health = 0;
        hasAxe = false;
        speed.set(0,0);
        isDead = false;
        shootDelay = 0.5f;
        timeSinceLastShot = 0f;

    }
}
