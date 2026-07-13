package models.npc;

public class Entity {
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
    public Entity(float x, float y, float width, float height, int line, int tileIndex) {

    }
    public Entity(){

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
        this.line = line;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
}
