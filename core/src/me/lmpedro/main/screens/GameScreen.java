package me.lmpedro.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import me.lmpedro.main.Main;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputManager;
import me.lmpedro.main.map.CollisionArea;
import me.lmpedro.main.map.Map;
import me.lmpedro.main.ui.GameUI;

import static me.lmpedro.main.Main.BIT_GROUND;
import static me.lmpedro.main.Main.UNIT_SCALE;
import static me.lmpedro.main.input.GameKeys.EXIT;

public class GameScreen extends AbstractScreen<GameUI> {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private Body player;
    private SpriteBatch batch;

    private final AssetManager assetManager;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera gameCam;
    private final GLProfiler profiler;
    private final me.lmpedro.main.map.Map map;
    private static final String ID = Main.class.getSimpleName();


    public GameScreen(final Main context) {
        super(context);

        assetManager = context.getAssetManager();
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, context.getSpriteBatch());
        this.gameCam = context.getGameCam();

        batch = new SpriteBatch();

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        final TiledMap tiledMap = assetManager.get("map/map.tmx", TiledMap.class);
        mapRenderer.setMap(tiledMap);
        map = new Map(tiledMap);

        spawnCollisionArea();
        context.getEcsEngine().createPlayer(map.getStartLocation(), 1,1);
        context.getEcsEngine().createEnemy(map.getStartLocation().x ,map.getStartLocation().y,1,1);
    }

    @Override
    protected GameUI getScreenUI(Main context) {
        return new GameUI(context);
    }



    private void spawnCollisionArea() {
        final BodyDef bodyDef = new BodyDef();
        final FixtureDef fixtureDef = new FixtureDef();
        for (final CollisionArea collisionArea : map.getCollisionAreas()) {

            //Create Room
            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.fixedRotation = true;
            final Body body = world.createBody(bodyDef);
            body.setUserData("Ground");

            fixtureDef.filter.categoryBits = BIT_GROUND;
            fixtureDef.filter.maskBits = -1;
            final ChainShape chainShape = new ChainShape();
            chainShape.createChain(collisionArea.getVertices());
            fixtureDef.shape = chainShape;
            body.createFixture(fixtureDef);
            chainShape.dispose();
        }
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.getBatch().setProjectionMatrix(gameCam.combined);

        viewport.apply(false );

        mapRenderer.setView(gameCam);
        mapRenderer.render();
        box2DDebugRenderer.render(world, viewport.getCamera().combined);

        screenUI.updateUi(delta);

        profiler.reset();

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
}
