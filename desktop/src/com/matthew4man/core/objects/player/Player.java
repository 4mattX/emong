package com.matthew4man.core.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.matthew4man.core.controllers.PlayerController;

import java.util.HashMap;

import static com.matthew4man.core.helper.Constants.PPM;

public class Player extends GameEntity {

    private long deltaTimeJump;
    private long deltaTimeLeft;
    private long deltaTimeRight;
    private boolean jumpReady;
    private boolean facingLeft;
    private boolean isWalking;
    public Fixture playerWallSensorFixture;
    public Fixture checkWallFixture;
    private Fixture playerFloorFixture;
    private Fixture playerCeilingFixture;
    private Texture texture;
    private HashMap<String, Texture> textureMap = new HashMap<>();
    private PlayerController playerController;

    public int walkIteration;
    public Sprite sprite;
    public String textureId;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        loadTextures();
        this.textureId = "idle";
        this.texture = textureMap.get(textureId);
        this.sprite = new Sprite(this.texture);
        this.playerController = new PlayerController(this);

        this.speed = 2.0f;
        this.deltaTimeJump = 0;
        this.jumpReady = false;
        this.facingLeft = false;
        this.walkIteration = 0;

        setPlayerSideColliders();
        for(int i=0; i< body.getFixtureList().size; i++){
//            this.getBody().getFixtureList().get(i).setSensor(true);
            System.out.println(this.getBody().getFixtureList().get(i).getUserData());
        }

    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        checkUserInput();

//        body.getFixtureList().forEach(fixture -> {
//            fixture.setSensor(false);
//        });

        body.getFixtureList().get(0).setSensor(false);

    }

    @Override
    public void render(SpriteBatch batch) {
        this.texture = textureMap.get(textureId);
        this.sprite.setTexture(this.texture);
//        batch.draw(texture, body.getPosition().x * PPM - (texture.getWidth() / 2), body.getPosition().y * PPM - (texture.getHeight() / 2), 0, 0, 32, 32, 32, 32, false, false);

        batch.draw(sprite, body.getPosition().x * PPM - (texture.getWidth() / 2), body.getPosition().y * PPM - (texture.getHeight() / 2));
    }

    private void checkUserInput() {

        this.playerController.getUserInput();
        this.playerController.performPlayerAction();
        this.playerController.checkWallCollisions();
        this.playerController.performSpriteChange();
        this.playerController.clearInputList();

    }

    private void setPlayerSideColliders() {

        float sensorRadius = 0.6f;
        PolygonShape wallSensorShape = new PolygonShape();
        Vector2[] vertices = new Vector2[8];
        vertices[0] = new Vector2(0, 0);
        for(int i = 0; i < 7; i++)
        {
            float angle = (i / 1.0f * 90f - 225f) * MathUtils.degreesToRadians;
            vertices[i + 1] = new Vector2(sensorRadius * MathUtils.cos(angle) * 0.8f, sensorRadius * 1.0f * MathUtils.sin(angle));
        }
        wallSensorShape.set(vertices);
        playerWallSensorFixture = body.createFixture(wallSensorShape, 0);
        playerWallSensorFixture.setSensor(false);

        for(int i = 0; i < 7; i++)
        {
            float angle = (i / 1.0f * 90f - 225f) * MathUtils.degreesToRadians;
            vertices[i + 1] = new Vector2(sensorRadius * MathUtils.cos(angle) * 0.8f, sensorRadius * 1.0f * MathUtils.sin(angle) + 0.1f);
        }
        wallSensorShape.set(vertices);
        checkWallFixture = body.createFixture(wallSensorShape, 0);
        checkWallFixture.setSensor(false);
        checkWallFixture.setUserData("ceiling");

        for(int i = 0; i < 7; i++)
        {
            float angle = (i / 1.0f * 90f - 225f) * MathUtils.degreesToRadians;
            vertices[i + 1] = new Vector2(sensorRadius * MathUtils.cos(angle) * 1.2f, sensorRadius * 1.0f * MathUtils.sin(angle));
        }
        wallSensorShape.set(vertices);
        checkWallFixture = body.createFixture(wallSensorShape, 0);
        checkWallFixture.setSensor(false);
        checkWallFixture.setUserData("checkwall");

    }

    public boolean onGround() {
        if (body.getLinearVelocity().y == 0.0f) {
            playerController.isBouncing = false;
            return true;
        }
        return false;
    }

    public boolean isFalling() {
        if (body.getLinearVelocity().y < 0.0f) {
            return true;
        }
        return false;
    }

    public boolean isJumping() {
        if (body.getLinearVelocity().y > 0.0f) {
            return true;
        }
        return false;
    }

    public PlayerController getPlayerController() {
        return this.playerController;
    }

    private void loadTextures() {
        textureMap.put("bounce0", new Texture("playerTextures/bounce0.png"));
        textureMap.put("fall0", new Texture("playerTextures/fall0.png"));
        textureMap.put("idle", new Texture("playerTextures/idle.png"));
        textureMap.put("jump0", new Texture("playerTextures/jump0.png"));
        textureMap.put("jump1", new Texture("playerTextures/jump1.png"));
        textureMap.put("splat0", new Texture("playerTextures/splat0.png"));
        textureMap.put("walk0", new Texture("playerTextures/walk0.png"));
        textureMap.put("walk1", new Texture("playerTextures/walk1.png"));
        textureMap.put("walk2", new Texture("playerTextures/walk2.png"));
//        textureMap.put("walk3", new Texture("playerTextures/walk3.png"));
//        textureMap.put("walk4", new Texture("playerTextures/walk4.png"));
//        textureMap.put("walk5", new Texture("playerTextures/walk5.png"));
//        textureMap.put("walk6", new Texture("playerTextures/walk6.png"));
//        textureMap.put("walk7", new Texture("playerTextures/walk7.png"));
    }

}
