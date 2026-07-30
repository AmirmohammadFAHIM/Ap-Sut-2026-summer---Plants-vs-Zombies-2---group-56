package models.games;

import commands.GameCommands;
import controllers.Start.PlantSelection;
import controllers.datacontroller.SeedPackage;
import models.App;
import models.GameAdventure.*;
import models.entity.*;
import models.entity.ability.SunRobbingAbility;
import models.factory.PlantFactory;
import models.factory.ZombieFactory;
import models.factory.builder.PlantType;
import models.factory.builder.SunBuilder;
import models.gamePanes.*;
import models.utils.Result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;


public class BaseGame implements Game {
    public enum GameState{STARTING , PLAYING , PAUSE , END}
    protected GameState state =  GameState.STARTING;
    protected PlantSelection selection;
    protected int sunCount = 0;
    protected int plantFoodsCount = 0;
    GridController gridController;
    protected boolean day = true;

    public int getPlantFoodsCount() {
        return plantFoodsCount;
    }

    public void setPlantFoodsCount(int plantFoodsCount) {
        this.plantFoodsCount = plantFoodsCount;
    }



    protected Field field ;
    protected ArrayList<Wave> waves;
    protected ArrayList<Plant> plants_inField;
    protected LinkedHashMap<PlantType , SeedPackage> available_plants;
    protected SunBuilder sunBuilder = new  SunBuilder();
    protected Wave currentWave;
    protected Wave previousWave;
    protected ArrayList<Zombie> zombies; ///combination of current wave and next wave
    protected ArrayList<Bullet>  bullets;
    protected ArrayList<Sun> suns;
    protected GameCommands StartGameCommand;
    protected ChapterSpecialEvent event;
    protected PlantFactory plantFactory = new PlantFactory();


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
    public void initGame(Chapters chapter , int level) {

    }

    @Override
    public boolean startGame(String plantName) {
        return plantSelection;
    }

    protected boolean plantSelection = false;
    public String add(String name){
        return null;
    }

    @Override
    public String playGame(float delta) {
        StringBuilder output = new StringBuilder();
            Result sunlight = sunBuilder.sunLight(delta , this);
            if(sunlight != null){
                output.append(sunlight.message());
            }
            updatePlants(delta);
            updatePlants(delta);
            updateScene(delta);
            Result result = attack(delta);
            if(result != null){
                output.append(result.message());
            }
            if(event!=null){
                event.run(this , delta);
            }
            return output.toString();


    }

    @Override
    public void updatePlants(float delta) {
        for (Plant p : plants_inField) {
            p.update(delta, this);
        }
    }

    @Override
    public void updateZombies(float delta) {
        for (Zombie zombie : zombies) {
            zombie.update(delta, this);
        }

       // checkAndReleaseDeadWizards();

        for (Zombie zombie : zombies) {
            if (zombie.isDead()) {
                SunRobbingAbility sun = zombie.getAbility(SunRobbingAbility.class);
                if (sun != null && sun.getStolenSun() > 0) {
                    int released = sun.getStolenSun() / 2;
                    addSun(released);
                }
            }
        }

        zombies.removeIf(Zombie::isDead);
    }

    @Override
    public void updateScene(float delta) {
        field.updateScene(delta , this);
    }



    @Override
    public String plant(String plantName , int x , int y) {
        return null;

    }



    @Override
    public String pluck(int x , int y) {
            return null;
    }

   protected boolean won = false;
    public boolean isWon() {
        return won;
    }
    @Override
    public Result check_endGame() {
        for (Zombie z : zombies) {
            if(z.getX() <= 0) return new Result(true , "Loss" , null);
        }
        return new  Result(false, null,null);
    }

    @Override
    public void endGame() {

    }

    protected int waveID = 0;
    protected Result attack(float delta) {
        if(currentWave == null || currentWave.isFinished()){
            if(currentWave == waves.getLast()){
                won = true;
                return new Result(true , "Won" , null);
            }
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


    public ArrayList<Plant> getPlants_inField() {
        return plants_inField;
    }



    public Wave getPreviousWave() {
        return previousWave;
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


    public LinkedHashMap<PlantType, SeedPackage> getAvailable_plants() {
        return available_plants;
    }





    public void setEvent(ChapterSpecialEvent event) {
        this.event = event;
    }

    public Plant findByCoordinates(int x , int y){
        return null;
    }

    public Plant getPlantAt(int row, int col) {
        for (Plant p : plants_inField) {
            if (p.getLine() == row && p.getTileIndex() == col) {
                return p;
            }
        }
        return null;
    }

    public boolean isCellEmpty(int row, int col) {
        return getPlantAt(row, col) == null;
    }

    // ====== ZOMBIE ABILITIES ======
    public Zombie findNearestZombie(Zombie center, float range) {
        Zombie nearest = null;
        float minDist = Float.MAX_VALUE;
        for (Zombie z : zombies) {
            if (z == center) continue;
            float dx = z.getX() - center.getX();
            if (Math.abs(dx) <= range * 80 && z.getRow() == center.getRow()) {
                float dist = Math.abs(dx);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = z;
                }
            }
        }
        return nearest;
    }

    public Plant findTargetPlant(Zombie zombie, float range) {
        Plant nearest = null;
        float minDist = Float.MAX_VALUE;
        for (Plant p : plants_inField) {
            if (p.getLine() != zombie.getRow()) continue;
            float dx = p.getX() - zombie.getX();
            if (dx > 0 && dx <= range * 80) {
                if (dx < minDist) {
                    minDist = dx;
                    nearest = p;
                }
            }
        }
        return nearest;
    }

    public void explodeArea(int row, float x, float range, int damage) {
        for (Plant p : plants_inField) {
            if (p.getLine() != row) continue;
            float dx = Math.abs(p.getX() - x);
            if (dx <= range * 80) {
                p.setHP(0);
            }
        }
    }

    public void explodeAreaOnZombies(int row, float x, float range, int damage) {
        for (Zombie z : zombies) {
            if (z.getRow() != row) continue;
            float dx = Math.abs(z.getX() - x);
            if (dx <= range * 80) {
                z.takeDamage(damage);
            }
        }
    }

    public GridItem getPushableItemInFront(Zombie zombie) {
        int row = zombie.getRow();
        int col = zombie.getTileIndex() + 1;
        if (col >= 9) return null;
        return gridController.getGridItem(row, col);
    }

    public void pushItem(Zombie zombie, GridItem item) {
        item.setCol(item.getCol() + 1);
        if (item.getCol() >= 9) {
            gridController.removeGridItem(item);
        }
    }

    public Plant findPullablePlant(Zombie zombie) {
        int row = zombie.getRow();
        int col = zombie.getTileIndex();
        for (int i = 2; i <= 8; i++) {
            int targetCol = col + i;
            if (targetCol >= 9) break;
            Plant p = getPlantAt(row, targetCol);
            if (p != null && p.getHp() > 0) return p;
        }
        return null;
    }

    public void pullPlant(Zombie zombie, Plant plant) {
        int col = plant.getTileIndex();
        if (isCellEmpty(plant.getLine(), col - 1)) {
            plant.setTileIndex(col - 1);
        }
    }

    public void pullZombie(Zombie source, Zombie target) {
        float dx = target.getX() - source.getX();
        if (dx > 80) {
            target.setPosition(target.getX() - 80, target.getY());
        }
    }

    public void swapZombieToRow(Zombie target, int row) {
        target.setRow(row);
    }

    public Plant getRandomPlantInRange(Zombie zombie, float range) {
        List<Plant> candidates = new ArrayList<>();
        for (Plant p : plants_inField) {
            if (p.getLine() != zombie.getRow()) continue;
            float dx = p.getX() - zombie.getX();
            if (dx > 0 && dx <= range * 80) {
                candidates.add(p);
            }
        }
        if (candidates.isEmpty()) return null;
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    public Zombie getRandomZombieInRange(Zombie center, float range) {
        List<Zombie> candidates = new ArrayList<>();
        for (Zombie z : zombies) {
            if (z == center) continue;
            float dx = z.getX() - center.getX();
            if (Math.abs(dx) <= range * 80 && z.getRow() == center.getRow()) {
                candidates.add(z);
            }
        }
        if (candidates.isEmpty()) return null;
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    public Zombie getRandomZombie() {
        if (zombies.isEmpty()) return null;
        return zombies.get(new Random().nextInt(zombies.size()));
    }

    public void spawnReverseZombie(int row) {
        Zombie z = ZombieFactory.createZombie("normal");
        z.setRow(row);
        z.setPosition(50, row * 100 + 50);
        z.setHypnotized(true);
        zombies.add(z);
    }

    public void spawn(Zombie source, String spawnType, int count) {
        if (spawnType.equals("imp")) {
            for (int i = 0; i < count; i++) {
                Zombie imp = ZombieFactory.createZombie("imp");
                imp.setRow(source.getRow());
                imp.setPosition(source.getX() + 50, source.getY());
                zombies.add(imp);
            }
        } else if (spawnType.equals("grave")) {
            int row = source.getRow();
            int col = source.getTileIndex() + 1;
            if (col < 9 && isCellEmpty(row, col)) {
                spawnGrave(row, col);
            }
        }
    }

    public void spawnGrave(int row, int col) {
        GridItem grave = new GridItem("grave", row, col, 700, false, true);
        gridController.addGridItem(grave);

        Field field = getField();
        if (field != null) {
            Tile tile = field.getTiles().get(row).get(col);
            if (tile != null) {
                tile.setBlock(true);
                tile.setPlantable(false);
                tile.setTileType(TileType.DARK_AGE_GRAVE);
            }
        }
    }

    public boolean hasKilledPlant(Zombie zombie) {
        return zombie.getAllStarObserver() != null &&
                zombie.getAllStarObserver().isSlowed();
    }

    public boolean isArmorBroken(Zombie zombie, String armorType) {
        for (Armor armor : zombie.getArmors()) {
            if (armor.getType().equals(armorType) && armor.isBroken()) {
                return true;
            }
        }
        return false;
    }

    public void removeSun(int amount) {
        sunCount -= amount;
        if (sunCount < 0) sunCount = 0;
    }

    public void addSun(int amount) {
        sunCount += amount;
    }




    public String formatZombieInfo(Zombie zombie) {
        StringBuilder sb = new StringBuilder();
        sb.append(zombie.getType()).append(": ");

        int col = zombie.getTileIndex();
        int row = zombie.getRow();
        sb.append("position: (").append(row).append(", ").append(col).append(")  ");
        sb.append("health: ").append(zombie.getHp());

        List<Armor> armors = zombie.getArmors();
        if (!armors.isEmpty()) {
            sb.append("  armors: ");
            for (int i = 0; i < armors.size(); i++) {
                Armor armor = armors.get(i);
                sb.append(armor.getType()).append(": ").append(armor.getHealth());
                if (i < armors.size() - 1) sb.append(", ");
            }
        }


        return "";
    }
}
