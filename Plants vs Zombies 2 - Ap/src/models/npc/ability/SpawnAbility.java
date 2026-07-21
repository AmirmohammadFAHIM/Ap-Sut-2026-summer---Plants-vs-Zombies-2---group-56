package models.npc.ability;

import models.npc.Zombie;
import controllers.GameController;

public class SpawnAbility extends Ability {

    private final String spawnType;
    private final int count;
    private final float cooldown;
    private final float healthThreshold;

    private float timer;
    private boolean triggered;
    private boolean isDeadTriggered;

    // ====== برای قبرساز (تایمر) ======
    public SpawnAbility(String spawnType, int count, float cooldown) {
        this(spawnType, count, cooldown, 0, false);
    }

    // ====== برای غول (شرط سلامت) ======
    public SpawnAbility(String spawnType, int count, float healthThreshold) {
        this(spawnType, count, 0, healthThreshold, false);
    }

    // ====== برای بشکه (شرط مرگ) ======
    public SpawnAbility(String spawnType, int count, boolean isDeadTriggered) {
        this(spawnType, count, 0, 0, isDeadTriggered);
    }

    private SpawnAbility(String spawnType, int count, float cooldown, float healthThreshold, boolean isDeadTriggered) {
        this.spawnType = spawnType;
        this.count = count;
        this.cooldown = cooldown;
        this.healthThreshold = healthThreshold;
        this.isDeadTriggered = isDeadTriggered;
        this.timer = 0;
        this.triggered = false;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, GameController controller) {

        // ====== حالت اول: غول (شرط سلامت) ======
        if (healthThreshold > 0) {
            if (triggered) return;

            float healthPercent = (float) zombie.getHp() / zombie.getMaxHp();
            if (healthPercent <= healthThreshold) {
                controller.spawn(zombie, spawnType, count);
                triggered = true;
            }
            return;
        }

        // ====== حالت دوم: بشکه (شرط مرگ) ======
        if (isDeadTriggered) {
            if (triggered) return;

            if (zombie.isDead()) {
                controller.spawn(zombie, spawnType, count);
                triggered = true;
            }
            return;
        }

        // ====== حالت سوم: قبرساز (تایمر) ======
        timer += deltaTime;
        if (timer >= cooldown) {
            timer = 0;
            controller.spawn(zombie, spawnType, count);
        }
    }
}