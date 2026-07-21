package controllers.observer;

import models.npc.Zombie;
import models.grid.GridItem;

public class PassThroughObserver {

    public boolean canPassThrough(Zombie zombie, GridItem item) {
        // Dodo Rider ignores obstacles
        return zombie != null &&
                zombie.getObjClass() != null &&
                zombie.getObjClass().equals("ZombieIceAgeDodoProps");
    }
}