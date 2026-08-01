package models.games.specialGames;

import models.Constants;
import models.entity.Zombie;
import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.games.NormalGame;
import models.utils.Result;

import java.util.ArrayList;

public class Deadline extends NormalGame implements SpecialGame {
    @Override
    public ArrayList<PlantType> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }

    @Override
    public Result check_endGame() {
        for (Zombie z : zombies) {
            if(z.getTileIndex() <= Constants.DeadLine_TileIndex){
                return new Result(true , "Loss" , null);
            }
        }
        return new Result(false , null, null);
    }

    @Override
    public void endGame() {

    }
}
