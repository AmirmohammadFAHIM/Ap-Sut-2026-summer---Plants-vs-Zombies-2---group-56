package models;

import models.GameAdventure.Chapter;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.entity.Plant;
import models.entity.Zombie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String nickname;
    private String email;
    private String passwordHash;
    private String gender;
    private int securityQuestionNumber;
    private String securityAnswer;
    private Chapters chapter;
    private Level level;
    private ArrayList<Zombie> zombies;
    private ArrayList<Plant> plants;
    private ArrayList<models.GameAdventure.Chapter> userChapters;
    private int coins = 0;
    private int diamonds = 0;
    private int highestScore = 0;
    private int gamesPlayed = 0;
    private int levelsPassed = 0;
    private int difficultyLevel = 3;
    private boolean isStayLoggedIn = false;
    private int unlockedPots = 5;
    private int plantFoods = 0;
    private int randomSeeds = 0;
    private String lastDailyPurchaseDate = "";
    private HashMap<String, Integer> specificSeeds;
    private ArrayList<String> unlockedPlantsNames;
    private ArrayList<String> unreadNews;
    private ArrayList<String> readNews;

    public User(String name, String passwordHash, String nickname, String email, String gender) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.zombies = new ArrayList<>();
        this.plants = new ArrayList<>();
        this.specificSeeds = new HashMap<>();
        this.unlockedPlantsNames = new ArrayList<>();
        this.unreadNews = new ArrayList<>();
        this.readNews = new ArrayList<>();
        this.userChapters = new ArrayList<>();
        for (models.GameAdventure.Chapters type : models.GameAdventure.Chapters.values()) {
            this.userChapters.add(new models.GameAdventure.Chapter(type));
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGender() { return gender; }
    public void setSecurityQuestion(int questionNumber, String answer) {
        this.securityQuestionNumber = questionNumber;
        this.securityAnswer = answer;
    }
    public int getSecurityQuestionNumber() { return securityQuestionNumber; }
    public boolean checkSecurityAnswer(String answer) { return this.securityAnswer.equals(answer); }

    public int getCoins() { return coins; }
    public void addCoins(int amount) { this.coins += amount; }
    public int getDiamonds() { return diamonds; }
    public void addDiamonds(int amount) { this.diamonds += amount; }

    public int getHighestScore() { return highestScore; }
    public void setHighestScore(int score) {
        if (score > this.highestScore) { this.highestScore = score; }
    }

    public int getGamesPlayed() { return gamesPlayed; }
    public void incrementGamesPlayed() { this.gamesPlayed++; }
    public int getLevelsPassed() { return levelsPassed; }
    public void incrementLevelsPassed() { this.levelsPassed++; }
    public int getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(int difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public boolean isStayLoggedIn() { return isStayLoggedIn; }
    public void setStayLoggedIn(boolean stayLoggedIn) { this.isStayLoggedIn = stayLoggedIn; }

    public int getUnlockedPots() { return unlockedPots; }
    public void addUnlockedPots(int amount) { this.unlockedPots += amount; }
    public int getPlantFoods() { return plantFoods; }
    public void addPlantFoods(int amount) { this.plantFoods += amount; }
    public int getRandomSeeds() { return randomSeeds; }
    public void addRandomSeeds(int amount) { this.randomSeeds += amount; }
    public String getLastDailyPurchaseDate() { return lastDailyPurchaseDate; }
    public void setLastDailyPurchaseDate(String date) { this.lastDailyPurchaseDate = date; }

    public Chapters getChapter() {
        return chapter;
    }

    public void addSpecificSeed(String plantType, int amount) {
        if (this.specificSeeds == null) this.specificSeeds = new HashMap<>();
        this.specificSeeds.put(plantType, this.specificSeeds.getOrDefault(plantType, 0) + amount);
    }
    public int getSpecificSeedCount(String plantType) {
        return this.specificSeeds != null ? this.specificSeeds.getOrDefault(plantType, 0) : 0;
    }

    public ArrayList<String> getUnlockedPlantsNames() { return unlockedPlantsNames; }
    public ArrayList<String> getUnreadNews() { return unreadNews; }
    public ArrayList<String> getReadNews() { return readNews; }

    public void updateProgress() { }

    public ArrayList<models.GameAdventure.Chapter> getUserChapters() {
        return userChapters;
    }

    public void setUserChapters(ArrayList<models.GameAdventure.Chapter> userChapters) {
        this.userChapters = userChapters;
    }
}