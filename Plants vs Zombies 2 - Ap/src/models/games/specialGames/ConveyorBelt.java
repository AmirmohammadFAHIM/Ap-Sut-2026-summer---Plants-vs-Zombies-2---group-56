package models.games.specialGames;

import models.App;
import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.utils.Result;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class ConveyorBelt extends BaseGame implements SpecialGame {
    ArrayList<PlantType> belt =  new ArrayList<>();
    ArrayList<PlantType> plants =  new ArrayList<>();

    public  ConveyorBelt() {

    }

    @Override
    public void playGame(float delta) {
        updateBelt(delta);
        super.playGame(delta);
    }

    int counter = 8;
    Random rand = new Random();
    @Override
    public ArrayList<PlantType> filterPlants() {
        if(counter == 0) return null;
        int random = rand.nextInt(App.getCurrentuser().getUnlockedPlants().size());
        PlantType toAdd = App.getCurrentuser().getUnlockedPlants().get(random);
       if(!plants.contains(toAdd)){
           plants.add(toAdd);
           counter--;
       }
       filterPlants();
        return null;
    }

    @Override
    public void attack() {

    }

    float beltTimer = 0;
    private void updateBelt(float delta){
        if(beltTimer <= 0){
            beltTimer = 12;
            int index = rand.nextInt(plants.size());
            belt.add(plants.get(index));
        }
        else beltTimer -= delta;
    }




}
