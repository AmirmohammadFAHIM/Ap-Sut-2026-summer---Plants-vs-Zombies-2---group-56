package models.entity.ability;

import models.entity.*;
import models.entity.EffectType;
import models.games.BaseGame;

public class BulletAbility implements Ability {

    private final BulletType bulletType;
    private final float cooldown;
    private final float range;
    private float timer;

    public BulletAbility(BulletType bulletType, float cooldown, float range) {
        this.bulletType = bulletType;
        this.cooldown = cooldown;
        this.range = range;
        this.timer = 0;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, BaseGame game) {
        timer -= deltaTime;
        if (timer > 0) return;

        boolean isHypnotized = zombie.hasEffect(EffectType.HYPNOTIZED);

        if (isHypnotized) {
            Zombie target = game.findNearestZombie(zombie, range);
            if (target == null) return;
            Bullet bullet = new Bullet(zombie.getX(), zombie.getY(), bulletType);
            bullet.setToLockIn(target);
            game.getBullets().add(bullet);
        } else {
            Plant target = game.findTargetPlant(zombie, range);
            if (target == null) return;
            Bullet bullet = new Bullet(zombie.getX(), zombie.getY(), bulletType);
            bullet.setToLockIn(null);
            game.getBullets().add(bullet);
        }

        timer = cooldown;
    }
}