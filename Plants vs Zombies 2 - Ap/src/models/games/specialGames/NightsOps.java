package models.games.specialGames;

import models.Constants;
import models.entity.Plant;
import models.entity.PlantTags;
import models.factory.builder.PlantType;
import models.games.BaseGame;

import java.util.ArrayList;

public class NightsOps extends BaseGame implements SpecialGame {
    @Override
    public boolean check_endGame() {
       return super.check_endGame();
    }

    @Override
    public boolean startGame(String plantName) {
        Plant selected = selection.selectPlant(plantName);
        if(available_plants.contains(selected)) {
            return false;
        }
        else{
            if(selected.getTags().contains(PlantTags.DAY)){
                available_plants.add(selected);
                /// here you print a warning that this plant gonna be sleeping the whole game
            }
            else available_plants.add(selected);
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
