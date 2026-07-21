package models.npc.ability;

import models.npc.Zombie;
import controllers.GameController;

public class SpeedChangeAbility extends Ability {

    private final float multiplier;   // 0.5 = نصف, 4.0 = ۴ برابر
    private final TriggerType trigger;
    private boolean triggered;

    public enum TriggerType {
        ON_KILL,        // AllStar: بعد از کشتن گیاه
        ON_ARMOR_BROKEN // Newspaper: بعد از شکستن روزنامه
    }

    public SpeedChangeAbility(float multiplier, TriggerType trigger) {
        this.multiplier = multiplier;
        this.trigger = trigger;
        this.triggered = false;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, GameController controller) {
        if (triggered) return;

        boolean condition = false;

        switch (trigger) {
            case ON_KILL:
                // AllStar: بعد از کشتن گیاه
                condition = controller.hasKilledPlant(zombie);
                break;
            case ON_ARMOR_BROKEN:
                // Newspaper: بعد از شکستن روزنامه
                condition = controller.isArmorBroken(zombie, "newspaper");
                break;
        }

        if (condition) {
            triggered = true;
            float newSpeed = zombie.getSpeed() * multiplier;
            zombie.setSpeed(newSpeed);
        }
    }
}