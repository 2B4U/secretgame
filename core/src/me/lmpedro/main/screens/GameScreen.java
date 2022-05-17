package me.lmpedro.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import me.lmpedro.main.Main;
import me.lmpedro.main.input.GameKeys;
import me.lmpedro.main.input.InputManager;
import me.lmpedro.main.map.CollisionArea;
import me.lmpedro.main.map.Map;
import me.lmpedro.main.ui.GameUI;

import static me.lmpedro.main.Main.*;

public class GameScreen extends AbstractScreen<GameUI> {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private Body player;
    private SpriteBatch batch;

    private boolean directionChange;
    private int xFactor;
    private int yFactor;

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
        spawnPlayer();

    }

    @Override
    protected GameUI getScreenUI(Main context) {
        return new GameUI(context);
    }

    private void spawnPlayer() {
        resetBodiesAndFixtures();

        bodyDef.position.set(map.getStartLocation().x, map.getStartLocation().y + 0.5f);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();
    }

    private void resetBodiesAndFixtures() {
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.density = 0;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = null;
    }

    private void spawnCollisionArea() {
        for (final CollisionArea collisionArea : map.getCollisionAreas()) {
            resetBodiesAndFixtures();

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

        if (directionChange) {
            player.applyLinearImpulse(
                    (xFactor * 5 - player.getLinearVelocity().x) * player.getMass(),
                    (yFactor * 5 - player.getLinearVelocity().y) * player.getMass(),
                    player.getWorldCenter().x, player.getWorldCenter().y, true);
        }
        viewport.apply(false);
        gameCam.position.set(player.getPosition().x, player.getPosition().y, 0);
        gameCam.update();
        mapRenderer.setView(gameCam);
        mapRenderer.render();
        box2DDebugRenderer.render(world, viewport.getCamera().combined);

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
    public void keyPressed(final InputManager manager, final GameKeys key) {

        switch (key) {
            case EXIT:
                Gdx.app.exit();
            case LEFT:
                directionChange = true;
                xFactor = -1;
                Gdx.app.debug(ID, "KeyPressd Left " + xFactor);
                break;
            case RIGHT:
                directionChange = true;
                xFactor = 1;
                Gdx.app.debug(ID, "KeyPressd right " + xFactor);
                break;
            case UP:
                directionChange = true;
                yFactor = 1;
                Gdx.app.debug(ID, "KeyPressd Up " + xFactor);
                break;
            case DOWN:
                directionChange = true;
                yFactor = -1;
                Gdx.app.debug(ID, "KeyPressd Down " + xFactor);
                break;
        }
    }

    @Override
    public void keyUp(final InputManager manager, final GameKeys key) {
        switch (key) {
            case LEFT:
                directionChange = true;
                xFactor = manager.isKeyDown(GameKeys.RIGHT) ? 1 : 0;
                Gdx.app.debug(ID, "KeyUPLeft " + xFactor);
                break;
            case RIGHT:
                directionChange = true;
                xFactor = manager.isKeyDown(GameKeys.LEFT) ? -1 : 0;
                Gdx.app.debug(ID, "KeyUpRIght " + xFactor);
                break;
            case UP:
                directionChange = true;
                yFactor = manager.isKeyDown(GameKeys.DOWN) ? -1 : 0;
                break;
            case DOWN:
                directionChange = true;
                yFactor = manager.isKeyDown(GameKeys.UP) ? 1 : 0;
                break;
            default:
                break;
        }
    }
}
