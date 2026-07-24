package models.games.specialGames;

import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.entity.Plant;
import models.utils.Result;

import java.util.ArrayList;

public class SaveOurSeeds extends BaseGame implements SpecialGame {
    ArrayList<Plant> toProtect;
    @Override
    public ArrayList<PlantType> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }

    @Override
    public Result check_endGame() {
        for (Plant p : toProtect) {
            if(!p.isAlive()) return new Result(true , "Loss" , null);
        }
        return  new Result(false , null , null);
    }
}
