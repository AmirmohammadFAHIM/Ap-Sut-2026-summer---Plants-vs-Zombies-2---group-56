package controllers.observer;

import models.npc.Zombie;
import models.npc.Bullet;

public class JugglerObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        bullet.setVelocityX(-bullet.getVelocityX());
        bullet.setVelocityY(-bullet.getVelocityY());
    }
}