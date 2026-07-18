package controllers.strategy;

import models.npc.Zombie;
import models.npc.Plant;

import java.util.*;

public class WizardBehavior implements BehaviorStrategy {

    private float cooldown = 0;
    private final float maxCooldown = 3.0f;
    private final List<Plant> cats = new ArrayList<>();

    @Override
    public void execute(Zombie zombie, float deltaTime) {
        cooldown -= deltaTime;
        if (cooldown <= 0 && zombie.isNearPlant()) {
            Plant target = zombie.getTargetPlant();
            if (target != null) {
                target.setCat(true);
                cats.add(target);
                cooldown = maxCooldown;
            }
        }
    }

    public void releaseCats() {
        for (Plant plant : cats) {
            plant.setCat(false);
        }
        cats.clear();
    }
}