package models.games.specialgames;

import models.factory.builder.PlantType;

import java.util.ArrayList;

public interface SpecialGame {

    public ArrayList<PlantType> filterPlants();

    public void attack();
}
