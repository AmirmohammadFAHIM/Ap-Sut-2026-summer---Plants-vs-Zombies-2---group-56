package models.npc.ability;

import models.npc.Zombie;
import controllers.GameController;

public interface Ability {
    void execute(Zombie zombie, float deltaTime, GameController controller);

    default void onDeath(Zombie zombie, GameController controller) {
        // Optional: to be overridden by abilities that need death handling
    }
}
