package controllers.datacontroller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.games.miniGames.MinigameLevel;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class MiniGameLevelManager {

    private static List<MinigameLevel> allLevels;

    public static void loadLevelsFromFile(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();

            Type listType = new TypeToken<List<MinigameLevel>>(){}.getType();
            allLevels = gson.fromJson(reader, listType);


        } catch (Exception e) {
            System.out.println("❌ exception in reading files " + e.getMessage());
        }
    }

    public static MinigameLevel getLevelById(int id) {
        if (allLevels == null) {
            throw new IllegalStateException("levels not loaded");
        }

        for (MinigameLevel level : allLevels) {
            if (level.getId() == id) {
                return level;
            }
        }
        return null;
    }
}