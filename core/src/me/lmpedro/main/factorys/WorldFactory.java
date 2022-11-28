package me.lmpedro.main.factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.*;

public class WorldFactory {
    private BodyFactory bodyFactory;
    private World world;
    private ECSEngine engine;


    public Entity getPlayer() {
        return player;
    }

    public Entity player;

    public WorldFactory(Main context) {
        engine = context.getEcsEngine();
        world = context.getWorld();

        bodyFactory = BodyFactory.getInstance(world);
    }

        public Entity createPlayer ( final Vector2 playerStartPos, final float width, final float height, OrthographicCamera cam){
            Entity entity = engine.createEntity();

            PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
            CollisionComponent collision = engine.createComponent(CollisionComponent.class);
            B2DComponent b2DComponent = engine.createComponent(B2DComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);

            //add Player Component
            playerComponent.cam = cam;
            playerComponent.speed.set(5, 5);
            playerComponent.health = 100;
            playerComponent.mana = 100;

            //add Box2d component
            /*        resetBodiesAndFixtures();*/
            b2DComponent.body = bodyFactory.makeBoxPolyBody(playerStartPos.x, playerStartPos.y + height * 0.5f, 1, 1, BodyFactory.PLAYER, BodyDef.BodyType.DynamicBody);
            b2DComponent.body.setUserData(entity);
            type.type = TypeComponent.PLAYER;
            position.position.set(playerStartPos.x, playerStartPos.y, 0);


            entity.add(position);
            entity.add(playerComponent);
            entity.add(type);
            entity.add(collision);
            entity.add(b2DComponent);

            engine.addEntity(entity);
            this.player = entity;

            return entity;
        }

        public Entity createEnemy ( float x, float y, final float width, final float height){

            //create enemy
            Entity entity = engine.createEntity();
            EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
            CollisionComponent collision = engine.createComponent(CollisionComponent.class);
            B2DComponent b2DComponent = engine.createComponent(B2DComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);

            enemyComponent.xPosCenter = x;
            //create Box2d component
            /*        resetBodiesAndFixtures();*/
            b2DComponent.body = bodyFactory.makeCirclePolyBody(x, y, 1, BodyFactory.ENEMY, BodyDef.BodyType.DynamicBody);
            b2DComponent.body.setUserData(entity);
            type.type = TypeComponent.ENEMY;
            position.position.set(x, y, 0);


/*        //create agro sensor

        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BIT_SENSOR;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        final CircleShape aShape = new CircleShape();
        aShape.setRadius(9);
        b2DComponent.body.setUserData("AGGRO");
        fixtureDef.shape = aShape;
        b2DComponent.body.createFixture(fixtureDef);
        aShape.dispose();*/

            entity.add(position);
            entity.add(enemyComponent);
            entity.add(type);
            entity.add(collision);
            entity.add(b2DComponent);
            engine.addEntity(entity);

            return entity;
        }

        public Entity createBullet ( float x, float y, float xVel, float yVel){
            System.out.println("Making bullet" + x + ":" + y + ":" + xVel + ":" + yVel);
            Entity entity = engine.createEntity();
            B2DComponent b2dbody = engine.createComponent(B2DComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
            BulletComponent bul = engine.createComponent(BulletComponent.class);

            b2dbody.body = bodyFactory.makeCirclePolyBody(x, y, 0.5f, BodyFactory.PLAYER, BodyDef.BodyType.DynamicBody);
            b2dbody.body.setBullet(true); // increase physics computation to limit body travelling through other objects
            bodyFactory.makeAllFixturesSensors(b2dbody.body); // make bullets sensors so they don't move player
            position.position.set(x, y, 0);
            type.type = TypeComponent.BULLET;
            b2dbody.body.setUserData(entity);
            bul.xVel = xVel;
            bul.yVel = yVel;

            entity.add(bul);
            entity.add(colComp);
            entity.add(b2dbody);
            entity.add(position);

            entity.add(type);

            engine.addEntity(entity);
            return entity;
        }
    }
