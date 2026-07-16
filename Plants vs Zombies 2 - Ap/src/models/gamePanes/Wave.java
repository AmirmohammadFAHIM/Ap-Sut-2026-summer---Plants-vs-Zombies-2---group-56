package models.gamePanes;

import models.entity.Zombie;

import java.util.ArrayList;
import java.util.Random;

public class Wave {
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private boolean finished = false;
    private int zombieCount = 0;
    private float zombiesHP;
    private int id;
    private float cost;

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

    public void setZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getZombieCount() {
        return zombieCount;
    }

    public void setZombieCount(int zombieCount) {
        this.zombieCount = zombieCount;
    }

    public float getZombiesHP() {
        return zombiesHP;
    }

    public void setZombiesHP(float zombiesHP) {
        this.zombiesHP = zombiesHP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }
}
