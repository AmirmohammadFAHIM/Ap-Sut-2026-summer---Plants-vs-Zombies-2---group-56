package models.games.miniGames;

import models.Constants;
import models.entity.Zombie;
import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.utils.Result;

import java.util.ArrayList;
import java.util.Random;

public class WallnutBowling extends BaseGame {
    public  WallnutBowling(MinigameLevel level) {

    }
    ArrayList<String> belt = new ArrayList<>();
    ArrayList<BowlingNut>  nuts = new ArrayList<>();


    @Override
    public Result check_endGame() {
        for (Zombie z : zombies) {
            if(z.getTileIndex() < Constants.WallnutLimitLine) return new Result(true,"Loss",null);
        }
        if(won) return  new Result(true,"Won",null);
        return new  Result(false,null,null);
    }


    @Override
    public void playGame(float delta) {
        for (BowlingNut x : nuts){
            x.go(delta, this);
        }
        attack(delta);
    }

    @Override
    public String plant(String plantName, int x, int y) {
        if(x >= Constants.WallnutLimitLine) return "You can't plant after limit line!";
        if(plantName.equals("Wallnut") && belt.contains(plantName)){
            nuts.add(makeNut(false , x ,y));
        }
        else if(plantName.equals("Explod'O nut") && belt.contains(plantName)){
            nuts.add(makeNut(true , x ,y));
        }
        else return "Come on man open your eyes.";
        return "Suiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii , bowling on It's wayyyyy";
    }

    private BowlingNut makeNut(boolean explosive , int x , int y) {
        BowlingNut bowling = new BowlingNut(1000 , explosive);
        Random rand = new Random();
        boolean up = rand.nextBoolean();
        bowling.setVelocityX(Constants.BowlingWallnutVelocity);
        bowling.setVelocityY(Constants.BowlingWallnutVelocity * 0.4f * (up ? 1 : -1));
        bowling.setTileIndex(x);
        bowling.setLine(y);
        nuts.add(bowling);
        return  bowling;
    }

    public ArrayList<BowlingNut> getNuts() {
        return nuts;
    }

    @Override
    protected Result attack(float delta) {
        if(currentWave.isFinished()){
            if(waveID == waves.size() - 1) return null;
            previousWave = currentWave;
            currentWave = waves.get(waveID);
            zombies.addAll(currentWave.getZombies());
            waveID += 1;
            return new Result(true,"new Wave",null);
        }
        return  new  Result(false,null,null);
    }
}
