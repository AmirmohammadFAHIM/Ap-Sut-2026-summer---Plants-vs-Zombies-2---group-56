package models.games;

import commands.GameCommands;
import controllers.Start.PlantSelection;
import models.Constants;
import models.factory.PlantFactory;
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
    protected GameState state =  GameState.STARTING;
    protected PlantSelection selection;
    protected int sunCount = 0;
    protected Field field ;
    protected ArrayList<Wave> waves;
    protected ArrayList<Plant> plants_inField;
    protected ArrayList<Plant> available_plants;
    protected SunBuilder sunBuilder;
    protected Wave currentWave;
    protected Wave previousWave;
    protected ArrayList<Zombie> zombies; ///combination of current wave and next wave
    protected ArrayList<Bullet>  bullets;
    protected ArrayList<Sun> suns;
    protected GameCommands StartGameCommand;

    public GameCommands getStartGameCommand() {
        return StartGameCommand;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

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
    public boolean startGame(String plantName) {
        /// TO DO: GET THE STRING , ADD THE PLANT TO THE AVAILABLE PLANTS , WHEN FULL , RETURN TRUE: MEANS WE ABOUT TO START
        Plant selected_plant = selection.selectPlant(plantName);
        if(available_plants.contains(selected_plant)) {
            return false;
        }
        else  {
            available_plants.add(selected_plant);
        }

        return available_plants.size() == Constants.Plants_count_in_a_game;
    }

    @Override
    public void playGame(float delta) {

    }

    @Override
    public void updatePlants(float delta) {

    }

    @Override
    public void updateZombies(float delta) {

    }

    @Override
    public void updateScene(float delta) {

    }

    @Override
    public void updateGame(float delta) {

    }

    @Override
    public void plant(String plantName , int x , int y) {

    }

    @Override
    public void dePlant(int x , int y) {

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

    public ArrayList<Plant> getPlants_inField() {
        return plants_inField;
    }

    public void setPlants_inField(ArrayList<Plant> plants_inField) {
        this.plants_inField = plants_inField;
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
