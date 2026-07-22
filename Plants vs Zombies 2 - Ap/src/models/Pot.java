package models;

import models.npc.Plant;
import models.utils.*;

public class Pot {

    private boolean unlocked;
    private Plant seedling; // using seed as a new object seemed a better way , but using unlocked plants was too hard that way
    // so now i use plant as a class for seedlings
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
            this.seedling = new Plant(); // marigold
        }

        else{ // an unlocked plant

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
}
