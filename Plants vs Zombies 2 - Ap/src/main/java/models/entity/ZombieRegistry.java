package models.entity;

import java.io.Serializable;
import java.util.*;

public class ZombieRegistry implements Serializable {

    // ====== ALL ZOMBIE TYPES IN THE GAME ======
    public enum ZombieType {
        NORMAL,
        CONEHEAD,
        BUCKETHEAD,
        BRICKHEAD,
        KNIGHT,
        IMP,
        GARGANTUAR,
        ALLSTAR,
        ARCADe,
        PARASOL,
        TURQUOISE,
        PROSPECTOR,
        PIANIST,
        NEWSPAPER,
        BARREL_ROLLER,
        RA,
        EXPLORER,
        TOMB_RAISER,
        DODO_RIDER,
        HUNTER,
        TROGLOBITE,
        FISHERMAN,
        SNORKEL,
        OCTOPUS,
        JUGGLER,
        WIZARD,
        KING,
        IMP_DRAGON
    }

    // ====== PER-USER REGISTRY ======
    private final Map<ZombieType, Boolean> registry = new HashMap<>();

    public ZombieRegistry() {
        reset();
    }

    public void unlock(ZombieType type) {
        registry.put(type, true);
    }

    public void unlock(String typeName) {
        try {
            ZombieType type = ZombieType.valueOf(typeName.toUpperCase());
            unlock(type);
        } catch (IllegalArgumentException e) {
            // ignore unknown types
        }
    }

    public boolean isUnlocked(ZombieType type) {
        return registry.getOrDefault(type, false);
    }

    public boolean isUnlocked(String typeName) {
        try {
            ZombieType type = ZombieType.valueOf(typeName.toUpperCase());
            return isUnlocked(type);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public List<ZombieType> getUnlockedZombies() {
        List<ZombieType> result = new ArrayList<>();
        for (Map.Entry<ZombieType, Boolean> entry : registry.entrySet()) {
            if (entry.getValue()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public List<ZombieType> getAllZombieTypes() {
        return new ArrayList<>(registry.keySet());
    }

    public int getUnlockedCount() {
        return (int) registry.values().stream().filter(v -> v).count();
    }

    public int getTotalCount() {
        return registry.size();
    }

    public void reset() {
        for (ZombieType type : ZombieType.values()) {
            registry.put(type, false);
        }
    }
}