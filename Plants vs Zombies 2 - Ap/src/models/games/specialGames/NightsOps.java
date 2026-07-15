package models.games.specialGames;

import models.entity.Plant;
import models.games.BaseGame;

import java.util.ArrayList;

public class NightsOps extends BaseGame implements SpecialGame {
    @Override
    public boolean check_endGame() {
       return super.check_endGame();
    }

    @Override
    public ArrayList<Plant> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }
}
