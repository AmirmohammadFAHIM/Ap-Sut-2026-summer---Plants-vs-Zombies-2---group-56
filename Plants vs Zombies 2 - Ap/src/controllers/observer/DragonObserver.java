package controllers.observer;

import models.npc.Zombie;
import models.npc.Bullet;

public class DragonObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        if (bullet.getTags() != null && bullet.getTags().contains(Bullet.Tag.FIRE)) {
            bullet.setProved(true);
        }
    }
}