package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.lmpedro.main.Main;
import me.lmpedro.main.ai.SteeringPresets;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.EnemyComponent;
import me.lmpedro.main.ecs.components.SteeringComponent;
import me.lmpedro.main.factorys.WorldFactory;

public class EnemySystem extends IteratingSystem {

    private final Main context;
    private ComponentMapper<EnemyComponent> em;
    private ComponentMapper<B2DComponent> bodm;


    @SuppressWarnings("unchecked")
    public EnemySystem(Main context) {
        super(Family.all(EnemyComponent.class).get());
        em = ComponentMapper.getFor(EnemyComponent.class);
        bodm = ComponentMapper.getFor(B2DComponent.class);
        this.context = context;
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {

/*        EnemyComponent enemyComponent = ECSEngine.enemyMapper.get(entity);
        B2DComponent b2DComponent = ECSEngine.b2DMapper.get(entity);*/
        EnemyComponent enemyComponent = em.get(entity);        // get EnemyComponent
        B2DComponent b2DComponent = bodm.get(entity);

        if (enemyComponent.enemyType == EnemyComponent.Type.TEST) {
            //get distance of enemy from its original start position(pad center)
            float distFromOrig = Math.abs(enemyComponent.xPosCenter - b2DComponent.body.getPosition().x);

            //if distance > 1 swap direction
            enemyComponent.isGoingLeft = (distFromOrig > 2) ? !enemyComponent.isGoingLeft : enemyComponent.isGoingLeft;

            //set speed base of direction
            float speed = enemyComponent.isGoingLeft ? -0.001f : 0.001f;

            //apply speed to body
            b2DComponent.body.setTransform(b2DComponent.body.getPosition().x + speed, b2DComponent.body.getPosition().y, b2DComponent.body.getAngle());
        }


        if (enemyComponent.enemyType == EnemyComponent.Type.TEST1) {

            B2DComponent b2Player = ECSEngine.b2DMapper.get(context.getWorldFactory().player);
            B2DComponent enemy = ECSEngine.b2DMapper.get(entity);
            float distance = b2Player.body.getPosition().dst(enemy.body.getPosition());
            //System.out.println(distance);
            SteeringComponent scom = ECSEngine.SteerMapper.get(entity);
            if(distance < 3 && scom.currentMode != SteeringComponent.SteeringState.FLEE){
                scom.steeringBehavior = SteeringPresets.getFlee(ECSEngine.SteerMapper.get(entity),ECSEngine.SteerMapper.get(context.getWorldFactory().player));
                scom.currentMode = SteeringComponent.SteeringState.FLEE;
            }else if(distance > 5 && distance < 11 && scom.currentMode != SteeringComponent.SteeringState.ARRIVE){
                scom.steeringBehavior = SteeringPresets.getArrive(ECSEngine.SteerMapper.get(entity),ECSEngine.SteerMapper.get(context.getWorldFactory().player));
                scom.currentMode = SteeringComponent.SteeringState.ARRIVE;
/*            }else if(distance > 15 && scom.currentMode != SteeringComponent.SteeringState.SEEK){
            scom.steeringBehavior = SteeringPresets.getSeek(ECSEngine.SteerMapper.get(entity), ECSEngine.SteerMapper.get(context.getWorldFactory().player));
            scom.currentMode = SteeringComponent.SteeringState.SEEK;*/
        }

        if (enemyComponent.isDead) {
            b2DComponent.isDead = true;
        }
    }
} }
