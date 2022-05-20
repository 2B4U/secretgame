package me.lmpedro.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import me.lmpedro.main.Main;
import me.lmpedro.main.audio.AudioType;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputManager;
import me.lmpedro.main.ui.LoadingUI;

public class LoadingScreen extends AbstractScreen<LoadingUI> {

    private final AssetManager assetManager;
    private boolean isMusicLoaded;

    public LoadingScreen(final Main context) {
        super(context);

        this.assetManager = context.getAssetManager();
        assetManager.load("map/map.tmx", TiledMap.class);

        //load audio files
        isMusicLoaded = false;
        for (final AudioType audioType : AudioType.values()) {
            assetManager.load(audioType.getFilePath(), audioType.isMusic() ? Music.class : Sound.class);
        }
    }

    @Override
    protected LoadingUI getScreenUI(final Main context) {
        return new LoadingUI(context);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(false);

        assetManager.update();
        if (!isMusicLoaded && assetManager.isLoaded(AudioType.INTRO.getFilePath())) {
            isMusicLoaded = true;
            audioManager.playAudio(AudioType.INTRO);
        }
        screenUI.setProgress(assetManager.getProgress());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
        audioManager.stopCurrentMusic();
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
        audioManager.playAudio(AudioType.SELECT);
        if (assetManager.getProgress() >= 1) {
            context.setScreen(ScreenType.MAINMENU);
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {

    }
}
