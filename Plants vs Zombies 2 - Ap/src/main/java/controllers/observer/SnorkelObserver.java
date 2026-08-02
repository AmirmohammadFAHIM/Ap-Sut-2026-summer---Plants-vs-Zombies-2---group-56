package controllers.observer;
import models.entity.Zombie;
import models.entity.Bullet;
import models.gamePanes.Tile;
import models.games.BaseGame;

public class SnorkelObserver implements BulletObserver {

    @Override
    public void onBulletHit(Zombie zombie, Bullet bullet) {

        boolean underWater = zombie.isInWater();
        if(!underWater)
            return;

        if (!bullet.isGrounded()) {
            bullet.setProved(true);
            bullet.setActive(false);
            System.out.println("just lobber can hit snorkel under water");
        }
    }
}
