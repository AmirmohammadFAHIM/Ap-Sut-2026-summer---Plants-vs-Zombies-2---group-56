package controllers.dataController;

import models.User;

import java.io.*;
import java.util.ArrayList;

public class Data {
    private static final String USERS_FILE = "users_data.dat";
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User currentUser = null;
    private static User tempUser = null;

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

    public static void setCurrentUser(User user) { currentUser = user; }
    public static User getCurrentUser() { return currentUser; }
    public static void setTempUser(User user) { tempUser = user; }
    public static User getTempUser() { return tempUser; }
    public static ArrayList<User> getAllUsers() { return allUsers; }

    public void saveGame() { }
    public void deserializeGame() { }
}