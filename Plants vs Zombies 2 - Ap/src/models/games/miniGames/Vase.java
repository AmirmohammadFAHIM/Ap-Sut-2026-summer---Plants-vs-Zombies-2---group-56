package models.games.miniGames;

import models.entity.Plant;
import models.entity.Zombie;
import models.factory.PlantFactory;
import models.factory.builder.PlantType;
import models.games.BaseGame;

import java.util.ArrayList;
import java.util.Random;

public class Vase {
    public enum Type {PLANT , ZOMBIE , RANDOM}
    Type type;
    int line;
    int tileIndex;
    public Vase(int line, int tileIndex , Type type) {
        this.line = line;
        this.tileIndex = tileIndex;
        this.type = type;
    }

    public void breakZombieVase(VaseBraker game , ArrayList<Zombie> inGame) {
        Random rand = new Random();
        int index = rand.nextInt(8);
        // TODO: build the zombie and put in the game
    }

    public void breakPlantVase(VaseBraker game , ArrayList<Plant> inGame) {
        Random rand = new Random();
        int index = rand.nextInt(game.availablePlants.size());
        PlantFactory  plantFactory = new PlantFactory();
        Plant plant = plantFactory.CreatePlant(game.availablePlants.get(index));
        plant.setLine(line);
        plant.setTileIndex(tileIndex);
        inGame.add(plant);
    }

    public void breakVase(VaseBraker game) {
        Random rand = new Random();
        int i = rand.nextInt(3);
        if(i == 0){
            
        }
        else if(i == 1){

        }
    }


}
