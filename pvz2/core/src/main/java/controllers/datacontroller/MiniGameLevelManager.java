package controllers.datacontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import models.games.minigames.MinigameLevel;

import java.util.ArrayList;
import java.util.List;

public class MiniGameLevelManager {

    private static List<MinigameLevel> allLevels;

    @SuppressWarnings("unchecked")
    public static void loadLevelsFromFile(String fileName) {
        // ۱. پیدا کردن فایل از پوشه assets (مثلاً "minigames.json")
        FileHandle file = Gdx.files.internal(fileName);

        if (!file.exists()) {
            // تو LibGDX به جای System.out از Gdx.app.error و log استفاده می‌کنیم
            Gdx.app.error("MiniGameLevelManager", "❌ File not found in assets: " + fileName);
            return;
        }

        try {
            // ۲. ساخت شیء Json (مال خود LibGDX)
            Json json = new Json();

            // ۳. این خط باعث میشه اگه تو فایل جیسون فیلد اضافه‌ای بود که تو کلاس جاوات نبود، کرش نکنه
            json.setIgnoreUnknownFields(true);

            // ۴. خواندن مستقیم لیست از فایل جیسون! (خیلی تمیزتر از Gson)
            allLevels = json.fromJson(ArrayList.class, MinigameLevel.class, file);

            Gdx.app.log("MiniGameLevelManager", "✅ Minigame Levels loaded successfully!");

        } catch (Exception e) {
            Gdx.app.error("MiniGameLevelManager", "❌ Exception in reading files: " + e.getMessage());
        }
    }

    public static MinigameLevel getLevelById(int id) {
        if (allLevels == null) throw new IllegalStateException("levels not loaded");
        for (MinigameLevel level : allLevels) {
            if (level.getId() == id) return level;
        }
        return null;
    }
}
