package me.lmpedro.main.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.PlayerComponent;


public class GameUI extends Table {
    /*    private final TextArea textArea ;*/

    private final OrthographicCamera hudCam;
    private final Label fpslabel;
    private final Label position;
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

        position = new Label("position:", context.getSkin(),"huge");


        add(fpslabel);
        row();
        add(position);
        top().left();
        setDebug(true, true);
    }

    public void updateUi(float delta){
        position.setText("Coming Soon");
        fpslabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }
}
