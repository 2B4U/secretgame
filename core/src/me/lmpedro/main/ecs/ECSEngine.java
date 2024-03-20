package me.lmpedro.main.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.PooledEngine;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.*;
import me.lmpedro.main.ecs.system.*;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> b2DMapper = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<CollisionComponent> collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);
    public static final ComponentMapper TypeMapper = ComponentMapper.getFor(TypeComponent.class);
    public static final ComponentMapper<SteeringComponent> SteerMapper = ComponentMapper.getFor(SteeringComponent.class);

    public ECSEngine(final Main context){
        super();

        this.addSystem(new PhysicsSystem(context.getWorld()));
        this.addSystem(new PlayerControlSystem(context, context.getWorld()));
        this.addSystem(new PlayerCameraSystem(context));
        this.addSystem(new EnemySystem(context));
        this.addSystem(new CollisionSystem());
        this.addSystem(new BulletSystem(context));
        this.addSystem(new SteeringSystem());
    }

}
