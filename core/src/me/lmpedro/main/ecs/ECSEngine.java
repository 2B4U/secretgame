package me.lmpedro.main.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.EnemyComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.ecs.system.EnemySystem;
import me.lmpedro.main.ecs.system.PlayerCameraSystem;
import me.lmpedro.main.ecs.system.PlayerMovementSystem;

import static me.lmpedro.main.Main.*;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> b2DMapper = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyMapper = ComponentMapper.getFor(EnemyComponent.class);

    private final World world;
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    public ECSEngine(final Main context){
        super();

        world = context.getWorld();
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        this.addSystem(new PlayerMovementSystem(context));
        this.addSystem(new PlayerCameraSystem(context));
        this.addSystem(new EnemySystem(context));
    }

    public void createPlayer(final Vector2 playerStartPos, final float width, final float height){
        final Entity player = this.createEntity();

        //add Player Component
        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed.set(5,5);
        playerComponent.health = 100;
        player.add(playerComponent);

        //add Box2d component
        resetBodiesAndFixtures();
        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        bodyDef.position.set(playerStartPos.x, playerStartPos.y + height * 0.5f);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2DComponent.body = world.createBody(bodyDef);
        b2DComponent.body.setUserData("PLAYER");
        b2DComponent.width = width;
        b2DComponent.height = height;

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_SENSOR;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(width * 0.5f,height * 0.5f);
        fixtureDef.shape = pShape;
        b2DComponent.body.createFixture(fixtureDef);
        pShape.dispose();

        player.add(b2DComponent);
        this.addEntity(player);
    }

    public void createEnemy(float x, float y, final float width, final float height){

        //create enemy
        Entity enemy = this.createEntity();
        EnemyComponent enemyComponent = this.createComponent(EnemyComponent.class);
        enemyComponent.xPosCenter = x;


        //create Box2d component
        resetBodiesAndFixtures();
        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        b2DComponent.body = world.createBody(bodyDef);
        b2DComponent.body.setUserData("ENEMY");
        b2DComponent.width = width;
        b2DComponent.height = height;


        fixtureDef.filter.categoryBits = BIT_ENEMY;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape eShape = new PolygonShape();
        eShape.setAsBox(width * 0.5f, height * 0.5f);
        fixtureDef.shape = eShape;
        b2DComponent.body.createFixture(fixtureDef);
        eShape.dispose();

        //create agro sensor

        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BIT_SENSOR;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        final CircleShape aShape = new CircleShape();
        aShape.setRadius(9);
        b2DComponent.body.setUserData("AGGRO");
        fixtureDef.shape = aShape;
        b2DComponent.body.createFixture(fixtureDef);
        aShape.dispose();

        enemy.add(b2DComponent);
        enemy.add(enemyComponent);
        this.addEntity(enemy);

    }
}
