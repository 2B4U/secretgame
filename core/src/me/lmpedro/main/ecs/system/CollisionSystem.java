package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.CollisionComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.ecs.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    @SuppressWarnings("unchecked")
    public CollisionSystem(final Main context) {

        super(Family.all(CollisionComponent.class).get());

        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get collision for this entity
        CollisionComponent cc = cm.get(entity);
        //get collided entity
        Entity collidedEntity = cc.collisionEntity;

        TypeComponent thisType = entity.getComponent(TypeComponent.class);

        // Do Player Collisions
        if(thisType.type == TypeComponent.PLAYER){
            PlayerComponent pl = pm.get(entity);
            if(collidedEntity != null){
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if(type != null){
                    switch(type.type){
                        case TypeComponent.ENEMY:
                            //do player hit enemy thing
                            pl.isDead = true;
                            System.out.println("player hit enemy");
                            break;
                        case TypeComponent.SCENERY:
                            //do player hit scenery thing
                            System.out.println("player hit scenery");
                            break;
                        case TypeComponent.OTHER:
                            //do player hit other thing
                            System.out.println("player hit other");
                            break;
                        default:
                            System.out.println("No matching type found");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }else{
                    System.out.println("type == null");
                }
            }
        }else if(thisType.type == TypeComponent.ENEMY){  	// Do enemy collisions
            if(collidedEntity != null){
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if(type != null){
                    switch(type.type){
                        case TypeComponent.PLAYER:
                            System.out.println("enemy hit player");
                            Gdx.app.debug("","enemy hit player");
                            break;
                        case TypeComponent.ENEMY:
                            System.out.println("enemy hit enemy");
                            break;
                        case TypeComponent.SCENERY:
                            System.out.println("enemy hit scenery");
                            break;
                        case TypeComponent.OTHER:
                            System.out.println("enemy hit other");
                            break;
                        default:
                            System.out.println("No matching type found");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }else {
                    System.out.println("Enemy: collidedEntity.type == null");
                }
        }else{
            cc.collisionEntity = null;
                }
            }
        }
    }
