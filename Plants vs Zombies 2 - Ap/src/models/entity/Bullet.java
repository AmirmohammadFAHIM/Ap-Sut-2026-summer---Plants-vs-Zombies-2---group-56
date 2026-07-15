package models.entity;

import models.Constants;

import java.util.ArrayList;

public class Bullet {
    private BulletType type;
    private float velocityX;
    private float velocityY;
    private float destinationX;
    private float destinationY;
    private int damage;
    private float x;
    private float y;

    /// ------------BOOLEANS------------
    public enum Tag{MAGICAL,ICE,FIRE,POISON,HOMING}
    ArrayList<Tag> tags;
    private boolean proved = false;
    /// for homing plants of course!
    private Zombie toLockIn;

    public Bullet(float x, float y , int velocityX ,  int velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Bullet(float x, float y , float velocityX , BulletType type ,  int damage) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.type = type;
        this.damage = damage;
    }

    public Bullet(float x, float y , BulletType bulletType) {
        this.x = x;
        this.y = y;
        this.type = bulletType;
        this.velocityX = Constants.BulletVelocityX;
        damage = 20;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Bullet(){

    }

    public void dealDamage() {}

    public void run(){}

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

    public float getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(float destinationX) {
        this.destinationX = destinationX;
    }

    public float getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(float destinationY) {
        this.destinationY = destinationY;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    public boolean isProved() {
        return proved;
    }

    public void setProved(boolean proved) {
        this.proved = proved;
    }

    public Zombie getToLockIn() {
        return toLockIn;
    }

    public void setToLockIn(Zombie toLockIn) {
        this.toLockIn = toLockIn;
    }

    public BulletType getType() {
        return type;
    }

    public void setType(BulletType type) {
        this.type = type;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        Bullet clone = new Bullet(this.x , this.y , this.type);
        clone.setVelocityX(this.velocityX);
        clone.setVelocityY(this.velocityY);
        clone.setDestinationX(this.destinationX);
        clone.setDestinationY(this.destinationY);
        clone.setDamage(this.damage);
        return clone;
    }
}
