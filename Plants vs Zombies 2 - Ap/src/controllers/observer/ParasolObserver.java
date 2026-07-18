package controllers.observer;

import models.npc.Zombie;
import models.npc.Bullet;

public class ParasolObserver implements BulletObserver {
    @Override
    public boolean onBulletHit(Zombie zombie, Bullet bullet) {
        if (bullet.isLobber()) {
            bullet.deflect();
            return true;
        }
        return false;
    }

}