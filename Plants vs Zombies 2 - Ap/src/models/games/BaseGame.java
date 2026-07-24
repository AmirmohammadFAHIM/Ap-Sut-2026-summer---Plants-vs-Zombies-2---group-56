package models.games;

import commands.GameCommands;
import controllers.Start.PlantSelection;
import controllers.datacontroller.SeedPackage;
import controllers.observer.*;
import models.App;
import models.Constants;
import models.GameAdventure.*;
import models.collection.ZombieRegistry;
import models.entity.*;
import models.factory.*;
import models.factory.builder.PlantType;
import models.factory.builder.SunBuilder;
import models.gamePanes.*;
import models.utils.Result;
import models.entity.ability.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.*;

public class BaseGame implements Game {
    public enum GameState{STARTING, PLAYING, PAUSE, END}
    protected GameState state = GameState.STARTING;
    protected PlantSelection selection;
    protected int sunCount = 0;
    protected int plantFoodsCount = 0;

    protected Field field;
    protected ArrayList<Wave> waves;
    protected ArrayList<Plant> plants_inField;
    protected LinkedHashMap<PlantType, SeedPackage> available_plants;
    protected SunBuilder sunBuilder;
    protected Wave currentWave;
    protected Wave previousWave;
    protected ArrayList<Zombie> zombies;
    protected ArrayList<Bullet> bullets;
    protected ArrayList<Sun> suns;
    protected GameCommands StartGameCommand;
    protected ChapterSpecialEvent event;
    protected PlantFactory plantFactory = new PlantFactory();

    // ====== GRID CONTROLLER ======
    private final GridController gridController = new GridController();

    // ====== WIZARD OBSERVER ======
    private final WizardObserver wizardObserver = new WizardObserver();

    // ====== GETTERS & SETTERS ======
    public GridController getGridController() { return gridController; }
    public int getPlantFoodsCount() { return plantFoodsCount; }
    public void setPlantFoodsCount(int plantFoodsCount) { this.plantFoodsCount = plantFoodsCount; }
    public PlantFactory getPlantFactory() { return plantFactory; }
    public void setPlantFactory(PlantFactory plantFactory) { this.plantFactory = plantFactory; }
    public int getWaveID() { return waveID; }
    public void setWaveID(int waveID) { this.waveID = waveID; }
    public GameCommands getStartGameCommand() { return StartGameCommand; }
    public GameState getState() { return state; }
    public void setState(GameState state) { this.state = state; }
    public int getSunCount() { return sunCount; }
    public void setSunCount(int sunCount) { this.sunCount = sunCount; }
    public ArrayList<Bullet> getBullets() { return bullets; }
    public ArrayList<Sun> getSuns() { return suns; }
    public Wave getCurrentWave() { return currentWave; }
    public Field getField() { return field; }
    public void setField(Field field) { this.field = field; }
    public ArrayList<Wave> getWaves() { return waves; }
    public void setWaves(ArrayList<Wave> waves) { this.waves = waves; }
    public ArrayList<Plant> getPlants_inField() { return plants_inField; }
    public void setPlants_inField(ArrayList<Plant> plants_inField) { this.plants_inField = plants_inField; }
    public SunBuilder getSunBuilder() { return sunBuilder; }
    public void setSunBuilder(SunBuilder sunBuilder) { this.sunBuilder = sunBuilder; }
    public void setCurrentWave(Wave currentWave) { this.currentWave = currentWave; }
    public Wave getPreviousWave() { return previousWave; }
    public void setPreviousWave(Wave previousWave) { this.previousWave = previousWave; }
    public void setBullets(ArrayList<Bullet> bullets) { this.bullets = bullets; }
    public void setSuns(ArrayList<Sun> suns) { this.suns = suns; }
    public ArrayList<Zombie> getZombies() { return zombies; }
    public void setZombies(ArrayList<Zombie> zombies) { this.zombies = zombies; }
    public PlantSelection getSelection() { return selection; }
    public void setSelection(PlantSelection selection) { this.selection = selection; }
    public LinkedHashMap<PlantType, SeedPackage> getAvailable_plants() { return available_plants; }
    public void setAvailable_plants(LinkedHashMap<PlantType, SeedPackage> available_plants) { this.available_plants = available_plants; }
    public void setStartGameCommand(GameCommands startGameCommand) { StartGameCommand = startGameCommand; }
    public ChapterSpecialEvent getEvent() { return event; }
    public void setEvent(ChapterSpecialEvent event) { this.event = event; }

    @Override
    public void initGame() {}

    @Override
    public boolean startGame(String plantName) {
        SeedPackage selected_plant = selection.selectPlant(plantName);
        if (available_plants.containsKey(selected_plant.getPlant())) {
            return false;
        } else {
            available_plants.put(selected_plant.getPlant(), selected_plant);
        }
        return available_plants.size() == Constants.Plants_count_in_a_game;
    }

    @Override
    public void playGame(float delta) {
        updatePlants(delta);
        updateZombies(delta);
        updateScene(delta);
        Result result = attack(delta);
        if (event != null) {
            event.run(this, delta);
        }
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

        checkAndReleaseDeadWizards();

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
    public void updateScene(float delta) {}

    @Override
    public String plant(String plantName, int x, int y) {
        String name = plantName.replaceAll(" ", "_").toUpperCase();
        Result findPlant = plantAvailable(name);
        if (!findPlant.success()) return findPlant.message();
        Plant newPlant = plantFactory.CreatePlant(findPlant.plantType());
        if (isEmpty(newPlant.getTags().contains(PlantTags.WATER), x, y)) {
            return "The coordination is not empty or plantable.";
        }
        plants_inField.add(newPlant);
        Tile tile = field.getTiles().get(y).get(x);
        if (plantName.equals("LILY_PAD")) {
            tile.setPlantable(true);
        } else {
            tile.setEmpty(true);
        }
        this.sunCount -= (int) available_plants.get(findPlant.plantType()).getCost();
        return "New plant : " + findPlant.plantType().name() + " planted successfully at coordination : ( " + x + "," + y + ")";
    }

    private Result plantAvailable(String plantName) {
        try {
            PlantType type = PlantType.valueOf(plantName.toUpperCase());
            if (!available_plants.containsKey(type)) {
                return new Result(false, "The plant doesn't exist on the available plants.", null);
            }
            return new Result(true, null, type);
        } catch (IllegalArgumentException e) {
            return new Result(false, "The plant doesn't exist on the available plants.", null);
        }
    }

    private boolean isEmpty(boolean waterPlant, int x, int y) {
        Tile toPlantOn = field.getTiles().get(x).get(y);
        boolean water = toPlantOn.isWater() || !waterPlant;
        return toPlantOn.isEmpty() && toPlantOn.isPlantable() && water;
    }

    @Override
    public String pluck(int x, int y) {
        Tile toPluckOn = field.getTiles().get(x).get(y);
        for (Plant p : plants_inField) {
            if (p.getLine() == y && p.getTileIndex() == x) {
                if (toPluckOn.isEmpty() && toPluckOn.isPlantable() && toPluckOn.isWater()) continue;
                p.dispose(this);
            }
        }
        return "Bro don't pluck the plants ):";
    }

    protected boolean won = false;
    public boolean isWon() { return won; }

    @Override
    public Result check_endGame() {
        for (Zombie z : zombies) {
            if (z.getX() <= 0) return new Result(true, "Loss", null);
        }
        return new Result(false, null, null);
    }

    @Override
    public void endGame() {}

    private int waveID = 0;

    private Result attack(float delta) {
        if (currentWave.isFinished()) {
            if (currentWave == waves.getLast()) {
                won = true;
                return new Result(true, "Won", null);
            }
            previousWave = currentWave;
            currentWave = waves.get(waveID);
            zombies.addAll(currentWave.getZombies());
            waveID += 1;
            event = switch (App.getCurrentuser().getChapter()) {
                case AncientEgypt -> new Tornado(this);
                case FrozenCaves -> new IcyWind(this);
                case BigWaveBeach -> new Water(this);
                default -> new GraveSpawner(this);
            };
            return new Result(true, setTheWaveZombies(), null);
        }
        return new Result(false, null, null);
    }

    protected String setTheWaveZombies() {
        StringBuilder output = new StringBuilder();
        int line = 0;
        for (Zombie z : zombies) {
            z.setRow(line % 5);
            z.setTileIndex(8);
            z.setX((int)(9 * Tile.getWidth() + 200));
            z.setY((int)(line * Tile.getHeight()));
            line++;
            output.append("Zombie spawned at line ").append(line).append(" , watch out human!\n");
        }
        return output.toString();
    }

    // ====== WIZARD OBSERVER ======
    public void addCat(Zombie wizard, Plant plant) {
        wizardObserver.addCat(wizard, plant);
    }

    public void checkAndReleaseDeadWizards() {
        wizardObserver.checkAndReleaseDeadWizards();
    }

    // ====== PLANT HELPERS ======
    public Plant findByCoordinates(int x, int y) {
        for (Plant p : this.plants_inField) {
            if (p.getLine() == y && p.getTileIndex() == x) {
                return p;
            }
        }
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


    public String showAllZombies() {
        if (zombies.isEmpty()) {
            return "No active zombies in the game.";
        }
        StringBuilder sb = new StringBuilder("--- Active Zombies ---\n");
        for (Zombie z : zombies) {
            sb.append(formatZombieInfo(z)).append("\n");
        }
        return sb.toString().trim();
    }

    public String showZombie(String zombieName) {
        Zombie target = null;
        for (Zombie z : zombies) {
            if (z.getId().equalsIgnoreCase(zombieName) || z.getType().equalsIgnoreCase(zombieName)) {
                target = z;
                break;
            }
        }
        if (target == null) {
            return "Zombie \"" + zombieName + "\" not found in the current game.";
        }
        return formatZombieInfo(target);
    }

    private String formatZombieInfo(Zombie zombie) {
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

        List<Effect> effects = zombie.getEffects();
        if (!effects.isEmpty()) {
            sb.append("  effects: ");
            for (int i = 0; i < effects.size(); i++) {
                Effect effect = effects.get(i);
                sb.append(effect.getType().name().toLowerCase());
                float remaining = effect.getRemainingTime();
                if (remaining > 0) {
                    sb.append(": ").append(String.format("%.1f", remaining)).append("s");
                }
                if (i < effects.size() - 1) sb.append(", ");
            }
        }

        return sb.toString();
    }
}