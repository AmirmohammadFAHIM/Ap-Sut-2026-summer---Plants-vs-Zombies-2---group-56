package models.entity;

import models.factory.zombies.observers.Armor;

import java.awt.*;
import java.util.ArrayList;

public class Zombie extends  Entity {
    private String type;
    private ArrayList<Armor> armors;
    private Rectangle bounds;
    private int cost;
    private Zombie imp;


    public void drop(){

    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Armor> getArmors() {
        return armors;
    }

    public void setArmors(ArrayList<Armor> armors) {
        this.armors = armors;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Zombie getImp() {
        return imp;
    }

    public void setImp(Zombie imp) {
        this.imp = imp;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }


}
