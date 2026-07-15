package models.games.specialGames;

import models.Constants;
import models.entity.Zombie;
import models.games.BaseGame;
import models.entity.Plant;

import java.util.ArrayList;

public class Deadline extends BaseGame implements SpecialGame {
    @Override
    public ArrayList<Plant> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean check_endGame() {
        for (Zombie z : zombies) {
            if(z.getTileIndex() <= Constants.DeadLine_TileIndex){
                return true;
            }
        }
        return false;
    }

    @Override
    public void endGame() {

    }
}
