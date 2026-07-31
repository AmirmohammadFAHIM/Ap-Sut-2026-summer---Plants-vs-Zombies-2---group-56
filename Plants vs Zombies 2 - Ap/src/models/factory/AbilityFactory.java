package models.factory;

import models.config.AbilityConfig;
import models.entity.BulletType;
import models.entity.ability.*;
import models.entity.ability.MoveAbility.MoveType;
import models.entity.ability.SpeedChangeAbility.TriggerType;

public class AbilityFactory {

    public static Ability create(AbilityConfig config) {
        String type = config.getType();

        switch (type) {
            case "bullet":
                return new BulletAbility(
                        BulletType.valueOf(config.getBulletType()),
                        config.getCooldown(),
                        config.getRange()
                );

            case "explode":
                return new ExplodeAbility(
                        config.getRange(),
                        config.getDamage(),
                        config.getCooldown()
                );

            case "move":
                return new MoveAbility(
                        MoveType.valueOf(config.getMoveType()),
                        config.getCooldown()
                );

            case "spawn":
                return new SpawnAbility(
                        config.getSpawnType(),
                        config.getCount(),
                        config.getCooldown(),
                        config.getHealthThreshold()
                );

            case "sun_robbing":
                return new SunRobbingAbility(
                        config.getMaxStolenSun(),
                        config.getStealRate()
                );

            case "speed_change":
                return new SpeedChangeAbility(
                        config.getMultiplier(),
                        TriggerType.valueOf(config.getTriggerType())
                );

            default:
                System.err.println("⚠️ Unknown ability type: " + type + " — ignored.");
                return null;
        }
    }
}