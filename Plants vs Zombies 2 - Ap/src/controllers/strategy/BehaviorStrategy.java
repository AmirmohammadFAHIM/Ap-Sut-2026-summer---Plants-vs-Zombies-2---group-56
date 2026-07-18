package controllers.strategy;

import models.npc.Zombie;

public interface BehaviorStrategy {
    void execute(Zombie zombie, float deltaTime);
}