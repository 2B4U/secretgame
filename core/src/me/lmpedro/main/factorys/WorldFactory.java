package me.lmpedro.main.factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import me.lmpedro.main.Main;
import me.lmpedro.main.ai.SteeringPresets;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.*;

public class WorldFactory {
    private BodyFactory bodyFactory;
    private World world;
    private ECSEngine engine;



    public Entity player;

    public WorldFactory(Main context) {
        engine = context.getEcsEngine();
        world = context.getWorld();

        bodyFactory = BodyFactory.getInstance(world);
    }

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public BodyFactory getBodyFactory() {
        return bodyFactory;
    }

    public void setBodyFactory(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }


    public Entity createHealth (float x, float y, final float width, final float height){

        Entity entity = engine.createEntity();
        B2DComponent b2dbody = engine.createComponent(B2DComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);

        TypeComponent type = engine.createComponent(TypeComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        SteeringComponent steering = engine.createComponent(SteeringComponent.class);
        EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);

        b2dbody.body = bodyFactory.makeCirclePolyBody(x, y, 0.5f, BodyFactory.HEALTH, BodyDef.BodyType.DynamicBody);


        position.position.set(x, y, 0);
        type.type = TypeComponent.HEALTH;
        b2dbody.body.setUserData(entity);
        b2dbody.body.setBullet(true); // increase physics computation to limit body travelling through other objects
        bodyFactory.makeAllFixturesSensors(b2dbody.body); // make bullets sensors so they don't move player
        steering.body = b2dbody.body;

        steering.steeringBehavior = SteeringPresets.getWander(steering);
        steering.currentMode = SteeringComponent.SteeringState.WANDER;

        enemyComponent.enemyType = EnemyComponent.Type.HEALTH;

        entity.add(colComp);
        entity.add(b2dbody);
        entity.add(position);
        entity.add(steering);
        entity.add(enemyComponent);

        entity.add(type);

        engine.addEntity(entity);
        return entity;
    }

    public Entity createPlayer (final Vector2 playerStartPos, final float width, final float height, OrthographicCamera cam){
            Entity entity = engine.createEntity();

            PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
            CollisionComponent collision = engine.createComponent(CollisionComponent.class);
            B2DComponent b2DComponent = engine.createComponent(B2DComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);
            SteeringComponent steer = engine.createComponent(SteeringComponent.class);

            //add Player Component
            playerComponent.cam = cam;
            playerComponent.speed.set(5, 5);
            playerComponent.health = 100;
            playerComponent.mana = 100;
            playerComponent.score = 0;

            //add Box2d component
            /*        resetBodiesAndFixtures();*/
            b2DComponent.body = bodyFactory.makeBoxPolyBody(playerStartPos.x, playerStartPos.y + height * 0.5f, 1, 1, BodyFactory.PLAYER, BodyDef.BodyType.DynamicBody);
            b2DComponent.body.setUserData(entity);
            type.type = TypeComponent.PLAYER;
            position.position.set(playerStartPos.x, playerStartPos.y, 0);

            steer.body = b2DComponent.body;


            entity.add(position);
            entity.add(playerComponent);
            entity.add(type);
            entity.add(collision);
            entity.add(b2DComponent);
            entity.add(steer);

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
            SteeringComponent steer = engine.createComponent(SteeringComponent.class);

            enemyComponent.xPosCenter = x;
            enemyComponent.health = 100;
            //create Box2d component
            /*        resetBodiesAndFixtures();*/
            b2DComponent.body = bodyFactory.makeCirclePolyBody(x, y, 1, BodyFactory.ENEMY, BodyDef.BodyType.DynamicBody);
            b2DComponent.body.setUserData(entity);
            type.type = TypeComponent.ENEMY;
            position.position.set(x, y, 0);
            steer.body = b2DComponent.body;

            steer.steeringBehavior = SteeringPresets.getWander(steer);
            steer.currentMode = SteeringComponent.SteeringState.WANDER;

            enemyComponent.enemyType = EnemyComponent.Type.TEST1;


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
            entity.add(steer);
            engine.addEntity(entity);

            return entity;
        }

        public Entity createBullet (float x, float y, float xVel, float yVel, BulletComponent.Owner owner){
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
            bul.owner = owner;

            entity.add(bul);
            entity.add(colComp);
            entity.add(b2dbody);
            entity.add(position);

            entity.add(type);

            engine.addEntity(entity);
            return entity;
        }

    public void removeEntity(Entity ent){
        engine.removeEntity(ent);
    }

    public void resetWorld() {

        Array<Body> bods = new Array<>();
        world.getBodies(bods);
        for(Body bod:bods){
            world.destroyBody(bod);
        }
    }
}
