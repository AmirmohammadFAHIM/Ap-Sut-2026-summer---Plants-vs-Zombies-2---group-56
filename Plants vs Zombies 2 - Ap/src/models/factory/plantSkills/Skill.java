package models.factory.plantSkills;

import models.games.BaseGame;
import models.npc.Plant;

public interface Skill {
    public void baseskill(Plant plant , BaseGame game);

    public void plantFoodSkill(Plant plant , BaseGame game);
}
