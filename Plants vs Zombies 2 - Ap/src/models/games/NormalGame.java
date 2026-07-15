package models.games;

import models.entity.Zombie;

public class NormalGame extends BaseGame{


    @Override
    public boolean check_endGame() {
        for (Zombie z : zombies){
            if(z.getX() <= 0) return true;
        }
        return false;
    }
}
