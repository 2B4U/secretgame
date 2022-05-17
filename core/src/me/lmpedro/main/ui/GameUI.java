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
/*
    private final Label playerCords;
*/




    public GameUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);

        hudCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCam.position.set(hudCam.viewportWidth / 2f, hudCam.viewportHeight / 2f,0);

        fpslabel = new Label("fps:", context.getSkin(), "huge");
        fpslabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());

/*        playerCords = new Label("playerCords:", context.getSkin(), "huge");
        playerCords.setText("playerCords: X:" + context.getBox2DDebugRenderer().);*/


/*
        textArea = new TextArea("[Red]DeBug Info // FPS: " + Gdx.graphics.getFramesPerSecond(), skin, "huge");
        textArea.setY(0);
        textArea.setX(100);
        textArea.setWidth(100);
        textArea.setHeight(120);*/
/*        debug = new TextField("[Red]Debug Info // FPS: ",skin, "huge");
        debug.setX(50);
        debug.setY(50);
        debug.setWidth(50);
        debug.setHeight(50);

        debug.setVisible(true);*/


//        add(debug);

        add(fpslabel).colspan(2);
        row();
        top().left();
        setDebug(true, true);
    }


}
