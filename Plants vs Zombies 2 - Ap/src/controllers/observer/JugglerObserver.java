package controllers.observer;

import models.npc.Zombie;
import models.npc.Bullet;

public class JugglerObserver implements BulletObserver {
    @Override
    public boolean onBulletHit(Zombie zombie, Bullet bullet) {
        bullet.deflectBack();
        return true;
    }
}