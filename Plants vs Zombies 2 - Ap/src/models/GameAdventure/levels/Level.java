package models.GameAdventure.levels;

import models.GameAdventure.Chapters;
import models.factory.builder.PlantType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Level implements Serializable {
    private String levelType;
    private Chapters chapters;
    private int id;
    private boolean unlocked = false;
    private boolean won = false;
    private int waves;
    private float baseHardness;
    private ArrayList<String> allowedZombies;
    private ArrayList<PlantType> unlockingPlants;

    public ArrayList<PlantType> getUnlockingPlants() {
        return unlockingPlants;
    }

    public Level(int id, Chapters chapters, String levelType, boolean unlocked, int waves, float baseHardness) {
        this.id = id;
        this.chapters = chapters;
        this.levelType = levelType;
        this.unlocked = unlocked;
        this.waves = waves;
        this.baseHardness = baseHardness;
        this.allowedZombies = new ArrayList<>();
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public int getWaves() {
        return waves;
    }

    public void setWaves(int waves) {
        this.waves = waves;
    }

    public float getBaseHardness() {
        return baseHardness;
    }

    public void setBaseHardness(float baseHardness) {
        this.baseHardness = baseHardness;
    }

    public Chapters getChapters() {
        return chapters;
    }

    public void setChapters(Chapters chapters) {
        this.chapters = chapters;
    }

    public ArrayList<String> getAllowedZombies() {
        return allowedZombies;
    }

    public void setAllowedZombies(ArrayList<String> allowedZombies) {
        this.allowedZombies = allowedZombies;
    }
}