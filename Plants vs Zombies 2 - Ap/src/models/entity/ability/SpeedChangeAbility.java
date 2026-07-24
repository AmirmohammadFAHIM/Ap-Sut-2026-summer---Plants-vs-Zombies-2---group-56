package models.entity.ability;

import models.entity.Zombie;
import models.games.BaseGame;

public class SpeedChangeAbility implements Ability {

    public enum TriggerType {
        ON_KILL,
        ON_ARMOR_BROKEN
    }

    private final float multiplier;
    private final TriggerType trigger;
    private boolean triggered;

    public SpeedChangeAbility(float multiplier, TriggerType trigger) {
        this.multiplier = multiplier;
        this.trigger = trigger;
        this.triggered = false;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, BaseGame game) {
        if (triggered) return;

        boolean condition = false;
        switch (trigger) {
            case ON_KILL:
                condition = game.hasKilledPlant(zombie);
                break;
            case ON_ARMOR_BROKEN:
                condition = game.isArmorBroken(zombie, "newspaper");
                break;
        }

        if (condition) {
            triggered = true;
            zombie.setSpeed(zombie.getSpeed() * multiplier);
        }
    }
}