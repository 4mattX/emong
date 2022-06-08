package com.matthew4man.core.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.matthew4man.core.objects.player.Player;

public class PlayerContactListener implements ContactListener {

    private Player player;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "wall" || fixtureB.getUserData() == "wall") {
            this.player.getPlayerController().bounce = true;
        }

        if (fixtureA.getUserData() == "ceiling" || fixtureB.getUserData() == "ceiling") {
            this.player.getPlayerController().bounce = true;
            this.player.getPlayerController().ceiling = true;
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
}
