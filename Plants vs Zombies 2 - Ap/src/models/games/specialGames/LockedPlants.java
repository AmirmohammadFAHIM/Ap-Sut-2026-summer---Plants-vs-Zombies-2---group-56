package models.games.specialGames;

import models.entity.PlantCategory;
import models.factory.builder.PlantType;
import models.games.BaseGame;

import java.util.ArrayList;
import java.util.Iterator;

public class LockedPlants extends BaseGame implements SpecialGame {
    public LockedPlants(LockType type) {
        this.lockType = type;
    }
    public enum LockType{ByCategory , Random}
    LockType lockType ;
    @Override
    public ArrayList<PlantType> filterPlants() {
        return null;
    }


    @Override
    public boolean startGame(String plantName) {

        if(!available_plants.isEmpty() && lockType == LockType.ByCategory) {
            PlantCategory lock;
            lock = available_plants.lastEntry().getValue().getPlant().getCategory();

            selection.getPlantsToChoose().removeIf(plant -> plant.getCategory().equals(lock));
        }
        return super.startGame(plantName);

    }

    @Override
    public void attack() {

    }
}
