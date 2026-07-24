package models.games.specialGames;

import commands.GameCommands;
import controllers.Start.PlantSelection;
import models.App;
import models.Constants;
import models.entity.Plant;
import models.entity.PlantCategory;
import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.games.Game;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantWhatYouGet extends BaseGame implements SpecialGame {
    PlantWhatYouGet(){
        selection = new PlantSelection(filterPlants());
        sunCount = Constants.PlantWhatYouGet_StartingSunCount;
    }
    @Override
    public ArrayList<PlantType> filterPlants() {
        ArrayList<PlantType> plantTypes = new ArrayList<>();
        for (PlantType plant : App.getCurrentuser().getUnlockedPlants()) {
            if(plant.getCategory() != PlantCategory.SunProducer) plantTypes.add(plant);
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
        return selectionFinished;
    }

    @Override
    public void playGame(float delta) {
        if(selectionFinished &&  plantFinished){
             super.playGame(delta);
        }
    }

    @Override
    public String plant(String plantName, int x, int y) {
        String output = super.plant(plantName, x, y);
        if(sunCount == 0) plantFinished = true;
        return output;
    }


}
