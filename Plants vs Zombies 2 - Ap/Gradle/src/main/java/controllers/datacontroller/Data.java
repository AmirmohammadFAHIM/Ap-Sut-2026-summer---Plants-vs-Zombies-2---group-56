package controllers.datacontroller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.tools.javac.Main;
import models.App;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.User;
import models.factory.builder.PlantType;
import view.HomeView;
import view.SignUpView;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public static void loadPlantsFromJson() {
        try (InputStream inputStream = Data.class.getResourceAsStream("/plants.json")) {

            if (inputStream == null) {
                System.err.println("خطا: فایل plants.json در پوشه resources پیدا نشد!");
                return;
            }

            try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

                Gson gson = new Gson();

                PlantData[] plants = gson.fromJson(reader, PlantData[].class);

                System.out.println(plants.length);
                if (plants != null) {
                    for (PlantData plant : plants) {
                        PlantType type = PlantType.valueOf(plant.getName());
                        Data.getPlants().put(type, plant);
                    }
                    System.out.println("دیتای گیاهان با موفقیت لود شد! تعداد: " + plants.length);
                } else {
                    System.out.println("فایل plants.json خالی است یا درست خوانده نشد.");
                    System.out.println(plants.length);
                }

            }
        } catch (Exception e) {
            System.err.println("یک مشکل غیرمنتظره در زمان خواندن فایل گیاهان رخ داد:");
            e.printStackTrace();
        }
    }

    public static void loadLevelsFromJson(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type levelListType = new TypeToken<List<Level>>() {}.getType();
            List<Level> levelsList = gson.fromJson(reader, levelListType);

            if (levelsList != null) {
                for (Chapters chapter : Chapters.values()) {
                    allLevels.put(chapter, new ArrayList<>());
                }

                for (Level level : levelsList) {
                    if (level.getUnlockingPlants() == null) level.setUnlockingPlants(new ArrayList<>());
                    if (level.getAllowedZombies() == null) level.setAllowedZombies(new ArrayList<>());

                    if (level.getChapters() != null) {
                        allLevels.get(level.getChapters()).add(level);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Something went wrong while reading levels.json \n " + e.getMessage());
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