package models.GameAdventure.levels;

import models.GameAdventure.Chapters;
import models.factory.builder.PlantType;

import java.util.ArrayList;

public class Level {
    private String levelType;
    private Chapters chapter;
    private ArrayList<PlantType> unlockingPlants;
    private int id;
    private boolean unlocked = false;
    private boolean won = false;
    private int waves;
    private float baseHardness;
    /// TODO: have a list of zombies names
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

    public Chapters getChapter() {
        return chapter;
    }

    public void setChapter(Chapters chapter) {
        this.chapter = chapter;
    }

    public ArrayList<PlantType> getUnlockingPlants() {
        return unlockingPlants;
    }

    public void setUnlockingPlants(ArrayList<PlantType> unlockingPlants) {
        this.unlockingPlants = unlockingPlants;
    }
}
