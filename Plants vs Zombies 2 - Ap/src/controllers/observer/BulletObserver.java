package controllers.observer;

import models.npc.Zombie;
import models.npc.Bullet;

public interface BulletObserver {
    void onBulletHit(Zombie zombie, Bullet bullet);
}