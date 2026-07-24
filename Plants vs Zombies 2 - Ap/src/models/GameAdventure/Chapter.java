package models.GameAdventure;

import controllers.datacontroller.Data;
import models.GameAdventure.levels.Level;
import java.io.Serializable;
import java.util.ArrayList;

public class Chapter implements Serializable {
    private ArrayList<Level> levels;
    private Chapters chapterType;

    public Chapter(Chapters chapterType) {
        this.chapterType = chapterType;
        this.levels = Data.getAllLevels().get(chapterType);

        if (this.levels == null) {
            this.levels = new ArrayList<>();
        }
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