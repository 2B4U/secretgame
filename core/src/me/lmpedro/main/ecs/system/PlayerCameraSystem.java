package me.lmpedro.main.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;

public class PlayerCameraSystem extends IteratingSystem {
    private final OrthographicCamera gameCam;
    public PlayerCameraSystem(final Main context) {
        super(Family.all(PlayerComponent.class, B2DComponent.class).get());
        gameCam = context.getGameCam();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        gameCam.position.set(ECSEngine.b2DMapper.get(entity).body.getPosition(),0);
        gameCam.update();
    }
}
