package models;

import models.utils.*;
import models.factory.builder.*;

public class Pot {

    private boolean unlocked;
    private PlantType seedling;
    private int x;
    private int y;
    int random;
    CountDown timer;

    public Pot(int x , int y){
        this.x = x;
        this.y = y;
        this.unlocked = false;
        this.seedling = null;
        this.random = (int)(Math.random() * 10) % 2 ;
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

            // get unlockeds count
            // find a random number % that number
            // this.seedling = that plant
        }
        timer = new CountDown(random * 6 + 2);
    }

    public void collect(){
        if(this.unlocked == false)
            return;
        if(this.seedling != null){
            // print error
            return;
        }
        if (this.seedling.equals(PlantType.MARIGOLD)){
            // add 500 golds tp user
        }
        else{
            // user.boostList.add(seedling)
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
        // if ! getDiamonds - reaminig < 0
        // setDiamonds(getDiamonds - reaminig)
        //
        timer.setCountingHours(0);
    }

    public void setLock(boolean lock){
        this.unlocked = lock;
        // will be called from shop
    }
}
