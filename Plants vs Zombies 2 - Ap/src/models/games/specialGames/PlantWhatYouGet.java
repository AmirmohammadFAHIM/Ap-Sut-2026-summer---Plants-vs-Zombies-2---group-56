package models.games.specialGames;

import commands.GameCommands;
import controllers.Start.PlantSelection;
import models.App;
import models.Constants;
import models.entity.Plant;
import models.entity.PlantCategory;
import models.factory.builder.PlantType;
import models.games.BaseGame;

import java.util.ArrayList;

public class PlantWhatYouGet extends BaseGame implements SpecialGame {
    PlantWhatYouGet(){
        selection = new PlantSelection(filterPlants());
        sunCount = Constants.PlantWhatYouGet_StartingSunCount;
    }
    @Override
    public ArrayList<PlantType> filterPlants() {
        ArrayList<PlantType> plantTypes = new ArrayList<>();
        for (Plant plant : App.getCurrentuser().getPlants()) {
            if(plant.getCategory() != PlantCategory.SunProducer) plantTypes.add(plant.getType());
        }
        return null;
    }

    @Override
    public void attack() {

    }

    boolean selectionFinished;
    boolean plantFinished;
    @Override
    public boolean startGame(String input) {
        if(!selectionFinished){
           selectionFinished = super.startGame(input);
           if(selectionFinished) StartGameCommand = GameCommands.PLANT;
           return false;
        }
        else if(!plantFinished){
            int x , y; // get them from the regex(input)
            String plantName; // same thing here
            plant(plantName , x , y);
            /// TODO: cost the price of the plant from the suns in the game
            plantFinished = sunCount == 0;
            if(plantFinished) StartGameCommand = GameCommands.START_GAME;
            return false;
        }
        else{
            return true; // only when the command is start game , we reach here , so don't worry
        }



    }
}
