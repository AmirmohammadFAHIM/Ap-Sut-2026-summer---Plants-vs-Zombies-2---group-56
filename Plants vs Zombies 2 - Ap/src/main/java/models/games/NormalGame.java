package models.games;

import controllers.datacontroller.SeedPackage;
import models.GameAdventure.Chapters;
import models.entity.Plant;
import models.entity.PlantTags;
import models.entity.Sun;
import models.entity.Zombie;
import models.factory.builder.PlantType;
import models.gamePanes.Field;
import models.gamePanes.Tile;
import models.gamePanes.Wave;
import models.utils.Result;

import java.util.ArrayList;

public class NormalGame extends BaseGame{

    @Override
    public void initGame(Chapters chapter , int level) {
        this.field = new Field().initField(chapter , level);
        initWaves();

    }

    private void initWaves(){
        int wavesCount = 0; ///get it from the file
        float baseCost = 0;///get it from the file
        ArrayList<Zombie>  zombies = new ArrayList<>();///filtered zombies for this level
        for (int i = 0; i < wavesCount - 1; i++) {
            Wave wave = new Wave();
            float lastCost = waves.getLast() == null ? baseCost : waves.getLast().getCost();
            wave.setCost(lastCost * 1.25f);
            wave.initWave(zombies);
            waves.add(wave);
        }

        Wave finalWave = new Wave();
        finalWave.setCost(waves.getLast().getCost() * 2);
        finalWave.initWave(zombies);
    }

    public void updateSuns(float delta){
        for (Sun sun : suns){
            if(sun.getProducer() == null) sun.setRemainingTime(sun.getRemainingTime() - delta);
        }
    }

    @Override
    public String plant(String plantName, int x, int y) {
        String name = plantName.replaceAll(" " , "_").toUpperCase();
        Result findPlant = plantAvailable(name);
        if(!findPlant.success()) return findPlant.message();
        Plant newPlant = plantFactory.CreatePlant(findPlant.plantType());
        if(isEmpty(newPlant.getTags().contains(PlantTags.WATER) ,x, y)) return "The coordination is not empty or plantable.";
        plants_inField.add(newPlant);
        Tile tile = field.getTiles().get(y).get(x);
        if(plantName.equals("LILY_PAD")){
            tile.setPlantable(true);
        } else {
            tile.setEmpty(true);
        }
        this.sunCount -= (int) available_plants.get(findPlant.plantType()).getCost();
        return "New plant : " + findPlant.plantType().name() + " planted successfully at coordination :" +
                " ( " + x + "," + y + ")";
    }

    protected Result plantAvailable(String plantName) {
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

    protected boolean isEmpty(boolean waterPlant , int x , int y){
        Tile toPlantOn = field.getTiles().get(x).get(y);
        boolean water = toPlantOn.isWater() || !waterPlant;
        return toPlantOn.isEmpty() && toPlantOn.isPlantable() && water;
    }


    @Override
    public String pluck(int x, int y) {
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
    public Plant findByCoordinates(int x, int y) {
        for (Plant p : this.plants_inField){
            if(p.getLine() == y && p.getTileIndex() == x){
                return p;
            }
        }
        return null;
    }

    @Override
    public String add(String name) {
        if(available_plants.size() == 8) return "Impossible. Slots are full";
        if(name.equalsIgnoreCase("Imitater")) name = available_plants.lastEntry().getKey().name();
        SeedPackage seedPackage = selection.selectPlant(name);
       if(seedPackage != null){
           available_plants.put(seedPackage.getPlant(), seedPackage);
       }
       else {
           return "Nah bro there ain't no shit like this plant.";
       }
        if(available_plants.size() == 8) {
            plantSelection = true;
        }
        return "Plant added successfully";
    }
}
