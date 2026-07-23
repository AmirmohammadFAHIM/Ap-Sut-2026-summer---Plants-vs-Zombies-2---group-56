package models.npc.ability;

import models.npc.Zombie;
import models.games.BaseGame;

public interface Ability {
    void execute(Zombie zombie, float deltaTime, BaseGame game);
}