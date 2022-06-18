package com.matthew4man.core.objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Entity  {

    private World world;
    private Body body;
    private Texture texture;
    private int bounceCount;

    public Entity(World world, Body body, Texture texture) {
        this.setWorld(world);
        this.setBody(body);
        this.setTexture(texture);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
