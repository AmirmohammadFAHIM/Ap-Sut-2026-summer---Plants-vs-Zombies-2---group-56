package models.factory;

import models.config.ZombieConfig;
import models.npc.Zombie;
import models.loader.ZombieDataLoader;

import java.util.*;

public class ZombieFactory {

    private static final Map<String, Zombie> prototypeCache = new HashMap<>();

    public static Zombie createZombie(String alias) {
        if (prototypeCache.containsKey(alias)) {
            return cloneZombie(prototypeCache.get(alias));
        }

        ZombieConfig config = ZombieDataLoader.getZombieConfig(alias);
        if (config == null) {
            throw new IllegalArgumentException("Unknown zombie alias: " + alias);
        }

        Zombie zombie = new Zombie(alias, config);
        prototypeCache.put(alias, zombie);
        return zombie;
    }

    private static Zombie cloneZombie(Zombie prototype) {
        ZombieConfig config = ZombieDataLoader.getZombieConfig(prototype.getId());
        return new Zombie(prototype.getId(), config);
    }

    public static Set<String> getAllZombieTypes() {
        return ZombieDataLoader.getAllZombieAliases();
    }
}