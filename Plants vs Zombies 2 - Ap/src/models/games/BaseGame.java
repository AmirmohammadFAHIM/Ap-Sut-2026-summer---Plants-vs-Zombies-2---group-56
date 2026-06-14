package models.games;

import models.factory.builder.SunBuilder;
import models.gamePanes.Field;
import models.gamePanes.Wave;
import models.npc.Bullet;
import models.npc.Plant;

import java.util.ArrayList;

public class BaseGame implements Game {
    private int sunCount = 0;
    private Field field ;
    private ArrayList<Wave> waves;
    private ArrayList<Plant> plants;
    private SunBuilder sunBuilder;
    private Wave currentWave;
    private ArrayList<Bullet>  bullets;

    public int getSunCount() {
        return sunCount;
    }

    public void setSunCount(int sunCount) {
        this.sunCount = sunCount;
    }

    @Override
    public void initGame() {

    }

    @Override
    public void playGame() {

    }

    @Override
    public void updatePlants() {

    }

    @Override
    public void updateZombies() {

    }

    @Override
    public void updateScene() {

    }

    @Override
    public void updateGame() {

    }

    @Override
    public void plant() {

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }


    //  public void plant(){}

  //  public void pluck(){}

  //  public void ShowMap(){}

  //  public void ShowPlantsStatus(){}

  //  public void ShowTile(){}

   // public void showPlant(){}



}
