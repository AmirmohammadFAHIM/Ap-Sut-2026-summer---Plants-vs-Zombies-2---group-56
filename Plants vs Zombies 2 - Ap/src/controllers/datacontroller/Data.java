package controllers.datacontroller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.App;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.User;
import models.factory.builder.PlantType;
import view.HomeView;
import view.SignUpView;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            allUsers = (ArrayList<User>) ois.readObject();
        } catch (Exception e) {
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
                }
            }
        }
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

    public static void loadPlantsFromJson(String filePath) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            Type plantListType = new TypeToken<List<PlantData>>() {}.getType();
            List<PlantData> plantsList = gson.fromJson(reader, plantListType);

            if (plantsList != null) {
                for (PlantData plant : plantsList) {
                    PlantType type = PlantType.valueOf(plant.getName());
                    plants.put(type, plant);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Something went wrong while reading plants data file. \n " + e.getMessage());
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

                    allLevels.get(level.getChapters()).add(level);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Something went wrong while reading levels data file. \n " + e.getMessage());
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