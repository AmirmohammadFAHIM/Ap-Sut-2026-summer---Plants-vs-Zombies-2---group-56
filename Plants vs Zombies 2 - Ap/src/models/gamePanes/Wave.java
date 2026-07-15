package models.gamePanes;

import models.entity.Zombie;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Wave {
    private ArrayList<Zombie> zombies;
    private boolean finished = false;
    private int zombieCount = 0;
    private int zombiesHP;
    private int id;
    private int hardness;

    public void initWave(){

    };

    public void isFinished(){

    };

    public void updateWave(){

       try {
           TimeUnit.SECONDS.sleep(5);
       }catch (InterruptedException e){
           e.printStackTrace();
       }
    };


    public ArrayList<Zombie> getZombies() {
        return zombies;
    }
}
