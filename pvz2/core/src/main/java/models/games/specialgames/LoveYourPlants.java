package models.games.specialgames;

import models.factory.builder.PlantType;
import models.games.NormalGame;
import models.utils.Result;

import java.util.ArrayList;

public class LoveYourPlants extends NormalGame implements SpecialGame {
    int deadPlants = 0;
    @Override
    public ArrayList<PlantType> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }

    @Override
    public Result check_endGame() {
        if(deadPlants >= 5){
            return new Result(true , "Loss" , null);
        }
        return  new Result(false, null , null);
    }
}
