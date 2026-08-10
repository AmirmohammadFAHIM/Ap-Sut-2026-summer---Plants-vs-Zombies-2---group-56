package models.games.minigames;

import controllers.datacontroller.SeedPackage;
import models.entity.Zombie;
import models.factory.builder.PlantType;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void breakPlantVase(VaseBraker game , HashMap<PlantType , SeedPackage> seedPackageHashMap) {
        Random rand = new Random();
        int index = rand.nextInt(game.availablePlants.size());
        PlantType plant =  game.availablePlants.get(index);
        seedPackageHashMap.compute(plant, (k, seedPackage) -> seedPackage);
    }

    public void breakVase(VaseBraker game) {
        Random rand = new Random();
        int i = rand.nextInt(3);
        if(i == 0){
            breakPlantVase(game , game.seedPackages);
        }
        else if(i == 1){
            breakZombieVase(game , game.getZombies());
        }


    }


}
