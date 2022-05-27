package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class B2DComponent implements Component, Pool.Poolable {
    public Body body;
    public boolean isDead;
    public float width;
    public float height;

    @Override
    public void reset() {
        if(body != null){
            body.getWorld().destroyBody(body);
            body = null;
        }
        width = height = 0;
    }
}
