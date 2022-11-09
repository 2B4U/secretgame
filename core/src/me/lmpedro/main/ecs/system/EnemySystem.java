package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.EnemyComponent;

public class EnemySystem extends IteratingSystem {


    public EnemySystem(final Main context) {
        super(Family.all(EnemyComponent.class, B2DComponent.class).get());
    }

    @Override
    protected void processEntity(final Entity entity,final float deltaTime) {
        EnemyComponent enemyComponent = ECSEngine.enemyMapper.get(entity);
        B2DComponent b2DComponent = ECSEngine.b2DMapper.get(entity);

        //get distance of enemy from its original start position(pad center)
        float distFromOrig = Math.abs(enemyComponent.xPosCenter - b2DComponent.body.getPosition().x);

        //if distance > 1 swap direction
        enemyComponent.isGoingLeft = (distFromOrig > 2)? !enemyComponent.isGoingLeft:enemyComponent.isGoingLeft;

        //set speed base of direction
        float speed = enemyComponent.isGoingLeft?-0.001f:0.001f;

        //apply speed to body
        b2DComponent.body.setTransform(b2DComponent.body.getPosition().x + speed, b2DComponent.body.getPosition().y,b2DComponent.body.getAngle());
    }
}
