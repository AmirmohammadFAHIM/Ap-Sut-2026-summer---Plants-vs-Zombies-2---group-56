package controllers.observer;

import models.npc.Zombie;
import models.npc.Bullet;

    public interface BulletObserver {
        boolean onBulletHit(Zombie zombie, Bullet bullet);
    }
