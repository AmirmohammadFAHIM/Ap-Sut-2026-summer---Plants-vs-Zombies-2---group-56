package models.games.specialGames;

import models.games.BaseGame;
import models.entity.Plant;

import java.util.ArrayList;

public class SaveOurSeeds extends BaseGame implements SpecialGame {
    ArrayList<Plant> toProtect;
    @Override
    public ArrayList<Plant> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean check_endGame() {
        for (Plant p : toProtect) {
            if(!p.isAlive()) return true;
        }
        return  false;
    }
}
