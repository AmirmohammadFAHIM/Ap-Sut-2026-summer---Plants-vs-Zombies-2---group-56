package models.games.minigames;

import models.entity.Plant;
import models.factory.builder.PlantType;
import models.gameadventure.Chapters;
import models.games.NormalGame;

import java.util.EnumMap;
import java.util.HashMap;

public class Beghouled extends NormalGame {
    public Beghouled(int i) {

    }


    @Override
    public String playGame(float delta) {
        if(resetNeeded()){
            reset();
            return "LeBron James";
        }
        return "Game played";
    }

    public void move(Plant plant , boolean positive , boolean horizontal){
        boolean canMove = checkMove(plant, positive, horizontal);
        if(canMove){

        }
    }

    private void movePlant(){

    }

    private void deleteCombinations(){

    }


    private void reset(){}

    private boolean resetNeeded(){
        for (Plant x : plantsInField){
            boolean right = checkMove(x,true,true);
            boolean left = checkMove(x,false,true);
            boolean up =  checkMove(x,true,false);
            boolean down =  checkMove(x,false,false);
            if(!right && !left && !up && !down){
                return true;
            }
        }
        return false;
    }

    private boolean checkMove(Plant plant, boolean positive , boolean horizontal){
        EnumMap<PlantType , Integer> plantsInThisColumn = new EnumMap<>(PlantType.class);

        int i = positive ? 1 : -1;
        for (Plant x : plantsInField){

            boolean check = horizontal ? x.getLine() == plant.getLine() + i
                : x.getTileIndex() == plant.getTileIndex() + i;
            if(check){
                plantsInThisColumn.merge(x.getType(), 1, Integer::sum);
            }
        }
        for (Integer integer : plantsInThisColumn.values()){
            if(integer >= 3){
                return true;
            }
        }
        return false;
    }



}
