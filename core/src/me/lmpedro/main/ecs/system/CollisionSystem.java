package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.*;

import java.util.Random;

public class CollisionSystem extends IteratingSystem {

    private final Main context;
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<EnemyComponent> em;

    @SuppressWarnings("unchecked")
    public CollisionSystem(Main context) {

        super(Family.all(CollisionComponent.class).get());

        this.context = context;
        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
        em = ComponentMapper.getFor(EnemyComponent.class);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get collision for this entity
        CollisionComponent cc = cm.get(entity);
        //get collided entity
        Entity collidedEntity = cc.collisionEntity;


        TypeComponent thisType = entity.getComponent(TypeComponent.class);

        // Do Player Collisions
        if (thisType.type == TypeComponent.PLAYER) {
            PlayerComponent player = pm.get(entity);
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.HEALTH:
                            B2DComponent b2DComponent = ECSEngine.b2DMapper.get(collidedEntity);
                            b2DComponent.isDead = true;
                            if (player.health < 100){
                                player.health += 10;
                                if (player.health > 100){
                                    player.health = 100;
                                }
                            }
                            break;
                        case TypeComponent.ENEMY:
                            //do player hit enemy thing
                            player.health -= 20;
                            System.out.println("player hit enemy" + player.health);
                            if (player.health == 0) {
                                player.isDead = true;
                                System.out.println("Player is Dead");
                            }
                            break;
                        case TypeComponent.SCENERY:
                            //do player hit scenery thing
                            System.out.println("player hit scenery");
                            break;
                        case TypeComponent.OTHER:
                            //do player hit other thing
                            System.out.println("player hit other");
                            break;
                        case TypeComponent.BULLET:
                            BulletComponent bullet = ECSEngine.bulletMapper.get(collidedEntity);
                            if (bullet.owner != BulletComponent.Owner.PLAYER) {
                                player.health -= 2;
                                bullet.isDead = true;
                                System.out.println("player hit Bullet" + player.health);
                                if (player.health == 0) {
                                    player.isDead = true;
                                    System.out.println("Player is Dead");
                                }
                            }
                            break;
                        default:
                            System.out.println("No matching type found");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                } else {
                    /*System.out.println("Player: collidedEntity.type == null");*/
                }
            }
        } else if (thisType.type == TypeComponent.ENEMY) {    // Do enemy collisions//
            EnemyComponent enemy = em.get(entity);
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.PLAYER:
                            System.out.println("enemy hit player");
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
                        case TypeComponent.BULLET:
                            System.out.println("enemy hit bullet");
                            BulletComponent bullet = ECSEngine.bulletMapper.get(collidedEntity);
                            PlayerComponent player = context.getWorldFactory().player.getComponent(PlayerComponent.class);
                            B2DComponent b2DComponent = ECSEngine.b2DMapper.get(entity);
                            if (bullet.owner != BulletComponent.Owner.ENEMY) {
                                enemy.health -= 50;
                                bullet.isDead = true;
                                if (enemy.health == 0) {
                                    enemy.isDead = true;
                                    player.score += 10;

                                    Random random = new Random();

                                    int min = 0;
                                    int max = 500;
                                    if (random.nextInt(max - min + 1) + min > 250) {
                                        System.out.println("health made");
                                        context.getWorldFactory().createHealth(b2DComponent.body.getPosition().x, b2DComponent.body.getPosition().y, 1, 1);
                                    }
                                }
                            }
                                System.out.println("enemy got shot, Enemy Health: " + enemy.health);
                                break;
                                default:
                                    System.out.println("No matching type found");
                            }
                            cc.collisionEntity = null; // collision handled reset component
                    } else{
                        /*System.out.println("Enemy: collidedEntity.type == null");*/
                    }
                } else {
                    cc.collisionEntity = null;
                }
            }
        }
    }

