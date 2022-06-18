package com.matthew4man.core.objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class SporeOrbEntity extends Entity {

    private int bounceCount;

    public SporeOrbEntity(World world, Body body, Texture texture) {
        super(world, body, texture);
        this.setBounceCount(0);
    }

    public int getBounceCount() {
        return bounceCount;
    }

    public void setBounceCount(int bounceCount) {
        this.bounceCount = bounceCount;
    }

    public void addBounceCount() {
        this.bounceCount++;
    }
}
