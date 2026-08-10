package models.gameadventure;

import controllers.datacontroller.Data;
import models.gameadventure.levels.Level;
import java.io.Serializable;
import java.util.ArrayList;

public class Chapter implements Serializable {
    private Chapters chapterType;
    private ArrayList<Level> levels;

    public Chapter(Chapters chapterType) {
        this.chapterType = chapterType;
        this.levels = Data.getAllLevels().get(chapterType);

        if (this.levels == null) {
            this.levels = new ArrayList<>();
        }
    }

    public Chapters getChapterType() {
        return chapterType;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public Level getLevelById(int id) {
        for (Level level : levels) {
            if (level.getId() == id) {
                return level;
            }
        }
        return null;
    }
}