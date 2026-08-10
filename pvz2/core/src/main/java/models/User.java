package models;

import models.gameadventure.*;
import models.gameadventure.levels.*;
import models.factory.builder.*;
import models.entity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class User implements Serializable, QuestObserver {
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
    private int levelId;

    private int coins = 0;
    private int diamonds = 0;
    private int highestScore = 0;
    private int gamesPlayed = 0;
    private int levelsPassed = 0;
    private int difficultyLevel = 3;
    private int vaseBreaker = 1;
    private int wallNutBowling = 1;
    private int IZombie = 1;
    private boolean isStayLoggedIn = false;

    private int unlockedPots = 5;
    private int plantFoods = 0;
    private int randomSeeds = 0;
    private String lastDailyPurchaseDate = "";
    private HashMap<String, Integer> specificSeeds;

    private ArrayList<PlantType> unlockedPlants;
    private HashMap<PlantType, Integer> levels;
    private ArrayList<String> unlockedPlantsNames;
    private ArrayList<String> unreadNews;
    private ArrayList<String> readNews;
    private ArrayList<PlantType> boostList;

    private ArrayList<Quest> activeQuests;

    private ZombieRegistry zombieRegistry;

    private GreenHouse greenHouse;

    public User(String name, String passwordHash, String nickname, String email, String gender) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.specificSeeds = new HashMap<>();
        this.unlockedPlantsNames = new ArrayList<>();
        this.unreadNews = new ArrayList<>();
        this.readNews = new ArrayList<>();
        this.boostList = new ArrayList<>();
        this.levelId = 1;
        this.chapter = Chapters.AncientEgypt;

        this.zombieRegistry = new ZombieRegistry();

        this.levels = new HashMap<>();

        this.unlockedPlants = new ArrayList<>(Arrays.asList(PlantType.PEASHOOTER , PlantType.SNOW_PEA,
                PlantType.REPEATER , PlantType.CHOMPER , PlantType.WALL_NUT));

        for (PlantType plant : this.unlockedPlants) {
            this.levels.put(plant, 1);
            this.unlockedPlantsNames.add(plant.name());

            this.greenHouse = new GreenHouse(this);
        }

        this.activeQuests = new ArrayList<>();
        this.activeQuests.add(new Quest("Finish Dark Ages Pt.2", 1, "KILL_ZOMBIE", 50, "Gems", 15));
        this.activeQuests.add(new Quest("Adventure Extra: Daytime Dark Ages", 2, "COLLECT_SUN", 2000, "Coins", 4000));
    }

    @Override
    public void updateQuestProgress(String action, int amount) {
        if (activeQuests != null) {
            for (Quest quest : activeQuests) {
                quest.updateQuestProgress(action, amount);
            }
        }
    }

    public ZombieRegistry getZombieRegistry() {
        if (zombieRegistry == null) {
            zombieRegistry = new ZombieRegistry();
        }
        return zombieRegistry;
    }

    public ArrayList<Quest> getActiveQuests() { return activeQuests; }
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

    public int getVaseBreaker() { return vaseBreaker; }
    public void setVaseBreaker(int vaseBreaker) {
        if(vaseBreaker >= 3) vaseBreaker = 3;
        this.vaseBreaker = vaseBreaker;
    }

    public int getWallNutBowling() { return wallNutBowling; }
    public void setWallNutBowling(int wallNutBowling) {
        if(wallNutBowling >= 3) wallNutBowling =3;
        this.wallNutBowling = wallNutBowling;
    }

    public int getIZombie() { return IZombie; }
    public void setIZombie(int IZombie) { this.IZombie = IZombie; }
    public int getSecurityQuestionNumber() { return securityQuestionNumber; }
    public boolean checkSecurityAnswer(String answer) { return this.securityAnswer.equals(answer); }
    public int getCoins() { return coins; }
    public void addCoins(int amount) { this.coins += amount; }
    public int getDiamonds() { return diamonds; }
    public void addDiamonds(int amount) { this.diamonds += amount; }
    public int getHighestScore() { return highestScore; }
    public void setHighestScore(int score) { if (score > this.highestScore) { this.highestScore = score; } }
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
    public void setPlantFoods(int plantFoods) { this.plantFoods = plantFoods; }
    public int getRandomSeeds() { return randomSeeds; }
    public void addRandomSeeds(int amount) { this.randomSeeds += amount; }
    public String getLastDailyPurchaseDate() { return lastDailyPurchaseDate; }
    public void setLastDailyPurchaseDate(String date) { this.lastDailyPurchaseDate = date; }
    public void setChapter(Chapters chapter) { this.chapter = chapter; }
    public Chapters getChapter() { return chapter; }
    public int getLevelId() { return levelId; }
    public void setLevelId(int levelId) { this.levelId = levelId; }
    public void addSpecificSeed(String plantType, int amount) {
        if (this.specificSeeds == null) this.specificSeeds = new HashMap<>();
        this.specificSeeds.put(plantType, this.specificSeeds.getOrDefault(plantType, 0) + amount);
    }
    public void addToBoostList(PlantType seedling){
        this.boostList.add(seedling);
    }
    public int getSpecificSeedCount(String plantType) { return this.specificSeeds != null ? this.specificSeeds.getOrDefault(plantType, 0) : 0; }
    public ArrayList<String> getUnlockedPlantsNames() { return unlockedPlantsNames; }
    public ArrayList<String> getUnreadNews() { return unreadNews; }
    public ArrayList<String> getReadNews() { return readNews; }
    public ArrayList<PlantType> getBoostList() { return boostList;
    }
    public ArrayList<PlantType> getUnlockedPlants() { return unlockedPlants; }
    public void setUnlockedPlants(ArrayList<PlantType> unlockedPlants) { this.unlockedPlants = unlockedPlants; }
    public HashMap<PlantType, Integer> getLevels() { return levels; }
    public void setLevels(HashMap<PlantType, Integer> levels) { this.levels = levels; }
    public void setLevelsPassed(int levelsPassed) {
        if(levelsPassed >= 16) levelsPassed = 16;
        this.levelsPassed = levelsPassed;
    }
    public GreenHouse getGreenHouse() {
        if (this.greenHouse == null) {
            this.greenHouse = new GreenHouse(this);
        }
        return greenHouse;
    }
    public void updateProgress() { }
}