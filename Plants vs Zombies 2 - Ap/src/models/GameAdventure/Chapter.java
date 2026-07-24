package models.GameAdventure;

import models.GameAdventure.levels.Level;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Chapter implements Serializable {
    private ArrayList<Level> levels = new ArrayList<>(4);
    private Chapters chapterType;

    public Chapter(Chapters chapterType) {
        this.chapterType = chapterType;
        initLevels();
    }

    private void initLevels() {
        boolean isFirstUnlocked = (this.chapterType == Chapters.AncientEgypt);

        Level level1 = new Level(1, this.chapterType, "Normal", isFirstUnlocked, 3, 1.0f);
        level1.setAllowedZombies(new ArrayList<>(Arrays.asList("NormalZombie")));

        Level level2 = new Level(2, this.chapterType, "Special", false, 4, 1.5f);
        level2.setAllowedZombies(new ArrayList<>(Arrays.asList("NormalZombie", "ConeHeadZombie")));

        Level level3 = new Level(3, this.chapterType, "Special", false, 5, 2.0f);
        level3.setAllowedZombies(new ArrayList<>(Arrays.asList("NormalZombie", "ConeHeadZombie", "BucketHeadZombie")));

        Level level4 = new Level(4, this.chapterType, "Boss", false, 1, 5.0f);
        level4.setAllowedZombies(new ArrayList<>(Arrays.asList("Gargantuar")));

        this.levels.add(level1);
        this.levels.add(level2);
        this.levels.add(level3);
        this.levels.add(level4);
    }

    public void unlockNextLevel() {
        for (int i = 0; i < levels.size() - 1; i++) {
            Level currentLevel = levels.get(i);
            Level nextLevel = levels.get(i + 1);

            if (currentLevel.isWon() && !nextLevel.isUnlocked()) {
                nextLevel.setUnlocked(true);
                System.out.println("Level " + nextLevel.getId() + " in " + chapterType.name() + " is now unlocked!");
                return;
            }
        }
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public Chapters getChapterType() {
        return chapterType;
    }

    public void setChapterType(Chapters chapterType) {
        this.chapterType = chapterType;
    }
}