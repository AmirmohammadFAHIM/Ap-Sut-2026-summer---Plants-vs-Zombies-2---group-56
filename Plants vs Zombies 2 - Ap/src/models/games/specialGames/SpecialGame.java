package models.games.specialGames;

import models.npc.Plant;

import java.util.ArrayList;

public interface SpecialGame {

    public ArrayList<Plant> filterPlants();

    public void attack();
}
