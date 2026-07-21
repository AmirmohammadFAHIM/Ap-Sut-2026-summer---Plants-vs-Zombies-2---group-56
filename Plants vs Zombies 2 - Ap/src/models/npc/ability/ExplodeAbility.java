package models.npc.ability;

import models.npc.Zombie;
import models.npc.EffectType;
import controllers.GameController;

public class ExplodeAbility extends Ability {

    private final float range;
    private final int damage;
    private final float cooldown;
    private float timer;

    public ExplodeAbility(float range, int damage, float cooldown) {
        this.range = range;
        this.damage = damage;
        this.cooldown = cooldown;
        this.timer = 0;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, GameController controller) {
        timer -= deltaTime;
        if (timer > 0) return;

        // اگر هیپنوتیزم شده باشد، به زامبی‌ها حمله می‌کند
        if (zombie.hasEffect(EffectType.HYPNOTIZED)) {
            controller.explodeAreaOnZombies(zombie.getRow(), zombie.getX(), range, damage);
        } else {
            controller.explodeArea(zombie.getRow(), zombie.getX(), range, damage);
        }

        timer = cooldown;
    }
}