package me.lmpedro.main.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.World;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.CollisionComponent;
import me.lmpedro.main.ecs.components.EnemyComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.ecs.system.*;
import me.lmpedro.main.factorys.WorldFactory;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> b2DMapper = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<CollisionComponent> collisionMapper = ComponentMapper.getFor(CollisionComponent.class);

    public ECSEngine(final Main context){
        super();

        this.addSystem(new PlayerMovementSystem(context));
        this.addSystem(new PlayerCameraSystem(context));
        this.addSystem(new EnemySystem(context));
        this.addSystem(new CollisionSystem(context));
        this.addSystem(new PhysicsSystem(context, context.getWorld()));
    }

}
