package controllers;

import controllers.menus.gameController.GameController;
import models.entity.*;
import models.gamePanes.*;
import models.factory.ZombieFactory;
import controllers.observer.WizardObserver;
import models.entity.ability.SunRobbingAbility;

import java.util.*;

public class ZombieController {

    private final List<Zombie> zombies = new ArrayList<>();
    private final WizardObserver wizardObserver = new WizardObserver();

    // ====== SPAWN ======
    public Zombie spawnZombie(String alias, int row, float x, float y) {
        Zombie zombie = ZombieFactory.createZombie(alias);
        zombie.setRow(row);
        zombie.setPosition(x, y);
        zombies.add(zombie);
        return zombie;
    }

    // ====== UPDATE ======
    public void update(float deltaTime, GameController gameController) {
        // 1. Update all zombies
        for (Zombie zombie : zombies) {
            zombie.update(deltaTime, gameController.getGame());
        }

        // 2. Check dead wizards and release cats
        wizardObserver.checkAndReleaseDeadWizards();

        // 3. Release stolen sun from dead Ra / Turquoise
        for (Zombie zombie : zombies) {
            if (zombie.isDead()) {
                SunRobbingAbility sunAbility = zombie.getAbility(SunRobbingAbility.class);
                if (sunAbility != null && sunAbility.getStolenSun() > 0) {
                    int released = sunAbility.getStolenSun() / 2;
                    gameController.getGame().addSun(released);
                }
            }
        }

        // 4. Remove dead zombies
        zombies.removeIf(Zombie::isDead);
    }

    // ====== QUERIES ======
    public List<Zombie> getZombies() {
        return Collections.unmodifiableList(zombies);
    }

    public List<Zombie> getZombiesInRow(int row) {
        List<Zombie> result = new ArrayList<>();
        for (Zombie z : zombies) {
            if (z.getRow() == row) {
                result.add(z);
            }
        }
        return result;
    }

    public Zombie getRandomZombie() {
        if (zombies.isEmpty()) return null;
        return zombies.get(new Random().nextInt(zombies.size()));
    }

    public Zombie getRandomZombieInRange(Zombie center, float range) {
        List<Zombie> candidates = new ArrayList<>();
        for (Zombie z : zombies) {
            if (z == center) continue;
            float dx = z.getX() - center.getX();
            if (Math.abs(dx) <= range * 80 && z.getRow() == center.getRow()) {
                candidates.add(z);
            }
        }
        if (candidates.isEmpty()) return null;
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    public Zombie findNearestZombie(Zombie center, float range) {
        Zombie nearest = null;
        float minDist = Float.MAX_VALUE;
        for (Zombie z : zombies) {
            if (z == center) continue;
            float dx = z.getX() - center.getX();
            if (Math.abs(dx) <= range * 80 && z.getRow() == center.getRow()) {
                float dist = Math.abs(dx);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = z;
                }
            }
        }
        return nearest;
    }

    public boolean isArmorBroken(Zombie zombie, String armorType) {
        for (Armor armor : zombie.getArmors()) {
            if (armor.getType().equals(armorType) && armor.isBroken()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasKilledPlant(Zombie zombie) {
        // AllStar: after first kill
        // This flag is set in Zombie.attack() when plant dies
        // We need to store it somewhere or check if speed is already halved
        if (zombie.getAllStarObserver() != null) {
            return zombie.getAllStarObserver().isSlowed();
        }
        return false;
    }

    // ====== ACTIONS ======
    public void spawn(Zombie source, String spawnType, int count) {
        // Spawn imps or graves based on spawnType
        // For imps: create imp zombies at source position
        // For graves: create grave grid items at source row, random columns
        // This will be handled by GameController or GridController
        // TODO: delegate to appropriate controller
    }

    // ====== WIZARD OBSERVER ======
    public void addCat(Zombie wizard, Plant plant) {
        wizardObserver.addCat(wizard, plant);
    }

    public WizardObserver getWizardObserver() {
        return wizardObserver;
    }
}