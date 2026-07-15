package models.npc;

import models.factory.zombies.observers.Armor;

import java.util.ArrayList;

public interface Zombie extends Entity {
    private String type;
    private ArrayList<Armor> armors;
    private int cost;
    private int hp;
    private int damage;
    private boolean hypnotized;
    private boolean freezed;
    public static int VELOCITY = -50;

    public void attack(){
    }

    public boolean reachedPlant(){
        /*
        if(there is a plant in next home)
         */
        return true;
    }
    public Plant findNextPlant(){
        if(!reachedPlant())
            return null;
        Plant plant = null; //the one who lives at next house
        return plant;
    }

    public void live(){
        return;
    }

    public int movingDirection(){
        return 1;
    }

    public void setVelocity(){

    }
    public void setFatality(Zombie zombie){

    }

}
