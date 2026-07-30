package models.games.specialGames;

import commands.GameCommands;
import controllers.Start.PlantSelection;
import controllers.datacontroller.SeedPackage;
import models.App;
import models.Constants;
import models.entity.Plant;
import models.entity.PlantCategory;
import models.factory.builder.PlantBuilder;
import models.factory.builder.PlantType;
import models.gamePanes.Tile;
import models.games.BaseGame;
import models.games.Game;
import models.games.NormalGame;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantWhatYouGet extends NormalGame implements SpecialGame {
    public PlantWhatYouGet(){
        selection = new PlantSelection(filterPlants());
        sunCount = Constants.PlantWhatYouGet_StartingSunCount;
    }
    @Override
    public ArrayList<PlantType> filterPlants() {
        ArrayList<PlantType> plantTypes = new ArrayList<>();
        for (PlantType plant : App.getCurrentuser().getUnlockedPlants()) {
            if(plant.getCategory() != PlantCategory.SunProducer) plantTypes.add(plant);
        }
        return plantTypes;
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
        if(selectionFinished) return "Yo What? Wanna plant? Lol , you idiot , you're fucked up.";
        try {
            PlantBuilder builder = new PlantBuilder();
            PlantType type = PlantType.valueOf(plantName);
            if(!available_plants.containsKey(type)){
                return "Plant is not in the slots.";
            }
            float cost = available_plants.get(type).getCost();
            if(sunCount < cost){
                return "Not enough suns to plant " + plantName;
            }
            sunCount -= (int) cost;
            Plant plant = builder.build(type);
            plant.setTileIndex(x);
            plant.setTileIndex(y);
            plants_inField.add(plant);
            Tile tile = field.getTileByCoordinats(x, y);
            tile.setEmpty(true);
            return "Plant " + type +" planted at (" + x + ", " + y + ")" + "and cost you " + cost + " suns.";
        }catch (IllegalArgumentException e){
            return ("Invalid PlantType");
        }
    }

    public void startWaves(){
        selectionFinished = true;
    }



}
