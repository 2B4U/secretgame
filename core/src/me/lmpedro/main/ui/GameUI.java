package me.lmpedro.main.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.PlayerComponent;


public class GameUI extends Table {
    /*    private final TextArea textArea ;*/

    private final Label fpsLabel;
    private final Label mousePositionX;
    private final Label mousePositionY;
/*
    private final Label playerCords;
*/


    public GameUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);


        fpsLabel = new Label("fps:", getSkin(), "huge");


        mousePositionX = new Label("position:", getSkin(),"huge");
        mousePositionY = new Label("position:", getSkin(),"huge");


        add(mousePositionX);
        row();
        add(mousePositionY);
        row();
        add(fpsLabel);
        top().left();
        setDebug(true, true);
    }

    public void updateUi(float delta){
        mousePositionX.setText("MouseX Pos: " + Gdx.input.getX());
        mousePositionY.setText("MouseY Pos: " + Gdx.input.getY());
        fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }
}
