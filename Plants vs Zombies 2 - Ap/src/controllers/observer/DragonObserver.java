package controllers.observer;

import models.npc.Zombie;
import models.npc.Bullet;

public class DragonObserver implements BulletObserver {
    @Override
    public boolean onBulletHit(Zombie zombie, Bullet bullet) {
        if (bullet.isFire()) {
            return true;
        }
        return false;
    }
}