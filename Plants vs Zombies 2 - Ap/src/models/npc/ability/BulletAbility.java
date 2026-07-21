package models.npc.ability;

import models.npc.*;
import controllers.GameController;

public class BulletAbility extends Ability {

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
    public void execute(Zombie zombie, float deltaTime, GameController controller) {
        timer -= deltaTime;
        if (timer > 0) return;

        // هیپنوتیزم: به جای گیاه، زامبی را هدف بگیر
        boolean isHypnotized = zombie.hasEffect(EffectType.HYPNOTIZED);

        if (isHypnotized) {
            // هدف: نزدیک‌ترین زامبی (غیر از خودش)
            Zombie target = controller.findNearestZombie(zombie, range);
            if (target == null) return;

            Bullet bullet = new Bullet(
                    zombie.getX(),
                    zombie.getY(),
                    zombie.getRow(),
                    bulletType
            );
            bullet.setTargetZombie(target);
            controller.addBullet(bullet);

        } else {
            // هدف: نزدیک‌ترین گیاه
            Plant target = controller.findTargetPlant(zombie, range);
            if (target == null) return;

            Bullet bullet = new Bullet(
                    zombie.getX(),
                    zombie.getY(),
                    zombie.getRow(),
                    bulletType
            );
            bullet.setTargetPlant(target);
            controller.addBullet(bullet);
        }

        timer = cooldown;
    }
}