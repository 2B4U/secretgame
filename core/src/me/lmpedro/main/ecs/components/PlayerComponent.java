package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable{
    public OrthographicCamera cam = null;
    public boolean hasAxe;
    public Vector2 speed = new Vector2();
    public int health;
    public int mana;
    public int score;
    public boolean isDead;
    public float shootDelay = 0.2f;
    public float timeSinceLastShot = 0f;

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void reset() {
        mana = 0;
        cam = null;
        health = 0;
        score = 0;
        hasAxe = false;
        speed.set(0,0);
        isDead = false;
        shootDelay = 0.2f;
        timeSinceLastShot = 0f;
    }
}
