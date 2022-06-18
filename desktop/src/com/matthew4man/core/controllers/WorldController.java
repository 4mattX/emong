package com.matthew4man.core.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.matthew4man.core.GameScreen;
import com.matthew4man.core.helper.EntityContactListener;
import com.matthew4man.core.helper.EntityHelperService;
import com.matthew4man.core.objects.player.Entity;
import com.matthew4man.core.objects.player.Player;
import com.matthew4man.core.objects.player.SporeOrbEntity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.matthew4man.core.helper.Constants.PPM;

public class WorldController {

    private Mouse mouse;
    private GameScreen gameScreen;
    private Player player;
    private EntityHelperService entityHelperService;
    private static HashMap<String, Entity> entityMap = new HashMap<>();

    public WorldController(GameScreen gameScreen) {
        this.mouse = new Mouse();
        this.gameScreen = gameScreen;
        this.entityHelperService = new EntityHelperService(this.gameScreen.getWorld());
    }

    public static HashMap<String, Entity> getEntityMap() {
        return entityMap;
    }

    public static void setEntityMap(HashMap<String, Entity> entityMap) {
        WorldController.entityMap = entityMap;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void checkEntityCollision() {

        for (Entity value : getEntityMap().values()) {

            if (value.getBounceCount() > 0) {
                value.getBody().setType(BodyDef.BodyType.StaticBody);
                value.getBody().setLinearVelocity(0,0);
                value.getBody().getFixtureList().get(0).setSensor(false);
            }

        }

    }

    public void checkInput() {
        if (Gdx.input.isTouched()) {
            mouse.setLeftClicked(true);
            mouse.setX(Gdx.input.getX());
            mouse.setY(Gdx.input.getY());
        } else {
            if (mouse.isLeftClicked()) {
                mouse.setLeftClicked(false);
                mouse.setLeftMouseReleased(true);
            }
        }
    }

    public void performAction() {
        if (mouse.isLeftMouseReleased()) {

            float clickX = mouse.getX() / PPM;
            float clickY = ((Gdx.graphics.getHeight() / PPM) - (mouse.getY() / PPM)) - (Gdx.graphics.getHeight() / PPM / 2f);

            float playerX = (player.x / PPM);
            float playerY = (player.y / PPM);

            List<Object> idEntityList = this.entityHelperService.createEntity(playerX, playerY, clickX, clickY);
            String uuid = (String) idEntityList.get(0);
            Entity entity = (Entity) idEntityList.get(1);

            getEntityMap().put(uuid, entity);

            mouse.setLeftMouseReleased(false);
        }
    }

}
