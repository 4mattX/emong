package com.matthew4man.core.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.matthew4man.core.objects.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    private Player player;
    private List<String> inputList;

    private boolean isJumpReady;
    private boolean isWalking;
    private boolean isFacingLeft;
    public boolean bounce;
    public boolean ceiling;
    public boolean againstWall;
    public boolean isBouncing;

    private float X_VELOCITY;
    private long deltaTimeJump;

    public PlayerController(Player player) {
        this.player = player;
        this.inputList = new ArrayList<>();
        this.isJumpReady = false;
        this.isWalking = false;
        this.isFacingLeft = false;
        this.againstWall = false;
        this.ceiling = false;

        this.X_VELOCITY = 2.5f;
        this.deltaTimeJump = 0;
    }

    public void getUserInput() {

        if (!this.player.onGround()) {
            this.player.getBody().getFixtureList().get(2).setSensor(false);
            return;
        }

        this.player.getBody().getFixtureList().get(3).setSensor(false);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            inputList.add("left");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            inputList.add("right");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            inputList.add("jump");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isJumpReady = true;
            deltaTimeJump = System.currentTimeMillis();
        }

    }

    public void performPlayerAction() {

        if (this.player.onGround()) {
            this.player.velX = 0;
        }

        if ((inputList.contains("left") || inputList.contains("right")) && !isJumpReady) {
            isWalking = true;
        }

        // Overwrite walk direction based on time applied
        if (inputList.contains("left") && inputList.contains("right")) {

            int leftIndex = inputList.indexOf("left");
            int rightIndex = inputList.indexOf("right");

            if (leftIndex > rightIndex) {
                inputList.remove("right");
            } else {
                inputList.remove("left");
            }
        }

        // Release Jump Logic
        if (isJumpReady && inputList.contains("jump")) {
            float timeDifJump = Math.abs(System.currentTimeMillis() - deltaTimeJump) / 80;

            if (timeDifJump > 10) {
                inputList.remove("jump");
            }
        }

        // Jump Logic
        if (isJumpReady && !inputList.contains("jump")) {
            isJumpReady = false;

            float timeDifJump = Math.abs(System.currentTimeMillis() - deltaTimeJump) / 80;

            float forceY = (this.player.getBody().getMass() * timeDifJump) + 2;

            if (inputList.contains("left") || inputList.contains("right")) {
                this.player.velX = (inputList.contains("right") ? X_VELOCITY : -X_VELOCITY);
            }


            this.player.getBody().getFixtureList().get(3).setSensor(true);

            this.player.getBody().applyLinearImpulse(new Vector2(0, forceY * 1.9f), this.player.getBody().getPosition(), true);
        }



        // Walking logic
        if (inputList.contains("right")) {

            // Flip sprite if necessary
            if (isFacingLeft) {
                isFacingLeft = false;
                this.player.sprite.flip(true, false);
            }

            if (!isJumpReady) {
                this.player.velX = X_VELOCITY;
            }

        }

        if (inputList.contains("left")) {

            // Flip sprite if necessary
            if (!isFacingLeft) {
                isFacingLeft = true;
                this.player.sprite.flip(true, false);
            }

            if (!isJumpReady) {
                this.player.velX = -X_VELOCITY;
            }
        }



        if (!inputList.contains("left") && !inputList.contains("right")) {
            isWalking = false;
        }

        this.player.getBody().setLinearVelocity(this.player.velX * this.player.speed, this.player.getBody().getLinearVelocity().y);
    }

    public void performSpriteChange() {

        if (isWalking) {
            if (this.player.walkIteration > 10) {
                this.player.walkIteration = 0;
            } else {
                this.player.walkIteration++;
            }

            this.player.textureId = "walk" + this.player.walkIteration / 5;
        } else if (this.player.isFalling()) {
            this.player.textureId = "fall0";
        } else if (this.player.isJumping()) {
            this.player.textureId = "jump1";
        }

        else {
            this.player.textureId = "idle";
        }

        if (inputList.contains("jump")) {
            this.player.textureId = "jump0";
        }

    }

    public void checkWallCollisions() {
        // If not in air, then remove main collider
        if (!this.player.onGround()) {
            this.player.getBody().getFixtureList().get(0).setUserData("wall");
            this.player.getBody().getFixtureList().get(0).setSensor(true);
        } else {
            this.player.getBody().getFixtureList().get(0).setUserData("ground");
            this.player.getBody().getFixtureList().get(0).setSensor(false);
        }

        if (bounce) {

            if (ceiling) {
                ceiling = false;
                bounce = false;
                isBouncing = true;
                return;
            }

            bounce = false;
            isBouncing = true;
            this.player.velX *= -1.1;
        }

    }

    public void clearInputList() {
        this.inputList.clear();
    }

    public Player getPlayer() {
        return this.player;
    }

}
