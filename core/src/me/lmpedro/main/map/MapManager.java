package me.lmpedro.main.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import me.lmpedro.main.Main;

import java.util.EnumMap;

import static me.lmpedro.main.Main.BIT_GROUND;

public class MapManager {
    public static final String ID = MapManager.class.getName();

    private final World world;
    private final Array<Body> bodies;

    private final AssetManager assetManager;

    private MapType currentMapType;
    private Map currentMap;
    private final EnumMap<MapType, Map> mapCache;
    private final Array<MapListener> listeners;

    public MapManager(final Main context){
        currentMapType = null;
        currentMap = null;
        world = context.getWorld();
        assetManager = context.getAssetManager();
        bodies = new Array<>();
        mapCache = new EnumMap<MapType, Map>(MapType.class);
        listeners = new Array<MapListener>();
    }

    public void addMapListener(final MapListener listener){
        listeners.add(listener);
    }

    public void setMap(final MapType type){
        if (currentMapType == type){
            return;
        }
        if (currentMap != null){
            world.getBodies(bodies);
            destroyCollisionAreas();
        }

        Gdx.app.debug(ID,"Changing to map " + type);
        currentMap = mapCache.get(type);
        if (currentMap == null){
            Gdx.app.debug(ID,"Creating new map of type " + type);
            final TiledMap tiledMap = assetManager.get(type.getFilePath(),TiledMap.class);
            currentMap = new Map(tiledMap);
            mapCache.put(type, currentMap);
        }

        spawnCollisionArea();

        for (final MapListener listener : listeners){
            listener.mapChange(currentMap);
        }
    }

    private void destroyCollisionAreas() {
        for (final Body body : bodies){
            if ("Ground".equals(body.getUserData())){
                world.destroyBody(body);
            }
        }
    }

    private void spawnCollisionArea() {
        final BodyDef bodyDef = new BodyDef();
        final FixtureDef fixtureDef = new FixtureDef();
        for (final CollisionArea collisionArea : currentMap.getCollisionAreas()) {

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

    public Map getCurrentMap() {
        return currentMap;
    }
}
