package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class CollisionComponent implements Component , Pool.Poolable {
    public Entity collisionEntity;

    @Override
    public void reset() {
        collisionEntity = null;
    }
}
