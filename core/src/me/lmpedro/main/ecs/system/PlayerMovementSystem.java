package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.BulletComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.ecs.components.TransformComponent;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputListener;
import me.lmpedro.main.input.InputManager;

public class PlayerMovementSystem extends IteratingSystem implements InputListener {
    private static final String ID = Main.class.getSimpleName();
    private boolean directionChange;
    private int xFactor;
    private int yFactor;

    public PlayerMovementSystem(final Main context) {
        super(Family.all(PlayerComponent.class, B2DComponent.class, BulletComponent.class, TransformComponent.class).get());
        context.getInputManager().addInputListener(this);
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
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        if (manager.isMouse1Down){
            final PlayerComponent player = new PlayerComponent();
            final Entity entity = new Entity();
            final B2DComponent b2DComponent = ECSEngine.b2DMapper.get(entity);
            if (player.timeSinceLastShot <= 0){
                Vector3 mousePos = new Vector3(manager.mouseLocation.x,manager.mouseLocation.y,0);
                float speed = 10f;
                float shooterX = b2DComponent.body.getPosition().x;
                float shooterY = b2DComponent.body.getPosition().y;
                float velX = mousePos.x - shooterX;
                float velY = mousePos.y - shooterY;
                float length = (float) Math.sqrt(velX * velX + velY * velY);
                if (length != 0){
                    velX = velX / length;
                    velY = velY / length;
                }
                ECSEngine.createBullet(shooterX,shooterY,velX*speed, velY*speed);
                player.timeSinceLastShot = player.shootDelay;
            }
        }

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
}
