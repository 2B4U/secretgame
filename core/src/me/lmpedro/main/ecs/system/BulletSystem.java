package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.BulletComponent;

public class BulletSystem extends IteratingSystem {
    private Entity player;

    public BulletSystem(){
        super(Family.all(BulletComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //get box 2d body and bullet components
        B2DComponent b2body = ECSEngine.b2DMapper.get(entity);
        BulletComponent bullet = ECSEngine.bulletMapper.get(entity);

        // apply bullet velocity to bullet body
        b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);
        // get player pos
        B2DComponent playerBodyComp = ECSEngine.b2DMapper.get(player);
        float px = playerBodyComp.body.getPosition().x;
        float py = playerBodyComp.body.getPosition().y;

        //get bullet pos
        float bx = b2body.body.getPosition().x;
        float by = b2body.body.getPosition().y;

        // if bullet is 20 units away from player on any axis then it is probably off screen
        if(bx - px > 20 || by - py > 20){
            bullet.isDead = true;
        }

        //check if bullet is dead
        if(bullet.isDead){
            b2body.isDead = true;
        }
    }
}