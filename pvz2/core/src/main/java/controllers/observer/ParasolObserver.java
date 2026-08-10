package controllers.observer;

import models.entity.Zombie;
import models.entity.Bullet;

public class ParasolObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        if (bullet.isGrounded()) {
            bullet.setProved(true);
            bullet.setActive(false);
            System.out.println("no lobber can hit parasol :)");
        }
    }
}