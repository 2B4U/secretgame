package me.lmpedro.main.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;
import me.lmpedro.main.WorldContactListener;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.ecs.components.EnemyComponent;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.input.GameKeys;


public class GameUI extends Table {


    private final Label fpsLabel;
    private final Label mousePositionX;
    private final Label mousePositionY;
    private final Label playerPosX;
    private final Label playerPosY;
    private final Label maxSpritesInBatch;
    private final Label javaHeap;
    private final Label nativeHeap;
    private final Label entities;


    public GameUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);


        fpsLabel = new Label("fps:", getSkin(), "huge");

        mousePositionX = new Label("position:", getSkin(),"huge");
        mousePositionY = new Label("position:", getSkin(),"huge");

        playerPosX = new Label("playerPos:", getSkin(), "huge");
        playerPosY = new Label("playerPos:", getSkin(), "huge");

        maxSpritesInBatch = new Label("MaxSpritesInBatch",getSkin(),"huge");
        javaHeap = new Label("javaHeap", getSkin(), "huge");
        nativeHeap = new Label("nativeHeap", getSkin(), "huge");
        entities = new Label("entities", getSkin(), "huge");


        add(mousePositionX);
        row();
        add(mousePositionY);
        row();
        add(playerPosX);
        row();
        add(playerPosY);
        row();
        add(fpsLabel);
        row();
        add(maxSpritesInBatch);
        row();
        add(nativeHeap);
        row();
        add(javaHeap);
        row();
        add(entities);
        top().left();
        setDebug(true, true);
    }

    public void updateUi(float delta, Main context){
        mousePositionX.setText("MouseXPos: " + Gdx.input.getX());
        mousePositionY.setText("MouseYPos: " +  Gdx.input.getY());
        playerPosX.setText("PlayerPosX " + context.getGameCam().position.x);
        playerPosY.setText("PlayerPosY " + context.getGameCam().position.y);
        maxSpritesInBatch.setText("MaxSpritesInBatch " + context.getSpriteBatch().maxSpritesInBatch);
        nativeHeap.setText("NativeHeap: " + Gdx.app.getNativeHeap()/1024/1024+"MB");
        javaHeap.setText("javaHeap: " + Gdx.app.getJavaHeap()/1024/1204+"MB");
        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        entities.setText("Entities: " + context.getEcsEngine().getEntities().size());
    }
}
