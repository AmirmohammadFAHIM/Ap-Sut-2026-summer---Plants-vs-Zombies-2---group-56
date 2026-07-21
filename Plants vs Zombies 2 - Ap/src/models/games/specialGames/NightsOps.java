package models.games.specialGames;

import controllers.datacontroller.Data;
import controllers.datacontroller.SeedPackage;
import models.Constants;
import models.entity.PlantTags;
import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.utils.Result;

import java.util.ArrayList;

public class NightsOps extends BaseGame implements SpecialGame {
    @Override
    public Result check_endGame() {
       return super.check_endGame();
    }

    @Override
    public boolean startGame(String plantName) {
        SeedPackage selected = selection.selectPlant(plantName);
        if(available_plants.containsKey(selected.getPlant())) {
            return false;
        }
        else{
            if(Data.getPlants().get(selected.getPlant()).getTags().contains(PlantTags.DAY)){
                available_plants.put(selected.getPlant() , selected);
                /// here you print a warning that this plant gonna be sleeping the whole game
            }
            else available_plants.put(selected.getPlant() , selected);
        }

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
