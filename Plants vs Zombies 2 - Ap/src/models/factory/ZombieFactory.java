package models.factory;

import models.config.ZombieConfig;
import models.config.AbilityConfig;
import models.npc.Zombie;
import models.npc.ability.*;
import models.loader.ZombieDataLoader;
import controllers.observer.*;

public class ZombieFactory {

    public static Zombie createZombie(String alias) {
        ZombieConfig config = ZombieDataLoader.getZombieConfig(alias);
        if (config == null) {
            throw new IllegalArgumentException("Unknown zombie alias: " + alias);
        }

        Zombie zombie = new Zombie(alias, config);

        // ====== ADD ABILITIES FROM CONFIG ======
        if (config.getObjdata().getAbilities() != null) {
            for (AbilityConfig abilityConfig : config.getObjdata().getAbilities()) {
                Ability ability = AbilityFactory.create(abilityConfig);
                if (ability != null) {
                    zombie.addAbility(ability);
                }
            }
        }

        // ====== ADD BULLET OBSERVERS ======
        addBulletObservers(zombie, config);

        return zombie;
    }

    private static void addBulletObservers(Zombie zombie, ZombieConfig config) {
        String objClass = config.getObjclass();
        String id = config.getPrimaryAlias();

        // Juggler
        if ("ZombieDarkJugglerProps".equals(objClass)) {
            zombie.addBulletObserver(new JugglerObserver());
        }

        // Dragon Imp (fire immunity)
        if (id != null && id.toLowerCase().contains("dragon")) {
            zombie.addBulletObserver(new DragonObserver());
        }

        // Parasol (from armor: Crown or Parasol)
        if (config.getObjdata().getZombieArmorProps() != null) {
            for (String armorRef : config.getObjdata().getZombieArmorProps()) {
                if (armorRef.contains("Crown") || armorRef.contains("Parasol")) {
                    zombie.addBulletObserver(new ParasolObserver());
                    break;
                }
            }
        }
    }
}