package models;

import models.utils.*;
import models.factory.builder.*;

import java.util.ArrayList;

public class Pot {

    private boolean unlocked;
    private PlantType seedling;
    private int x;
    private int y;
    private int random;
    private CountDown timer;
    private User user;

    public Pot(User user , int x , int y){
        this.x = x;
        this.y = y;
        this.user = user;
        this.seedling = null;
        this.random = (int)(Math.random() * 10) % 2 ;
        if(y == 1)
            this.unlocked = true;
        else
            this.unlocked = false;
    }

    public void plant(){
        if(this.unlocked == false)
            return;
        if(this.seedling != null){
            // print error
            return;
        }
        if (random == 0){ // marigold
            this.seedling = PlantType.MARIGOLD; // marigold
        }

        else{ // an unlocked plant , randomly
            ArrayList<String> names = user.getUnlockedPlantsNames();
            int count = names.size();
            int plantRand = (int)(Math.random() * 100 ) % count;
            String chosen = names.get(plantRand);
            this.seedling = PlantType.valueOf(chosen.toUpperCase());
        }
        timer = new CountDown(random * 6 + 2);
    }

    public void collect(){
        if(this.unlocked == false)
            return;
        if(this.seedling != null){
            System.out.println("har kasi bedrood chizi ra ke kesht!  nakeshti chizi!!!");
            return;
        }
        if(this.timer.getRemainingHours() > 0){
            System.out.println("bagheban saber bash , waghte besyary dar mazrae pishe gozar joost , nadani to magar?");
        }
        if (this.seedling.equals(PlantType.MARIGOLD)){
            user.addCoins(500);
            System.out.println("500 seke moft!");
        }
        else{
            user.addToList(seedling);
            //addToList must check if it is tekrary
        }
        this.seedling = null;


    }

    public void growNow(){
        if(this.unlocked == false)
            return;
        if(this.seedling != null){
            // print error
            return;
        }

        int remaining = this.timer.getRemainingHours();
        if(remaining == 0)
            return;
        int newDiamond = user.getDiamonds() - remaining;
        if(newDiamond < 0){
            System.out.println("motasefam doost gerami , almaset kafi nabood :( ");
            return;
        }
        user.addDiamonds(-1 * remaining);
        timer.setCountingHours(0);
        System.out.println("6 mahe be donya oomadi?" + remaining + "saat sabr mikardy!");
    }

    public void unlock(boolean isOpen){
        this.unlocked = isOpen;
        // will be called from shop
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSeedling() {
        if (seedling == null)
            return null;
        return seedling.toString();
    }

    public int getRemainingHours() {
        if (timer == null) return 0;

        return timer.getRemainingHours();
    }

    public boolean isUnlocked(){
        return unlocked;
    }
}
