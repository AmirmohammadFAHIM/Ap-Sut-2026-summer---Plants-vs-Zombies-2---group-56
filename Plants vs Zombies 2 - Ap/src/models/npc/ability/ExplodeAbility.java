package models.npc.ability;

import models.npc.*;
import controllers.GameController;

public class ExplodeAbility implements Ability {

    private final float range;
    private final int damage;
    private final float cooldown;
    private float timer;
    private boolean triggered = false;

    public ExplodeAbility(float range, int damage, float cooldown) {
        this.range = range;
        this.damage = damage;
        this.cooldown = cooldown;
        this.timer = 0;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, ZombieController controller) {
        if (triggered) return;
        if (zombie.isDead()) return;

        // برای دینامیت: اگر یخ زده باشد، تایمر متوقف می‌شود
        if (zombie.getAbility(DynamiteState.class) != null) {
            DynamiteState state = zombie.getAbility(DynamiteState.class);
            if (state.isFrozen()) return;
        }

        timer += deltaTime;
        if (timer >= cooldown) {
            triggered = true;

            if (zombie.hasEffect(EffectType.HYPNOTIZED)) {
                controller.explodeAreaOnZombies(zombie.getRow(), zombie.getX(), range, damage);
            } else {
                controller.explodeArea(zombie.getRow(), zombie.getX(), range, damage);
            }
        }
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void reset() {
        triggered = false;
        timer = 0;
    }
}

//package models.npc.ability;
//
//        import models.npc.Zombie;
//        import models.npc.EffectType;
//        import controllers.GameController;
//
//public class ExplodeAbility extends Ability {
//
//    private final float range;
//    private final int damage;
//    private final float cooldown;
//    private float timer;
//
//    public ExplodeAbility(float range, int damage, float cooldown) {
//        this.range = range;
//        this.damage = damage;
//        this.cooldown = cooldown;
//        this.timer = 0;
//    }
//
//    @Override
//    public void execute(Zombie zombie, float deltaTime, GameController controller) {
//        timer -= deltaTime;
//        if (timer > 0) return;
//
//        // اگر هیپنوتیزم شده باشد، به زامبی‌ها حمله می‌کند
//        if (zombie.hasEffect(EffectType.HYPNOTIZED)) {
//            controller.explodeAreaOnZombies(zombie.getRow(), zombie.getX(), range, damage);
//        } else {
//            controller.explodeArea(zombie.getRow(), zombie.getX(), range, damage);
//        }
//
//        timer = cooldown;
//    }
//}