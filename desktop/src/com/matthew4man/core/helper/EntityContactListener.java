package com.matthew4man.core.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.matthew4man.core.controllers.WorldController;
import com.matthew4man.core.objects.player.Entity;
import com.matthew4man.core.objects.player.Player;
import com.matthew4man.core.objects.player.SporeOrbEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.matthew4man.core.helper.Constants.MAX_ENTITIES;

public class EntityContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    private Player player;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == null && fixtureB.getUserData() == null) {
            return;
        }

        if (fixtureA.getUserData() == "wall" || fixtureB.getUserData() == "wall") {
            this.player.getPlayerController().bounce = true;
        }

        if (fixtureA.getUserData() == "ceiling" || fixtureB.getUserData() == "ceiling") {
            this.player.getPlayerController().bounce = true;
            this.player.getPlayerController().ceiling = true;
        }

        // Spore-orb Logic
        int sporeOrbIndex = 0;
        try {
            if (fixtureA.getUserData().toString().contains("spore-orb")) {
                sporeOrbIndex = 1;
            }
        } catch (Exception e) {
        }

        try {
            if (fixtureB.getUserData().toString().contains("spore-orb")) {
                sporeOrbIndex = 2;
            }
        } catch (Exception e) {
        }

        if (sporeOrbIndex > 0) {
            if (fixtureA.getUserData() != "ceiling" && fixtureB.getUserData() != "ceiling" &&
                fixtureA.getUserData() != "wall" && fixtureB.getUserData() != "wall" &&
                fixtureA.getUserData() != "ground" && fixtureB.getUserData() != "ground" &&
                fixtureA.getUserData() != "player" && fixtureB.getUserData() != "player" &&
                fixtureA.getUserData() != "player-wall-sensor" && fixtureB.getUserData() != "player-wall-sensor" &&
                fixtureA.getUserData() != "checkwall" && fixtureB.getUserData() != "checkwall") {

                String entityId = "";

                if (sporeOrbIndex == 1) {
                    entityId = fixtureA.getUserData().toString().split("spore-orb:")[1];
                } else if (sporeOrbIndex == 2) {
                    entityId = fixtureB.getUserData().toString().split("spore-orb:")[1];
                }

                WorldController.getEntityMap().get(entityId).addBounceCount();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

//    public void addEntity(Entity entity) {
//
//        if (entityList.size() > MAX_ENTITIES) {
//            entityList.remove(0);
//        }
//
//        this.entityList.add(entity);
//    }
}
