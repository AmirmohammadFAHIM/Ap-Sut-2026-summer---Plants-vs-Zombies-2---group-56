package models.factory.plants;

import models.gamePanes.Field;
import models.games.Game;
import models.npc.Plant;

public interface Skill {

    public void DoSkill(Plant self , Game game);
}
