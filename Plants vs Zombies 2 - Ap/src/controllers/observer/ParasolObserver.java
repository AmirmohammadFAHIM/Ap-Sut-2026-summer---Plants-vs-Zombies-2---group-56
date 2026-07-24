package controllers.observer;

import models.entity.Zombie;
import models.entity.Bullet;

public class ParasolObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        if (bullet.getTags() != null && bullet.getTags().contains(Bullet.Tag.MAGICAL)) {
            bullet.setProved(true);
        }
    }
}