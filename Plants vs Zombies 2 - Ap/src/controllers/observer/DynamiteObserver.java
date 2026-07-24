package controllers.observer;

import models.entity.*;

public class DynamiteObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        if (!zombie.getType().toLowerCase().contains("prospector")) return;

        if (bullet.getTags() != null) {
            if (bullet.getTags().contains(Bullet.Tag.ICE)) {
                zombie.setDynamiteFrozen(true);
            } else if (bullet.getTags().contains(Bullet.Tag.FIRE)) {
                zombie.setDynamiteFrozen(false);
            }
        }
    }
}