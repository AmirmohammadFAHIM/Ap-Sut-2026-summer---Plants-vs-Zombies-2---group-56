package controllers.observer;

import models.npc.Bullet;
import models.npc.Zombie;
import models.grid.GridItem;

public class PassThroughObserver implements BulletObserver{

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet){
        // just for asani structure
        return;
    }

    public boolean canPassThrough(Zombie zombie, GridItem item) {
        // Dodo Rider ignores obstacles
        return zombie != null &&
                zombie.getObjClass() != null &&
                zombie.getObjClass().equals("ZombieIceAgeDodoProps");
    }
}