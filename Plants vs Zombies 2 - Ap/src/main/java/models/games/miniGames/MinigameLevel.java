package models.games.miniGames;

import models.entity.Plant;
import models.factory.builder.PlantType;

import java.util.ArrayList;

public class MinigameLevel {
    private int id;
    private ArrayList<PlantType> plants;
    private ArrayList<String> zombiesNames;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<PlantType> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<PlantType> plants) {
        this.plants = plants;
    }

    public ArrayList<String> getZombiesNames() {
        return zombiesNames;
    }

    public void setZombiesNames(ArrayList<String> zombiesNames) {
        this.zombiesNames = zombiesNames;
    }
}
