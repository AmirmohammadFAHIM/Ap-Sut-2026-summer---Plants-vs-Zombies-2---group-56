package models.npc;

import models.factory.zombies.observers.Armor;

import java.util.ArrayList;

public class Zombie {
    private String type;
    private ArrayList<Armor> armors;
    private float x;
    private float y;
    private int line;
    private float velocityX;
    private float velocityY;
    private int cost;
    private int hp;
    private Zombie imp;

    public void drop(){}


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
