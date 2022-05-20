package me.lmpedro.main.ui;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.input.GameKeys;


public class GameUI extends Table {
    /*    private final TextArea textArea ;*/

    private final Label fpsLabel;
    private final Label mousePositionX;
    private final Label mousePositionY;
    private final Label playerPosX;
    private final Label playerPosY;
    private final Label maxSpritesInBatch;
    private final Label test1;
    private final Label test2;
/*
    private final Label playerCords;
*/


    public GameUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);


        fpsLabel = new Label("fps:", getSkin(), "huge");

        mousePositionX = new Label("position:", getSkin(),"huge");
        mousePositionY = new Label("position:", getSkin(),"huge");

        playerPosX = new Label("playerPos:", getSkin(), "huge");
        playerPosY = new Label("playerPos:", getSkin(), "huge");

        maxSpritesInBatch = new Label("MaxSpritesInBatch",getSkin(),"huge");
        test1 = new Label("test1", getSkin(), "huge");
        test2 = new Label("test2", getSkin(), "huge");


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
        add(test1);
        row();
        add(test2);
        top().left();
        setDebug(true, true);
    }

    public void updateUi(float delta, Main context){
        mousePositionX.setText("MouseXPos: " + Gdx.input.getX());
        mousePositionY.setText("MouseYPos: " +  Gdx.input.getY());
        playerPosX.setText("PlayerPosX " + context.getGameCam().position.x);
        playerPosY.setText("PlayerPosY " + context.getGameCam().position.y);
        maxSpritesInBatch.setText("MaxSpritesInBatch " + context.getSpriteBatch().maxSpritesInBatch);
        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }
}
