package com.matthew4man.core.helper;

import com.badlogic.gdx.physics.box2d.World;

public class EntityHelperService {

    private World world;

    public EntityHelperService(World world) {
        this.world = world;
    }

    public void createEntity(int x, int y) {
        System.out.println("X: " + x + "  Y: " + y);
    }

}
