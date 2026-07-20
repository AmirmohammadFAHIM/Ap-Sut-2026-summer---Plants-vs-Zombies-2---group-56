package models.games;

import commands.GameCommands;
import controllers.Start.PlantSelection;
import controllers.dataController.SeedPackage;
import models.App;
import models.Constants;
import models.GameAdventure.*;
import models.factory.PlantFactory;
import models.factory.builder.PlantType;
import models.factory.builder.SunBuilder;
import models.gamePanes.Field;
import models.gamePanes.Tile;
import models.gamePanes.Wave;
import models.entity.Bullet;
import models.entity.Plant;
import models.entity.Sun;
import models.entity.Zombie;
import models.utils.Result;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BaseGame implements Game {
    public enum GameState{STARTING , PLAYING , PAUSE , END}
    protected GameState state =  GameState.STARTING;
    protected PlantSelection selection;
    protected int sunCount = 0;
    protected Field field ;
    protected ArrayList<Wave> waves;
    protected ArrayList<Plant> plants_inField;
    protected LinkedHashMap<PlantType , SeedPackage> available_plants;
    protected SunBuilder sunBuilder;
    protected Wave currentWave;
    protected Wave previousWave;
    protected ArrayList<Zombie> zombies; ///combination of current wave and next wave
    protected ArrayList<Bullet>  bullets;
    protected ArrayList<Sun> suns;
    protected GameCommands StartGameCommand;
    protected ChapterSpecialEvent event;
    protected PlantFactory plantFactory = new PlantFactory();

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
        SeedPackage selected_plant = selection.selectPlant(plantName);
        if(available_plants.containsKey(selected_plant.getPlant())) {
            return false;
        }
        else  {
            available_plants.put(selected_plant.getPlant(), selected_plant);
        }

        return available_plants.size() == Constants.Plants_count_in_a_game;
    }

    @Override
    public void playGame(float delta) {
            updatePlants(delta);
            updatePlants(delta);
            updateScene(delta);
            attack(delta);
            if(event!=null){
                event.run(this , delta);
            }


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
    public String plant(String plantName , int x , int y) {
        String name = plantName.replaceAll(" " , "_").toUpperCase();
        Result findPlant = plantAvailable(name);
        if(!findPlant.success()) return findPlant.message();
        else if(isEmpty(x, y)) return "The coordination is not empty or plantable.";
        Plant newPlant = plantFactory.CreatePlant(findPlant.plantType());
        plants_inField.add(newPlant);
        return "New plant : " + findPlant.plantType().name() + " planted successfully at coordination :" +
                " ( " + x + "," + y + ")";

    }
    private Result plantAvailable(String plantName) {
        try {
            PlantType type = PlantType.valueOf(plantName.toUpperCase());
            if(!available_plants.containsKey(type)) {
                return new Result(false , "The plant doesn't exist on the available plants.",null);
            }
            return new Result(true, null,type);

        } catch (IllegalArgumentException e) {
            return new Result(false , "The plant doesn't exist on the available plants.",null);
        }

    }

    private boolean isEmpty(int x , int y){
        Tile toPlantOn = field.getTiles().get(x).get(y);
        return toPlantOn.isEmpty() && toPlantOn.isPlantable();
    }

    @Override
    public String pluck(int x , int y) {
            Tile  toPluckOn = field.getTiles().get(x).get(y);
            for (Plant p : plants_inField){
                if(p.getLine() == y && p.getTileIndex() == x){
                    if(toPluckOn.isEmpty() && toPluckOn.isPlantable()
                            && toPluckOn.isWater()) continue; // This is a lily pad!
                    p.dispose(this);
                }
            }
            return "Bro don't pluck the plants ):";
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
        // TODO : Implement unlocking plants and updating user's progress(Notice the level is a new level or a passed one).

    }

    private int waveID = 0;
    private Result attack(float delta) {
        if(currentWave.isFinished()){
            previousWave = currentWave;
            currentWave = waves.get(waveID);
            zombies.addAll(currentWave.getZombies());
            waveID += 1;
           event = switch (App.getCurrentuser().getChapter()){
               case AncientEgypt -> new Tornado(this);
               case FrozenCaves -> new IcyWind(this);
               case BigWaveBeach -> new Water(this);
               default -> new GraveSpawner(this);
            };
           return new Result(true , setTheWaveZombies() , null);
        }
        return new  Result(false, null,null);
    }


    protected String setTheWaveZombies(){
        StringBuilder output = new StringBuilder();
        int line = 0;
        for (Zombie z : zombies) {
            z.setLine(line % 5);
            z.setTileIndex(8);
            z.setX(9 * Tile.getWidth() + 200);
            z.setY(line * Tile.getHeight());
            line++;
            output.append("Zombie spawned at line " + line + " , watch out human!\n");
        }
        return output.toString();
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

    public PlantSelection getSelection() {
        return selection;
    }

    public void setSelection(PlantSelection selection) {
        this.selection = selection;
    }

    public LinkedHashMap<PlantType, SeedPackage> getAvailable_plants() {
        return available_plants;
    }

    public void setAvailable_plants(LinkedHashMap<PlantType, SeedPackage> available_plants) {
        this.available_plants = available_plants;
    }

    public void setStartGameCommand(GameCommands startGameCommand) {
        StartGameCommand = startGameCommand;
    }

    public ChapterSpecialEvent getEvent() {
        return event;
    }

    public void setEvent(ChapterSpecialEvent event) {
        this.event = event;
    }
}
