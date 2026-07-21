package models.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.config.ZombieConfig;
import models.config.ArmorConfig;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class ZombieDataLoader {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Map<String, ZombieConfig> zombieConfigs = new HashMap<>();
    private static final Map<String, ArmorConfig> armorConfigs = new HashMap<>();

    // ====== LOAD ZOMBIES ======
    public static void loadZombies(String path) throws Exception {
        List<ZombieConfig> configs = mapper.readValue(
                new File(path),
                new TypeReference<List<ZombieConfig>>() {}
        );
        indexZombies(configs);
    }

    public static void loadZombies(InputStream inputStream) throws Exception {
        List<ZombieConfig> configs = mapper.readValue(
                inputStream,
                new TypeReference<List<ZombieConfig>>() {}
        );
        indexZombies(configs);
    }

    private static void indexZombies(List<ZombieConfig> configs) {
        for (ZombieConfig config : configs) {
            if (config.getAliases() != null) {
                for (String alias : config.getAliases()) {
                    zombieConfigs.put(alias, config);
                }
            }
        }
    }

    // ====== LOAD ARMORS ======
    public static void loadArmors(String path) throws Exception {
        List<ArmorConfig> configs = mapper.readValue(
                new File(path),
                new TypeReference<List<ArmorConfig>>() {}
        );
        indexArmors(configs);
    }

    public static void loadArmors(InputStream inputStream) throws Exception {
        List<ArmorConfig> configs = mapper.readValue(
                inputStream,
                new TypeReference<List<ArmorConfig>>() {}
        );
        indexArmors(configs);
    }

    private static void indexArmors(List<ArmorConfig> configs) {
        for (ArmorConfig config : configs) {
            if (config.getAliases() != null) {
                for (String alias : config.getAliases()) {
                    armorConfigs.put(alias, config);
                }
            }
        }
    }

    // ====== GETTERS ======
    public static ZombieConfig getZombieConfig(String alias) {
        return zombieConfigs.get(alias);
    }

    public static ArmorConfig getArmorConfig(String alias) {
        return armorConfigs.get(alias);
    }

    public static Set<String> getAllZombieAliases() {
        return zombieConfigs.keySet();
    }

    public static Set<String> getAllArmorAliases() {
        return armorConfigs.keySet();
    }

    public static boolean hasZombie(String alias) {
        return zombieConfigs.containsKey(alias);
    }

    public static boolean hasArmor(String alias) {
        return armorConfigs.containsKey(alias);
    }
}