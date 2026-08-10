package controllers.observer;

import models.entity.Zombie;
import models.entity.Bullet;

public class DragonObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        if (bullet.getTags() != null && bullet.getTags().contains(Bullet.Tag.FIRE)) {
            bullet.setProved(true);
        }
    }
}