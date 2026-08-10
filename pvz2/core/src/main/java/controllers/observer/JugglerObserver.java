package controllers.observer;

import models.entity.Zombie;
import models.entity.Bullet;

public class JugglerObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {
        zombie.setSpeed(zombie.getSpeed() * 5);
        System.out.println("5 times faster now");

        bullet.setVelocityX(-bullet.getVelocityX());
        bullet.setVelocityY(-bullet.getVelocityY());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        zombie.setSpeed(zombie.getSpeed() / 5);
        System.out.println("return to first speed");
    }
}