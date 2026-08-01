package models.factory.plantSkills.skillobserver;

import models.entity.Plant;
import models.entity.Zombie;
import models.games.BaseGame;

public class AoEObserver implements Observer {
    @Override
    public boolean observe(Plant self , BaseGame game) {
        for (Zombie zombie: game.getZombies()) {
            int dx = zombie.getTileIndex() - self.getTileIndex();
            int dy = zombie.getTileIndex() - self.getTileIndex();
            if(Math.abs(dx) <= 4 &&  Math.abs(dy) <= 4) {
                return  true;
            }
        }
        return false;
    }
}
