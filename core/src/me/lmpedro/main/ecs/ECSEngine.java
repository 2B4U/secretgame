package me.lmpedro.main.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.ecs.system.PlayerCameraSystem;
import me.lmpedro.main.ecs.system.PlayerMovementSystem;

import static me.lmpedro.main.Main.BIT_GROUND;
import static me.lmpedro.main.Main.BIT_PLAYER;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> b2DMapper = ComponentMapper.getFor(B2DComponent.class);

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
    }

    private void resetBodiesAndFixtures() {
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.density = 0;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = null;
    }

    public void createPlayer(final Vector2 playerStartPos, final float width, final float height){
        final Entity player = this.createEntity();

        //add Player Component
        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed.set(3,3);
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
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(width * 0.5f,height * 0.5f);
        fixtureDef.shape = pShape;
        b2DComponent.body.createFixture(fixtureDef);
        pShape.dispose();

        player.add(b2DComponent);
        this.addEntity(player);
    }
}
