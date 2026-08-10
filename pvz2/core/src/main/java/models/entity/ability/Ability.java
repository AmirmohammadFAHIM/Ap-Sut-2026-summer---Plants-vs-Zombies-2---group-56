package models.entity.ability;

import models.entity.Zombie;
import models.games.BaseGame;

public interface Ability {
    void execute(Zombie zombie, float deltaTime, BaseGame game);
}