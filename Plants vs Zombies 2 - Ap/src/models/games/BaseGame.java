package models.games;

import models.factory.builder.SunBuilder;
import models.gamePanes.Field;
import models.gamePanes.Wave;
import models.entity.Bullet;
import models.entity.Plant;
import models.entity.Sun;
import models.entity.Zombie;

import java.util.ArrayList;

public class BaseGame implements Game {
    public enum GameState{STARTING , PLAYING , PAUSE , END}

    protected int sunCount = 0;
    protected Field field ;
    protected ArrayList<Wave> waves;
    protected ArrayList<Plant> plants;
    protected SunBuilder sunBuilder;
    protected Wave currentWave;
    protected Wave previousWave;
    protected ArrayList<Zombie> zombies; ///combination of current wave and next wave
    protected ArrayList<Bullet>  bullets;
    protected ArrayList<Sun> suns;

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

    @Override
    public void dePlant() {

    }

    @Override
    public boolean check_endGame() {
        for (Zombie z : zombies) {
            if(z.getX() <= 0) return true;
        }
        return false;
    }

    @Override
    public void endGame() {

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Sun> getSuns() {
        return suns;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public void setWaves(ArrayList<Wave> waves) {
        this.waves = waves;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    public SunBuilder getSunBuilder() {
        return sunBuilder;
    }

    public void setSunBuilder(SunBuilder sunBuilder) {
        this.sunBuilder = sunBuilder;
    }

    public void setCurrentWave(Wave currentWave) {
        this.currentWave = currentWave;
    }

    public Wave getPreviousWave() {
        return previousWave;
    }

    public void setPreviousWave(Wave previousWave) {
        this.previousWave = previousWave;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void setSuns(ArrayList<Sun> suns) {
        this.suns = suns;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public void setZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    //  public void plant(){}

  //  public void pluck(){}

  //  public void ShowMap(){}

  //  public void ShowPlantsStatus(){}

  //  public void ShowTile(){}

   // public void showPlant(){}



}
