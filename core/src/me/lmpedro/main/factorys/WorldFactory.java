package me.lmpedro.main.factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.EnemyComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;

import static me.lmpedro.main.Main.resetBodiesAndFixtures;

public class WorldFactory {

    private final BodyFactory bodyFactory;
    public World world;
    public ECSEngine engine;
    public Main main;

    public WorldFactory(final Main context){
        engine = context.getEcsEngine();
        world = context.getWorld();
        bodyFactory = BodyFactory.getInstance(world);
    }

    public void createPlayer(final Vector2 playerStartPos, final float width, final float height){
        final Entity entity = engine.createEntity();

        //add Player Component
        final PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        playerComponent.speed.set(5,5);
        playerComponent.health = 100;
        entity.add(playerComponent);

        //add Box2d component
        resetBodiesAndFixtures();
        final B2DComponent b2DComponent = engine.createComponent(B2DComponent.class);
        b2DComponent.body = bodyFactory.makeBoxPolyBody(playerStartPos.x,playerStartPos.y + height * 0.5f,1,1,BodyFactory.PLAYER, BodyDef.BodyType.DynamicBody);
        b2DComponent.body.setUserData("PLAYER");

        entity.add(b2DComponent);
        engine.addEntity(entity);
    }

    public void createEnemy(float x, float y, final float width, final float height){

        //create enemy
        Entity entity = engine.createEntity();
        EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
        enemyComponent.xPosCenter = x;


        //create Box2d component
        resetBodiesAndFixtures();
        final B2DComponent b2DComponent = engine.createComponent(B2DComponent.class);
        b2DComponent.body = bodyFactory.makeCirclePolyBody(x,y,1, BodyFactory.ENEMY, BodyDef.BodyType.DynamicBody);
        b2DComponent.body.setUserData("ENEMY");


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

        entity.add(b2DComponent);
        entity.add(enemyComponent);
        engine.addEntity(entity);

    }
}
