package me.lmpedro.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import me.lmpedro.main.Main;
import me.lmpedro.main.audio.AudioType;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputListener;
import me.lmpedro.main.input.InputManager;
import me.lmpedro.main.ui.MainMenuUI;

public class MainMenuScreen extends AbstractScreen<MainMenuUI> implements InputListener {
    private final AssetManager assetManager;


    public MainMenuScreen(final Main context) {
        super(context);

        this.assetManager = context.getAssetManager();
        assetManager.load("map/map.tmx", TiledMap.class);

    }

    @Override
    protected MainMenuUI getScreenUI(Main context) {
        return new MainMenuUI(context);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        audioManager.playAudio(AudioType.INTRO);

/*        audioManager.playAudio(AudioType.INTRO);*/

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {

    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {

    }
}
