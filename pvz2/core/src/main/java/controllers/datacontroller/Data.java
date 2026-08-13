package controllers.datacontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import models.*;
import models.gameadventure.*;
import models.gameadventure.levels.*;
import models.*;
import models.factory.builder.*;
import view.*;
import view.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private static final String USERS_FILE = "users_data.dat";
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User currentUser = null;
    private static User tempUser = null;
    private static HashMap<PlantType , PlantData> plants = new HashMap<>();
    private static HashMap<Chapters, ArrayList<Level>> allLevels = new HashMap<>();

    public static void saveUser() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(allUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void deserializeUser() {
        File userFile = new File(USERS_FILE);

        if (!userFile.exists()) {
            allUsers = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {
            allUsers = (ArrayList<User>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Warning: Could not load user data or file is corrupted. Starting fresh.");
            allUsers = new ArrayList<>();
        }
    }

    public static void setUp(){
        if(allUsers.isEmpty()) App.setScreen(new SignUpView());
        else{
            for (User user : allUsers) {
                if(user.isStayLoggedIn()){
                    currentUser = user;
                    App.setScreen(new HomeView());
                    return;
                }
            }
        }
        App.setScreen(new SignUpView());
    }

    public static void addUser(User user) {
        allUsers.add(user);
        saveUser();
    }

    public static boolean isUsernameExists(String username) {
        for (User user : allUsers) {
            if (user.getName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }




// ... بقیه کدهای کلاس Data ...

    public static void loadPlantsFromJson() {
        FileHandle file = Gdx.files.internal("plants.json"); // فایل حتماً تو پوشه assets باشه

        if (!file.exists()) {
            Gdx.app.error("Data", "❌ plants.json not found in assets folder!");
            return;
        }

        try {
            Json json = new Json();
            json.setIgnoreUnknownFields(true);

            // اول جیسون رو به عنوان یه آرایه (لیست) می‌خونیم
            ArrayList<PlantData> plantsList = json.fromJson(ArrayList.class, PlantData.class, file);

            // حالا تبدیلش می‌کنیم به HashMap که خودت تعریف کرده بودی
            plants = new HashMap<>();
            for (PlantData plant : plantsList) {
                // فرض کردم متدی مثل getType() برای گرفتن نوع گیاه (Enum) توی PlantData داری
                plants.put(PlantType.valueOf(plant.getName()), plant);
            }

            Gdx.app.log("Data", "✅ Plants loaded successfully!");
        } catch (Exception e) {
            Gdx.app.error("Data", "❌ Error reading plants: " + e.getMessage());
        }
    }


    public static void loadLevelsFromJson() {
        FileHandle file = Gdx.files.internal("levels.json"); // فایل حتماً تو پوشه assets باشه

        if (!file.exists()) {
            Gdx.app.error("Data", "❌ levels.json not found in assets folder!");
            return;
        }

        try {
            Json json = new Json();
            json.setIgnoreUnknownFields(true);

            // اول جیسون رو به عنوان یه آرایه (لیست) از Level ها می‌خونیم
            ArrayList<Level> levelsList = json.fromJson(ArrayList.class, Level.class, file);

            // هش‌مپ allLevels رو ریست می‌کنیم تا دیتای تکراری اضافه نشه
            allLevels = new HashMap<>();

            for (Level level : levelsList) {
                // فرض بر این است که متدی برای گرفتن چپتر این مرحله داری
                Chapters chapter = level.getChapter();

                // اگر این چپتر هنوز توی مپ ساخته نشده، یه لیست خالی براش می‌سازیم
                allLevels.putIfAbsent(chapter, new ArrayList<Level>());

                // مرحله رو به لیستِ همون چپتر اضافه می‌کنیم
                allLevels.get(chapter).add(level);
            }

            Gdx.app.log("Data", "✅ Levels loaded successfully! Total chapters: " + allLevels.size());
        } catch (Exception e) {
            Gdx.app.error("Data", "❌ Error reading levels: " + e.getMessage());
        }
    }
    public static HashMap<Chapters, ArrayList<Level>> getAllLevels() {
        return allLevels;
    }

    public static void setCurrentUser(User user) { currentUser = user; }
    public static User getCurrentUser() { return currentUser; }
    public static void setTempUser(User user) { tempUser = user; }
    public static User getTempUser() { return tempUser; }
    public static ArrayList<User> getAllUsers() { return allUsers; }

    public static HashMap<PlantType, PlantData> getPlants() {
        return plants;
    }

    public static void setPlants(HashMap<PlantType, PlantData> plants) {
        Data.plants = plants;
    }

    public void saveGame() { }
    public void deserializeGame() { }
}
