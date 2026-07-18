package controllers.strategy;

import models.npc.Zombie;

public class OctopusBehavior implements BehaviorStrategy {

    private float cooldown = 0;
    private final float maxCooldown = 3.0f;

    @Override
    public void execute(Zombie zombie, float deltaTime) {
        cooldown -= deltaTime;
        if (cooldown <= 0) {
            // find random plant in same row and throw octopus - delegated to controller
            cooldown = maxCooldown;
        }
    }
}