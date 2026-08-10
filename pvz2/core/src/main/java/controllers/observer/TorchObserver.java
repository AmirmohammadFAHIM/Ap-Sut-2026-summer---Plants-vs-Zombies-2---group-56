package controllers.observer;

import models.entity.*;

public class TorchObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        if (!zombie.getType().toLowerCase().contains("explorer")) return;

        if (bullet.getTags() != null) {
            if (bullet.getTags().contains(Bullet.Tag.ICE)) {
                zombie.setTorchOn(false);
            } else if (bullet.getTags().contains(Bullet.Tag.FIRE)) {
                zombie.setTorchOn(true);
            }
        }
    }
}