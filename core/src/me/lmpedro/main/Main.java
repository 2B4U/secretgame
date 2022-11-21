package me.lmpedro.main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.lmpedro.main.audio.AudioManager;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.B2DComponent;
import me.lmpedro.main.factorys.WorldFactory;
import me.lmpedro.main.input.InputManager;
import me.lmpedro.main.map.MapManager;
import me.lmpedro.main.screens.AbstractScreen;
import me.lmpedro.main.screens.ScreenType;

import java.util.EnumMap;

public class Main extends Game {
    private static final String ID = Main.class.getSimpleName();

    private SpriteBatch spriteBatch;
    private EnumMap<ScreenType, AbstractScreen> screenCache;
    private OrthographicCamera gameCam;
    private OrthographicCamera loadingCam;
    private ExtendViewport screenViewport;

    public static final BodyDef BODY_DEF = new BodyDef();
    public static final FixtureDef FIXTURE_DEF = new FixtureDef();
    public static final float UNIT_SCALE = 1 / 8f;
    public static final short BIT_GROUND = 3;

    private ComponentMapper<B2DComponent> bm = ComponentMapper.getFor(B2DComponent.class);

    private World world;
    private WorldFactory worldFactory;
    private WorldContactListener worldContactListener;
    private Box2DDebugRenderer box2DDebugRenderer;

    private AssetManager assetManager;
    private AudioManager audioManager;

    private Stage stage;
    private Skin skin;

    private InputManager inputManager;

    private MapManager mapManager;
    private ECSEngine ecsEngine;


    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        spriteBatch = new SpriteBatch();

        //Box2D Stuff
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        worldContactListener = new WorldContactListener(this);
        world.setContactListener(worldContactListener);


        box2DDebugRenderer = new Box2DDebugRenderer();

        //Initialize AssetManager
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));

        initializeSkin();
        stage = new Stage(new ScreenViewport(), spriteBatch);
        stage.setDebugAll(true);


        // audio
        audioManager = new AudioManager(this);

        // input manager
        inputManager = new InputManager();
        Gdx.input.setInputProcessor(new InputMultiplexer(inputManager, stage));


        //Set game viewport
        gameCam = new OrthographicCamera();
        screenViewport = new ExtendViewport(15, 15, gameCam);

        //map manager
        mapManager = new MapManager(this);

        // entity system
        ecsEngine = new ECSEngine(this);

        worldFactory = new WorldFactory(this);

        //set first screen
        screenCache = new EnumMap<>(ScreenType.class);
        setScreen(ScreenType.LOADING);

    }

    @Override
    public void render() {
        super.render();

        final float deltaTime = Math.min(0.25f, Gdx.graphics.getDeltaTime());
        ecsEngine.update(deltaTime);

        stage.getViewport().apply();
        stage.act(deltaTime);
        stage.draw();
    }

    public void setScreen(final ScreenType screenType) {
        final Screen screen = screenCache.get(screenType);
        if (screen == null) {
            try {
                Gdx.app.debug(ID, "Creating New Screen " + screenType);
                final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor(screenType.getScreenClass(), Main.class).newInstance(this);
                screenCache.put(screenType, newScreen);
                setScreen(newScreen);
            } catch (ReflectionException e) {
                throw new GdxRuntimeException("Screen" + screenType + " Could Not Be Created", e);
            }
        } else {
            Gdx.app.debug(ID, "Swapping Screen To " + screenType);
            setScreen(screen);
        }
    }

    private void initializeSkin() {
        //setup markup colours
        Colors.put("Red", Color.RED);
        Colors.put("Green", Color.GREEN);
        Colors.put("Blue", Color.BLUE);
        Colors.put("Black", Color.BLACK);
        Colors.put("White", Color.WHITE);

        //genterate ttf bitmaps
        final ObjectMap<String, Object> rescources = new ObjectMap<>();
        final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font.ttf"));
        final FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;
        final int[] sizeToCreate = {16, 20, 26, 32};
        for (int size : sizeToCreate) {
            fontParameter.size = size;
            final BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
            bitmapFont.getData().markupEnabled = true;
            rescources.put("font_" + size, bitmapFont);

        }
        fontGenerator.dispose();

        //load skins
        final SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter("ui/ui.atlas", rescources);
        assetManager.load("ui/hud.json", Skin.class, skinParameter);
        assetManager.finishLoading();
        skin = assetManager.get("ui/hud.json", Skin.class);
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public ECSEngine getEcsEngine() {
        return ecsEngine;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public ExtendViewport getScreenViewport() {
        return screenViewport;
    }

    public World getWorld() {
        return world;
    }

    public Box2DDebugRenderer getBox2DDebugRenderer() {
        return box2DDebugRenderer;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public WorldFactory getWorldFactory() {
        return worldFactory;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public void dispose() {
        super.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        assetManager.dispose();
        spriteBatch.dispose();
        stage.dispose();
    }
}
