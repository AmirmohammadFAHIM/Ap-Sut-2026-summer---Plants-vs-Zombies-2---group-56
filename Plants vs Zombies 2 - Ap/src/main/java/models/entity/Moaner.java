package models.entity;

import models.Constants;
import models.games.BaseGame;

public class Moaner extends Entity {


    private boolean on = false;
    public void run(float delta , BaseGame game) {
        if(!on) return;
        x += Constants.MoanerSpeed * delta;
        for (Zombie z : game.getZombies()) {
            if(Constants.overlap(z , this)){
                z.setHurt(true);
                z.setAlive(false);
                z.setHp(0);
            }
        }
    }
    public Moaner(int line){
        this.line = line;
        this.y = line * height;
    }

}
