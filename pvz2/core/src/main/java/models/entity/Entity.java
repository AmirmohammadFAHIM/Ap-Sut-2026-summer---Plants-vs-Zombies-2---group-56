package models.entity;

import models.gamepanes.Tile;

public class Entity {
    protected float stateTime = 0;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float velocityX;
    protected float velocityY;
    protected int line;
    protected int tileIndex;
    protected float hp;
    protected boolean isAlive = true;
    protected boolean ground = true;
    protected boolean hurt = false;
    public Entity(float x, float y, float width, float height, int line, int tileIndex) {

    }
    public Entity(){

    }

    public float getStateTime() {
        return stateTime;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        setY(line * Tile.getHeight());
        this.line = line;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        setX(Tile.getWidth() * tileIndex);
        this.tileIndex = tileIndex;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public boolean isGround() {
        return ground;
    }
    public void setGround(boolean ground) {
        this.ground = ground;
    }

    public boolean isHurt() {
        return hurt;
    }

    public void setHurt(boolean hurt) {
        this.hurt = hurt;
    }
}
