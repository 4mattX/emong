package com.matthew4man.core.controllers;

public class Mouse {

    private int x;
    private int y;
    private boolean isLeftClicked;
    private boolean isRightClicked;
    private boolean wasLeftClicked;
    private boolean leftMouseReleased;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeftClicked() {
        return isLeftClicked;
    }

    public void setLeftClicked(boolean leftClicked) {
        isLeftClicked = leftClicked;
    }

    public boolean isRightClicked() {
        return isRightClicked;
    }

    public void setRightClicked(boolean rightClicked) {
        isRightClicked = rightClicked;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean wasLeftClicked() {
        return wasLeftClicked;
    }

    public void setWasLeftClicked(boolean wasLeftClicked) {
        this.wasLeftClicked = wasLeftClicked;
    }

    public boolean isLeftMouseReleased() {
        return leftMouseReleased;
    }

    public void setLeftMouseReleased(boolean leftMouseReleased) {
        this.leftMouseReleased = leftMouseReleased;
    }
}
