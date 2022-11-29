package me.lmpedro.main;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Utils {

    public static float vectorToAngle (Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    public static Vector2 angleToVector (Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    public static Vector2 aimTo(Vector2 shooter, Vector2 target){
        Vector2 aim = new Vector2();
        float velx = target.x - shooter.x; // get distance from shooter to target on x plain
        float vely = target.y - shooter.y; // get distance from shooter to target on y plain
        float length = (float) Math.sqrt(velx * velx + vely * vely); // get distance to target direct
        if (length != 0) {
            aim.x = velx / length;  // get required x velocity to aim at target
            aim.y = vely / length;  // get required y velocity to aim at target
        }
        return aim;
    }


    /** Takes Vector 3 as argument here for mouse location(unproject etc)
     * @param shooter Vector 2 for shooter position
     * @param target Vector 3 for target location
     * @return
     */
    public static Vector2 aimTo(Vector2 shooter, Vector3 target){
        return aimTo(shooter, new Vector2(target.x,target.y));
    }
}
