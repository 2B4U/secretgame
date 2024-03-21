package me.lmpedro.main.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.PlayerComponent;


public class GameUI extends Table {


    private final Label fpsLabel;
    private final Label mousePositionX;
    private final Label mousePositionY;
    private final Label playerPosX;
    private final Label playerPosY;
    private final Label playerHealth;
    private final Label playerScore;
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
        playerHealth = new Label("playerHealth:", getSkin(),"huge");
        playerScore = new Label("playerScore:", getSkin(),"huge");

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
        row();
        add(playerHealth);
        row();
        add(playerScore);
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
        playerHealth.setText("Health: " + context.getWorldFactory().player.getComponent(PlayerComponent.class).getHealth());
        playerScore.setText("Score: " + context.lastScore);

    }
}
