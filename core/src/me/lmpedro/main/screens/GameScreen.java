package me.lmpedro.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import me.lmpedro.main.Main;
import me.lmpedro.main.WorldContactListener;
import me.lmpedro.main.audio.AudioType;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputManager;
import me.lmpedro.main.map.Map;
import me.lmpedro.main.map.MapListener;
import me.lmpedro.main.map.MapManager;
import me.lmpedro.main.map.MapType;
import me.lmpedro.main.ui.GameUI;

import static me.lmpedro.main.Main.UNIT_SCALE;
import static me.lmpedro.main.input.GameKeys.EXIT;
import static me.lmpedro.main.map.MapType.MAP_2;

public class GameScreen extends AbstractScreen<GameUI> implements MapListener{

    private final AssetManager assetManager;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera gameCam;
    private final GLProfiler profiler;
    private final MapManager mapManager;
    private static final String ID = Main.class.getSimpleName();


    public GameScreen(final Main context) {
        super(context);

        assetManager = context.getAssetManager();
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, context.getSpriteBatch());
        this.gameCam = context.getGameCam();

        profiler = new GLProfiler(Gdx.graphics);
/*        profiler.enable();*/


        mapManager = context.getMapManager();
        mapManager.addMapListener(this);
        mapManager.setMap(MAP_2);

            context.getEcsEngine().createPlayer(mapManager.getCurrentMap().getStartLocation(), 0.7f, 0.7f);
            context.getEcsEngine().createEnemy(1, 1, 1, 1);


    }

    @Override
    protected GameUI getScreenUI(Main context) {
        return new GameUI(context);
    }


    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.getBatch().setProjectionMatrix(gameCam.combined);

        viewport.apply(false );
        if (mapRenderer.getMap() != null){
            mapRenderer.setView(gameCam);
            mapRenderer.render();
        }

        box2DDebugRenderer.render(world, viewport.getCamera().combined);

        screenUI.updateUi(delta,context);
        audioManager.playAudio(AudioType.INTRO);

        profiler.reset();

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
            mapManager.setMap(MAP_2);

        }

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
        mapRenderer.dispose();
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        if (manager.isKeyDown(EXIT)){
            Gdx.app.exit();
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {

    }

    @Override
    public void mapChange(final Map map) {
        mapRenderer.setMap(map.getTiledMap());
        map.getTiledMap().getLayers().getByType(TiledMapTileLayer.class);
    }
}
