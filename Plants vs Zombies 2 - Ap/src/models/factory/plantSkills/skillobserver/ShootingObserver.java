package models.factory.plantSkills.skillobserver;

import models.entity.Plant;
import models.entity.Zombie;
import models.games.BaseGame;

public class ShootingObserver implements  Observer {
    @Override
    public boolean observe(Plant self, BaseGame game) {
        for (Zombie zombie: game.getZombies()) {
            if(zombie.getLine() == self.getLine()) {
                return true;
            }
        }
        return false;
    }
}
