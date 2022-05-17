package me.lmpedro.main.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.lmpedro.main.Main;
import me.lmpedro.main.audio.AudioManager;
import me.lmpedro.main.input.InputListener;
import me.lmpedro.main.input.InputManager;

public abstract class AbstractScreen<T extends Table> implements Screen, InputListener {

    protected final Main context;
    protected final ExtendViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2DDebugRenderer;
    protected final Stage stage;
    protected final T screenUI;
    protected final InputManager inputManager;
    protected final AudioManager audioManager;

    public AbstractScreen(final Main context) {
        this.context = context;
        this.world = context.getWorld();
        viewport = context.getScreenViewport();
        this.box2DDebugRenderer = context.getBox2DDebugRenderer();
        inputManager = context.getInputManager();

        stage = context.getStage();
        screenUI = getScreenUI(context);

        audioManager = context.getAudioManager();
    }

    protected abstract T getScreenUI(final Main context);


    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        inputManager.addInputListener(this);
        stage.addActor(screenUI);
    }

    @Override
    public void hide() {
        inputManager.removeInputListener(this);
        stage.getRoot().removeActor(screenUI);
    }
}
