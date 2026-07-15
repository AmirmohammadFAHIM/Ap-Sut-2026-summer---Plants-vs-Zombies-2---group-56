package models.gamePanes;

import models.entity.Zombie;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Wave {
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private boolean finished = false;
    private int zombieCount = 0;
    private float zombiesHP;
    private int id;
    private int cost;

    Random rand = new Random();
    public void initWave(ArrayList<Zombie> available){
        if(cost == 0) return;
        int index = rand.nextInt(available.size());
        zombies.add(available.get(index));
        zombieCount++;
        zombiesHP += zombies.getLast().getHp();
        cost -= zombies.getLast().getCost();
        initWave(available);
    }

    public boolean isFinished(){
        float totalHp = 0;
        for (Zombie z : zombies) {
            totalHp += z.getHp();
        }
        return totalHp <= 0.75f * zombiesHP;
    }

    public void updateWave(){


    }


    public ArrayList<Zombie> getZombies() {
        return zombies;
    }
}
