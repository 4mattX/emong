package com.matthew4man.core.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.HashMap;

import static com.matthew4man.core.helper.Constants.PPM;

public class Player extends GameEntity{

    private long deltaTimeJump;
    private long deltaTimeLeft;
    private long deltaTimeRight;
    private boolean jumpReady;
    private boolean facingLeft;
    private boolean isWalking;
    private int walkIteration;

    private Texture texture;
    private Sprite sprite;
    private HashMap<String, Texture> textureMap = new HashMap<>();
    private String textureId;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        loadTextures();
        this.textureId = "idle";
        this.texture = textureMap.get(textureId);
        this.sprite = new Sprite(this.texture);

        this.speed = 2.85f;
        this.deltaTimeJump = 0;
        this.jumpReady = false;
        this.facingLeft = false;
        this.walkIteration = 0;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        this.texture = textureMap.get(textureId);
        this.sprite.setTexture(this.texture);
//        batch.draw(texture, body.getPosition().x * PPM - (texture.getWidth() / 2), body.getPosition().y * PPM - (texture.getHeight() / 2), 0, 0, 32, 32, 32, 32, false, false);

        batch.draw(sprite, body.getPosition().x * PPM - (texture.getWidth() / 2), body.getPosition().y * PPM - (texture.getHeight() / 2));
    }

    private void checkUserInput() {
        if (onGround()) {
            velX = 0;
        }

        if (isWalking) {
            if (walkIteration > 10) {
                walkIteration = 0;
            } else {
                walkIteration++;
            }

            this.textureId = "walk" + walkIteration / 5;
        } else if (isFalling()) {
            this.textureId = "fall0";
        } else if (isJumping()) {
            this.textureId = "jump1";
        }

        else {
            this.textureId = "idle";
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {

            isWalking = true;

            // Flip textures
            if (facingLeft) {
                facingLeft = false;
                this.sprite.flip(true, false);
            }

            if (!jumpReady) {
                velX = 1;
            } else {
                deltaTimeRight = System.currentTimeMillis();
            }

        }

        else if (Gdx.input.isKeyPressed(Input.Keys.A )) {

            isWalking = true;

            // Flip textures
            if (!facingLeft) {
                facingLeft = true;
                this.sprite.flip(true, false);
            }

            if (!jumpReady) {
                velX = -1;
            } else {
                deltaTimeLeft = System.currentTimeMillis();
            }
        } else {
            isWalking = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!isWalking) {
                deltaTimeJump = System.currentTimeMillis();
                jumpReady = true;
            }
        }

//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            float force = body.getMass() * 18;
//            body.setLinearVelocity(body.getLinearVelocity().x, 0);
//            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
//        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (!isWalking) {
                velX = 0;
                this.textureId = "jump0";
            }

        } else {

            if (jumpReady) {
                float timeDifJump = Math.abs(System.currentTimeMillis() - deltaTimeJump) / 80;

                long timeDifRight = Math.abs(System.currentTimeMillis() - deltaTimeRight) / 80;
                long timeDifLeft = Math.abs(System.currentTimeMillis() - deltaTimeLeft) / 80;

                float forceX = (timeDifLeft - timeDifRight);
                float forceY = body.getMass() * timeDifJump + 7;

                body.applyLinearImpulse(new Vector2(0, forceY), body.getPosition(), true);

                if (forceX > 0) {
                    velX = 3;
                } else if (forceX == 0) {
                   velX = 0;
                } else {
                    velX = -3;
                }


                deltaTimeLeft = System.currentTimeMillis();
                deltaTimeRight = System.currentTimeMillis();

                jumpReady = false;

            }

//            this.textureId = "idle";
            body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 20 ? body.getLinearVelocity().y : 20);
        }


    }

    private boolean onGround() {
        if (body.getLinearVelocity().y == 0.0f) {
            return true;
        }
        return false;
    }

    private boolean isFalling() {
        if (body.getLinearVelocity().y < 0.0f) {
            return true;
        }
        return false;
    }

    private boolean isJumping() {
        if (body.getLinearVelocity().y > 0.0f) {
            return true;
        }
        return false;
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
    }

}
