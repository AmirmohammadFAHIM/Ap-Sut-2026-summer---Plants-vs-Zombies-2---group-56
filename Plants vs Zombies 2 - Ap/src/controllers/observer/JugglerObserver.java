package controllers.observer;

import models.entity.Zombie;
import models.entity.Bullet;

public class JugglerObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        bullet.setVelocityX(-bullet.getVelocityX());
        bullet.setVelocityY(-bullet.getVelocityY());
    }
}