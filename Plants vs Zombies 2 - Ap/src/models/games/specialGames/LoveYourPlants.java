package models.games.specialGames;

import models.entity.Plant;
import models.games.BaseGame;

import java.util.ArrayList;

public class LoveYourPlants extends BaseGame implements SpecialGame {
    int deadPlants = 0;
    @Override
    public ArrayList<Plant> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean check_endGame() {
        return deadPlants >= 5;
    }
}
