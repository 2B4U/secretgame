package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.TransformComponent;
import me.lmpedro.main.factorys.WorldFactory;

public class PhysicsSystem extends IteratingSystem {

    private static final float FIXED_TIME = 1 / 60f;
    private float accumulator;


    private Array<Entity> bodiesQueue;
    private World world;

    private ComponentMapper<B2DComponent> bm = ComponentMapper.getFor(B2DComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    @SuppressWarnings("unchecked")
    public PhysicsSystem(World world) {
        super(Family.all(B2DComponent.class, TransformComponent.class).get());

        this.world = world;
        this.bodiesQueue = new Array<>();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        float deltaTime = Math.min(0.25f, Gdx.graphics.getDeltaTime());

        accumulator += deltaTime;
        if (accumulator >= FIXED_TIME) {
            world.step(FIXED_TIME, 6, 2);
            accumulator -= FIXED_TIME;

            //Entity Queue
            for (Entity entity : bodiesQueue) {
                TransformComponent tfm = tm.get(entity);
                B2DComponent bodyComp = bm.get(entity);
                Vector2 position = bodyComp.body.getPosition();
                tfm.position.x = position.x;
                tfm.position.y = position.y;
                tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
                if (bodyComp.isDead) {
                    System.out.println("Removing a body and entity");
                    world.destroyBody(bodyComp.body);
                    getEngine().removeEntity(entity);

                }
            }

        }
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodiesQueue.add(entity);
    }
}
