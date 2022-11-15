package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class B2DComponent implements Component, Pool.Poolable {
    public Body body;
    public float width;
    public float height;
    public boolean isDead = false;

    @Override
    public void reset() {
        isDead = false;

        if(body != null){
            body.getWorld().destroyBody(body);
            body = null;
        }
        width = height = 0;
    }
}
