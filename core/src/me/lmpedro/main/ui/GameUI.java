package me.lmpedro.main.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;


public class GameUI extends Table {
    /*    private final TextArea textArea ;*/

    private final OrthographicCamera hudCam;
    private final Label fpslabel;
/*
    private final Label playerCords;
*/




    public GameUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);

        hudCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCam.position.set(hudCam.viewportWidth / 2f, hudCam.viewportHeight / 2f,0);

        fpslabel = new Label("fps:", context.getSkin(), "huge");
        fpslabel.setVisible(true);

        add(fpslabel);
        row();
        top().left();
        setDebug(true, true);
    }

    public void updateFps(float delta){
        fpslabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());
    }
}
