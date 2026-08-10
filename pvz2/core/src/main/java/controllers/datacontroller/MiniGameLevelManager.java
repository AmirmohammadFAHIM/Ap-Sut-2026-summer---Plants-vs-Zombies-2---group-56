package controllers.datacontroller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.games.minigames.MinigameLevel;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MiniGameLevelManager {

    private static List<MinigameLevel> allLevels;

    public static void loadLevelsFromFile(String fileName) {
        try (InputStream inputStream = MiniGameLevelManager.class
                .getClassLoader().getResourceAsStream(fileName)) {

            if (inputStream == null) {
                System.out.println("❌ File not found in resources: " + fileName);
                return;
            }

            try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<MinigameLevel>>(){}.getType();
                allLevels = gson.fromJson(reader, listType);
            }

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