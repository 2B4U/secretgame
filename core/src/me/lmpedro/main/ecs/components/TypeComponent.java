package me.lmpedro.main.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int SCENERY = 3;
    public static final int OTHER = 4;
    public static final int BULLET = 5;
    public static final int HEALTH = 6;

    public int type = OTHER;

    @Override
    public void reset() {
        type = OTHER;
    }

    public int getType() {
        return type;
    }
}
