package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.BulletComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.factorys.WorldFactory;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputListener;
import me.lmpedro.main.input.InputManager;

public class PlayerControlSystem extends IteratingSystem implements InputListener {
    private static final String ID = Main.class.getSimpleName();
    private boolean directionChange;
    private final WorldFactory worldFactory;
    private final InputManager manager;
    private final Main context;

    private final World world;
    private int xFactor;
    private int yFactor;


    public PlayerControlSystem(Main context, World world) {
        super(Family.all(PlayerComponent.class).get());
        context.getInputManager().addInputListener(this);
        this.context = context;
        this.world = world;
        manager = context.getInputManager();
        worldFactory = context.getWorldFactory();
        directionChange = false;
        xFactor = yFactor = 0;
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {

        if (directionChange) {
            final PlayerComponent playerComponent = ECSEngine.playerMapper.get(entity);
            final B2DComponent b2DComponent = ECSEngine.b2DMapper.get(entity);

            directionChange = false;
            b2DComponent.body.applyLinearImpulse(
                    (xFactor * playerComponent.speed.x - b2DComponent.body.getLinearVelocity().x) * b2DComponent.body.getMass(),
                    (yFactor * playerComponent.speed.y - b2DComponent.body.getLinearVelocity().y) * b2DComponent.body.getMass(),
                    b2DComponent.body.getWorldCenter().x, b2DComponent.body.getWorldCenter().y, true);
        }

        final PlayerComponent playerComponent = ECSEngine.playerMapper.get(entity);
        final B2DComponent b2DComponent = ECSEngine.b2DMapper.get(entity);

        if (playerComponent.timeSinceLastShot > 0) {
            playerComponent.timeSinceLastShot -= deltaTime;
        }

        if (manager.isMouse1Down) { // if mouse button is pressed
            // user wants to fire
                if (playerComponent.timeSinceLastShot <= 0) { // check the player hasn't just shot
                    //player can shoot so do player shoot
                    Vector3 mousePos = new Vector3(manager.mouseLocation.x, manager.mouseLocation.y, 0); // get mouse position
                    playerComponent.cam.unproject(mousePos); // convert position from screen to box2d world position
                    float speed = 10f;  // set the speed of the bullet
                    float shooterX = b2DComponent.body.getPosition().x; // get player location
                    float shooterY = b2DComponent.body.getPosition().y; // get player location
                    float velx = mousePos.x - shooterX; // get distance from shooter to target on x plain
                    float vely = mousePos.y - shooterY; // get distance from shooter to target on y plain

                    float velx1 = mousePos.x + 0.5f - shooterX; // get distance from shooter to target on x plain
                    float vely1 = mousePos.y + 0.5f - shooterY; // get distance from shooter to target on y plain

                    float velx2 = mousePos.x - 0.5f - shooterX; // get distance from shooter to target on x plain
                    float vely2 = mousePos.y - 0.5f - shooterY; // get distance from shooter to target on y plain
                    float length = (float) Math.sqrt(velx * velx + vely * vely); // get distance to target direct
                    if (length != 0) {
                        velx = velx / length;  // get required x velocity to aim at target
                        vely = vely / length;  // get required y velocity to aim at target
                        velx1 = velx1 / length;
                        vely1 = vely1 / length;
                        velx2 = velx2 / length;
                        vely2 = vely2 / length;
                    }
                    final WorldFactory worldFactory = new WorldFactory(context);
                    // create a bullet

                    worldFactory.createBullet(shooterX, shooterY, velx * speed, vely * speed, BulletComponent.Owner.PLAYER);

/*
                    //create 3shot cluster
                    worldFactory.createBullet(shooterX, shooterY, velx1 * speed, vely * speed, BulletComponent.Owner.PLAYER);
                    worldFactory.createBullet(shooterX, shooterY, velx * speed, vely * speed, BulletComponent.Owner.PLAYER);
                    worldFactory.createBullet(shooterX, shooterY, velx * speed, vely2 * speed, BulletComponent.Owner.PLAYER);
*/

/*                //create 3shot spread
                worldFactory.createBullet(shooterX, shooterY, velx1 * speed, vely1 * speed);
                worldFactory.createBullet(shooterX, shooterY, velx * speed, vely * speed);
                worldFactory.createBullet(shooterX, shooterY, velx2 * speed, vely2 * speed);*/

                    //reset timeSinceLastShot
                    playerComponent.timeSinceLastShot = playerComponent.shootDelay;
                }
            }
        }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {

        switch (key) {
            case LEFT:
                directionChange = true;
                xFactor = -1;
                Gdx.app.debug(ID, "KeyPressd Left " + xFactor);
                break;
            case RIGHT:
                directionChange = true;
                xFactor = 1;
                Gdx.app.debug(ID, "KeyPressd right " + xFactor);
                break;
            case UP:
                directionChange = true;
                yFactor = 1;
                Gdx.app.debug(ID, "KeyPressd Up " + xFactor);
                break;
            case DOWN:
                directionChange = true;
                yFactor = -1;
                Gdx.app.debug(ID, "KeyPressd Down " + xFactor);
                break;
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
        switch (key) {
            case LEFT:
                directionChange = true;
                xFactor = manager.isKeyDown(GameKeys.RIGHT) ? 1 : 0;
                Gdx.app.debug(ID, "KeyUPLeft " + xFactor);
                break;
            case RIGHT:
                directionChange = true;
                xFactor = manager.isKeyDown(GameKeys.LEFT) ? -1 : 0;
                Gdx.app.debug(ID, "KeyUpRIght " + xFactor);
                break;
            case UP:
                directionChange = true;
                yFactor = manager.isKeyDown(GameKeys.DOWN) ? -1 : 0;
                break;
            case DOWN:
                directionChange = true;
                yFactor = manager.isKeyDown(GameKeys.UP) ? 1 : 0;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
