package models.games.specialGames;

import commands.GameCommands;
import models.entity.Plant;
import models.games.BaseGame;

import java.util.ArrayList;

public class PlantWhatYouGet extends BaseGame implements SpecialGame {
    @Override
    public ArrayList<Plant> filterPlants() {
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
            plantFinished = sunCount == 0;
            if(plantFinished) StartGameCommand = GameCommands.START_GAME;
            return false;
        }
        else{
            return true; // only when the command is start game , we reach here , so don't worry
        }



    }
}
