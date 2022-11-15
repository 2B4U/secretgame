package me.lmpedro.main;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import me.lmpedro.main.ecs.components.CollisionComponent;

public class WorldContactListener implements ContactListener {

    private Main main;

    public WorldContactListener(Main main){
        this.main = main;
    }
    @Override
    public void beginContact(Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        Gdx.app.debug("CONTACT",  fixtureA.getBody().getType() + " has hit " + fixtureB.getBody().getType());
        Gdx.app.debug("CONTACT", "BEGIN: " + fixtureB.getBody().getType() + " " + fixtureB.isSensor());

        if(fixtureA.getBody().getUserData() instanceof Entity){
            Entity ent = (Entity) fixtureA.getBody().getUserData();
            entityCollision(ent,fixtureB);
            return;
        }else if(fixtureB.getBody().getUserData() instanceof Entity){
            Entity ent = (Entity) fixtureB.getBody().getUserData();
            entityCollision(ent,fixtureA);
            return;
        }
    }

    private void entityCollision(Entity entity, Fixture fixture) {
        if(fixture.getBody().getUserData() instanceof Entity){
            Entity colEnt = (Entity) fixture.getBody().getUserData();

            CollisionComponent col = entity.getComponent(CollisionComponent.class);
            CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);

            if(col != null){
                col.collisionEntity = colEnt;
            }else if(colb != null){
                colb.collisionEntity = entity;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        Gdx.app.debug("CONTACT", "END ");
        Gdx.app.debug("CONTACT", "END ");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
