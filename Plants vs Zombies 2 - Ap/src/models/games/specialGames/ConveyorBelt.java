package models.games.specialGames;

import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.utils.Result;

import java.util.ArrayList;

public class ConveyorBelt extends BaseGame implements SpecialGame {


    @Override
    public ArrayList<PlantType> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }

    @Override
    public Result check_endGame() {
        return super.check_endGame();
    }
}
