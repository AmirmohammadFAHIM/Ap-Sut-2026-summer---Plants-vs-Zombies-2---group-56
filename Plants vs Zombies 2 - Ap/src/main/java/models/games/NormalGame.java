package models.games;

import controllers.datacontroller.SeedPackage;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.entity.*;
import models.factory.builder.PlantType;
import models.gamePanes.Field;
import models.gamePanes.Tile;
import models.gamePanes.TileType;
import models.gamePanes.Wave;
import models.utils.Result;

import java.util.ArrayList;
import java.util.Iterator;

public class NormalGame extends BaseGame{

    @Override
    public void initGame(Chapters chapter , Level level) {
        waves = new  ArrayList<>();
        this.field = new Field().initField(chapter , level.getId());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Tile tile = field.getTileByCoordinats(j , i);
                System.out.println("coordinates (" + tile.getCol()+ ", " + tile.getLine() +
                        " )" + " , type : " + tile.getTileType());
            }
        }
        initWaves(level);
        //initTestWave();

    }



    private void initWaves(Level level){
        int wavesCount = level.getWaves();
        float baseCost = level.getBaseHardness();
        ArrayList<String>  zombies = level.getAllowedZombies();///filtered zombies for this level
        for (int i = 0; i < wavesCount - 1; i++) {
            Wave wave = new Wave();
            float lastCost ;
            try {
                lastCost = waves.getLast() == null ? baseCost : waves.getLast().getCost();
            } catch (Exception e){
                lastCost = baseCost;
            }
            wave.setId(i + 1);
            wave.setCost(lastCost * 1.25f);
            wave.initWave(zombies);
            waves.add(wave);
        }

        Wave finalWave = new Wave();
        finalWave.setId(wavesCount);
        finalWave.setCost(waves.getLast().getCost() * 2);
        finalWave.initWave(zombies);
    }


    @Override
    public String playGame(float delta) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()){
            Bullet bullet = iterator.next();
            bullet.run(delta , this);
            if(bullet.getPierce() <= 0){
                iterator.remove();
            }
        }
        mawners(delta);
        return super.playGame(delta);
    }

    @Override
    public String plant(String plantName, int x, int y) {
        String name = plantName.replaceAll(" " , "_").toUpperCase();
        Result findPlant = plantAvailable(name);
        if(!findPlant.success()){
            return findPlant.message();
        }
        try {
            if(available_plants.get(findPlant.plantType()).getCost() > sunCount){
                return "I hoped you wanna talk about business and " +
                        "you can't even effort a fuckin plant?";
            }
        }catch (Exception e){
            return "This plant , you haven't selected!\n - Yoda";
        }

            Plant newPlant = plantFactory.CreatePlant(findPlant.plantType());
        if(!isEmpty(newPlant ,x, y)) {
            return "The coordination is not empty or plantable.";
        }
        plantsInField.add(newPlant);
        Tile tile = field.getTiles().get(y).get(x);
        if(plantName.equals("LILY_PAD")){
            tile.setPlantable(true);
        } else {
            tile.setEmpty(false);
        }
        newPlant.setLine(y);
        newPlant.setTileIndex(x);
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

    protected boolean isEmpty(Plant type , int x , int y){
        boolean waterPlant = type.getTags().contains(PlantTags.WATER);
        Tile tile = field.getTileByCoordinats(x,y);
        Plant a = findByCoordinates(x,y);
        if(a != null && a.getType() == PlantType.PEA_POD &&
        type.getType() == PlantType.PEA_POD){
            return true;
        }
        else if(type.getType() == PlantType.GRAVE_BUSTER){
            return tile.getTileType() == TileType.EGYPTIAN_GRAVE ||
                    tile.getTileType() == TileType.DARK_AGE_GRAVE;
        }
        Tile toPlantOn = field.getTiles().get(x).get(y);
        boolean water = toPlantOn.isWater() || !waterPlant;


        return toPlantOn.isEmpty() &&
                (toPlantOn.isPlantable() || type.getArmor().isEmpty()
                        && type.getType() == PlantType.PUMPKIN ) && water;
    }


    @Override
    public String pluck(int x, int y) {
        Tile  toPluckOn = field.getTiles().get(x).get(y);
        for (Plant p : plantsInField){
            if(p.getLine() == y && p.getTileIndex() == x){
                if(toPluckOn.isEmpty() && toPluckOn.isPlantable()
                        && toPluckOn.isWater()) continue; // This is a lily pad!
                p.dispose(this);
            }
        }
        return "Bro don't pluck the plants ):";
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
           return "This plant is not on the list.";
       }
        if(available_plants.size() == 8) {
            plantSelection = true;
        }
        return "Plant added successfully";
    }


    public String nuke(){
        for (Zombie x : zombies){
            x.setHp(0);
            x.setAlive(false);
        }
        return "Booooom. zombies got nuked";
    }

    private void mawners(float delta){
        for (Mawner x : this.field.getMoaners()){
            String a = x.run(delta , this);
            if(a != null) output.append(a);
        }
    }
}
