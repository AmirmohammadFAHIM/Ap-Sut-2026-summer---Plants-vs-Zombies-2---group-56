package models.games;

import models.factory.builder.SunBuilder;
import models.gamePanes.Field;
import models.gamePanes.Wave;
import models.npc.Bullet;
import models.npc.Plant;
import models.npc.Sun;
import models.npc.Zombie;

import java.util.ArrayList;

public class BaseGame implements Game {
    private int sunCount = 0;
    private Field field ;
    private ArrayList<Wave> waves;
    private ArrayList<Plant> plants;
    private SunBuilder sunBuilder;
    private Wave currentWave;
    private Wave previousWave;
    private ArrayList<Zombie> zombies; ///combination of current wave and next wave
    private ArrayList<Bullet>  bullets;
    private ArrayList<Sun> suns;

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
