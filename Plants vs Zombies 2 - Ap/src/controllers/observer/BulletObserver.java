package controllers.observer;

import models.entity.Zombie;
import models.entity.Bullet;

public interface BulletObserver {
    void onBulletHit(Zombie zombie, Bullet bullet);
}