package models.games.specialGames;

import controllers.datacontroller.Data;
import controllers.datacontroller.SeedPackage;
import models.Constants;
import models.entity.PlantTags;
import models.factory.builder.PlantType;
import models.games.NormalGame;
import models.utils.Result;

import java.util.ArrayList;

public class NightsOps extends NormalGame implements SpecialGame {
    public NightsOps(){
        day = false;
    }
    @Override
    public Result check_endGame() {
       return super.check_endGame();
    }

    @Override
    public boolean startGame(String plantName) {
        SeedPackage selected = selection.selectPlant(plantName);


        return available_plants.size() == Constants.Plants_count_in_a_game;
    }

    @Override
    public ArrayList<PlantType> filterPlants() {
        return null;
    }

    @Override
    public void attack() {

    }
}
