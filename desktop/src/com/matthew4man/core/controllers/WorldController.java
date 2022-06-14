package com.matthew4man.core.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.matthew4man.core.helper.EntityHelperService;

public class WorldController {

    private Mouse mouse;
    private World world;
    private EntityHelperService entityHelperService;

    public WorldController(World world) {
        this.mouse = new Mouse();
        this.world = world;
        this.entityHelperService = new EntityHelperService(world);
    }

    public void checkInput() {
        if (Gdx.input.isTouched()) {
            mouse.setLeftClicked(true);
            mouse.setX(Gdx.input.getX());
            mouse.setY(Gdx.input.getY());
        } else {
            if (!mouse.wasLeftClicked()) {
                mouse.setWasLeftClicked(true);
            } else {
                mouse.setWasLeftClicked(false);
            }
        }
    }

    public void performAction() {
        if (mouse.wasLeftClicked()) {
            this.entityHelperService.createEntity(mouse.getX(), mouse.getY());
        }
    }

}
