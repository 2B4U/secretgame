package me.lmpedro.main.ai;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import me.lmpedro.main.ecs.components.SteeringComponent;

public class SteeringPresets {
    public static Wander<Vector2> getWander(SteeringComponent scom){

        return new Wander<>(scom)
                .setFaceEnabled(false)// let wander behaviour manage facing
                .setWanderOffset(0) // distance away from entity to set target
                .setWanderOrientation(0) // the initial orientation
                .setWanderRadius(0)// size of target
                .setWanderRate(MathUtils.PI2);
    }

    public static Seek<Vector2> getSeek(SteeringComponent seeker, SteeringComponent target){
        Seek<Vector2> seek = new Seek<Vector2>(seeker,target);
        return seek;
    }

    public static Flee<Vector2> getFlee(SteeringComponent runner, SteeringComponent fleeingFrom){
        Flee<Vector2> seek = new Flee<Vector2>(runner,fleeingFrom);
        return seek;
    }

    public static Arrive<Vector2> getArrive(SteeringComponent runner, SteeringComponent target){

        return new Arrive<Vector2>(runner, target)
                .setTimeToTarget(0.1f) // default 0.1f
                .setArrivalTolerance(5f) //
                .setDecelerationRadius(10f);
    }
}
