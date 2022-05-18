package me.lmpedro.main.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;


public class GameUI extends Table {
    /*    private final TextArea textArea ;*/

    private final OrthographicCamera hudCam;
    private final Label fpslabel;
    private final Label mousePositionX;
    private final Label mousePositionY;
/*
    private final Label playerCords;
*/




    public GameUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);

        hudCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCam.position.set(hudCam.viewportWidth / 2f, hudCam.viewportHeight / 2f,0);

        fpslabel = new Label("fps:", getSkin(), "huge");
        fpslabel.setVisible(true);

        mousePositionX = new Label("position:", getSkin(),"huge");
        mousePositionY = new Label("position:", getSkin(),"huge");


        add(mousePositionX);
        row();
        add(mousePositionY);
        row();
        add(fpslabel);
        top().left();
        setDebug(true, true);
    }

    public void updateUi(float delta){
        mousePositionX.setText("MouseX Pos: " + Gdx.input.getX());
        mousePositionY.setText("MouseY Pos: " + Gdx.input.getY());
        fpslabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }
}
