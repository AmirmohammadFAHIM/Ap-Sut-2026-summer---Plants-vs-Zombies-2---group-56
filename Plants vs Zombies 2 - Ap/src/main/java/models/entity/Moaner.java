package models.entity;

import models.Constants;
import models.games.BaseGame;

public class Moaner extends Entity {


    private boolean on = false;
    public String run(float delta , BaseGame game) {


      if(on){
          x += Constants.MoanerSpeed * delta;
      }
        for (Zombie z : game.getZombies()) {
            if(Constants.overlap(z , this)){
                if(!on){
                    on = true;
                    return "Lawn Mawner turned on at line " + line;
                }
                z.setHurt(true);
                z.setAlive(false);
                z.setHp(0);
            }
        }
        return null;
    }
    public Moaner(int line){
        this.line = line;
        this.y = line * height;
    }

}
