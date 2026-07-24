package models.games.specialGames;

import models.factory.builder.PlantType;
import models.games.BaseGame;

import java.util.ArrayList;

public class TimedWar extends BaseGame implements SpecialGame {
    @Override
    public ArrayList<PlantType> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }
}
