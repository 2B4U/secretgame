package me.lmpedro.main.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static me.lmpedro.main.Main.UNIT_SCALE;

public class Map {
    public static final String ID = Map.class.getName();
    private final TiledMap tiledMap;
    private final Array<CollisionArea> collisionAreas;
    private final Vector2 startLocation;
    private final Vector2 textLocation;

    public Map(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        collisionAreas = new Array<CollisionArea>();
        parseCollisionLayer();
        startLocation = new Vector2();
        textLocation = new Vector2();
        parsePlayerStartPos();
        /*        parseTextLayer();*/
    }

/*    private void parseTextLayer() {
        final MapLayer TextLayer = tiledMap.getLayers().get("textLayer");
        if (TextLayer == null){
            Gdx.app.debug(ID,"There Is Text Layer");
            return;
        }
        final MapObjects objects = TextLayer.getObjects();
        for (final MapObject mapObjects : objects){
            if (mapObjects instanceof RectangleMapObject){
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObjects;
                final Rectangle rectangle = rectangleMapObject.getRectangle();
                final String text =
                textLocation.set(rectangle.x * UNIT_SCALE, rectangle.y * UNIT_SCALE);
            }
            else{
                Gdx.app.debug(ID, "MapObject of Type " + mapObjects + " Is Not Supported For TextLayer!");
                return;
            }
        }
    }*/

    private void parsePlayerStartPos() {
        final MapLayer playerStartPos = tiledMap.getLayers().get("playerStartPos");
        if (playerStartPos == null){
            Gdx.app.debug(ID,"There Is No Player Start Position");
            return;
        }
        final MapObjects objects = playerStartPos.getObjects();
        for (final MapObject mapObjects : objects){
            if (mapObjects instanceof RectangleMapObject){
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObjects;
                final Rectangle rectangle = rectangleMapObject.getRectangle();
                startLocation.set(rectangle.x * UNIT_SCALE, rectangle.y * UNIT_SCALE);
            } else {
            Gdx.app.debug(ID, "MapObject of Type " + mapObjects + " Is Not Supported For playerStartPos Layer!");
        }
        }
    }

    private void parseCollisionLayer() {
        final MapLayer collisionLayer = tiledMap.getLayers().get("collision");
        if (collisionLayer == null){
            Gdx.app.debug(ID,"There Is No Collision Layer?");
            return;
        }

        for (final MapObject mapObj : collisionLayer.getObjects()){
            if (mapObj instanceof RectangleMapObject){
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;
                final Rectangle rectangle = rectangleMapObject.getRectangle();
                final float[] rectVertices = new float[10];

                //left-bottom
                rectVertices[0] = 0;
                rectVertices[1] = 0;
                //left-top
                rectVertices[2] = 0;
                rectVertices[3] = rectangle.height;
                //right-top
                rectVertices[4] = rectangle.width;
                rectVertices[5] = rectangle.height;
                //right-bottom
                rectVertices[6] = rectangle.width;
                rectVertices[7] = 0;
                //left-bottom-finish#
                rectVertices[8] = 0;
                rectVertices[9] = 0;

                collisionAreas.add(new CollisionArea(rectangle.x, rectangle.y, rectVertices));

            } else if (mapObj instanceof PolylineMapObject){
                final PolylineMapObject polyLineMapObject = (PolylineMapObject) mapObj;
                final Polyline polyline = polyLineMapObject.getPolyline();
                collisionAreas.add(new CollisionArea(polyline.getX(), polyline.getY(), polyline.getVertices()));
            } else {
                Gdx.app.debug(ID, "MapObject of Type " + mapObj + " Is Not Supported For Collision Layer!");
            }
        }
    }

    public Array<CollisionArea> getCollisionAreas() {
        return collisionAreas;
    }

    public Vector2 getTextLocation() {
        return textLocation;
    }

    public Vector2 getStartLocation() {
        return startLocation;
    }
}
