package me.lmpedro.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.PlayerComponent;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputListener;
import me.lmpedro.main.input.InputManager;
import me.lmpedro.main.ui.DeathUI;

public class DeathScreen extends AbstractScreen<DeathUI> implements InputListener {

    public DeathScreen(final Main context) {
        super(context);

    }

    @Override
    protected DeathUI getScreenUI(Main context) {
        return new DeathUI(context);
    }

    @Override
    public void render(final float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        /*Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/

        viewport.apply();

        screenUI.updateUi(delta, context);

        /*        audioManager.playAudio(AudioType.DEATH);*/
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
        context.dispose();
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            context.setScreen(ScreenType.MAINMENU);
            context.getWorldFactory().player.getComponent(PlayerComponent.class).score = 0;
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
