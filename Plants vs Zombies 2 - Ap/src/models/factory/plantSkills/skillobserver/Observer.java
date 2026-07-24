package models.factory.plantSkills.skillobserver;

import models.entity.Plant;
import models.games.BaseGame;

public interface Observer {

    public boolean observe(Plant self , BaseGame game);
}
