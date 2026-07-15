package models;

import models.GameAdventure.Chapter;
import models.GameAdventure.levels.Level;
import models.npc.Plant;
import models.npc.Zombie;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String nickname;
    private String email;
    private String passwordHash;
    private String gender;

    private int securityQuestionNumber;
    private String securityAnswer;

    private Chapter chapter;
    private Level level;
    private ArrayList<Zombie> zombies;
    private ArrayList<Plant> plants;

    private int coins = 0;
    private int diamonds = 0;
    private int highestScore = 0;
    private int gamesPlayed = 0;
    private int levelsPassed = 0;
    private int difficultyLevel = 3;
    private boolean isStayLoggedIn = false;

    public User(String name, String passwordHash, String nickname, String email, String gender) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.zombies = new ArrayList<>();
        this.plants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setSecurityQuestion(int questionNumber, String answer) {
        this.securityQuestionNumber = questionNumber;
        this.securityAnswer = answer;
    }

    public int getSecurityQuestionNumber() {
        return securityQuestionNumber;
    }

    public boolean checkSecurityAnswer(String answer) {
        return this.securityAnswer.equals(answer);
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void addDiamonds(int amount) {
        this.diamonds += amount;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int score) {
        if (score > this.highestScore) {
            this.highestScore = score;
        }
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public int getLevelsPassed() {
        return levelsPassed;
    }

    public void incrementLevelsPassed() {
        this.levelsPassed++;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public boolean isStayLoggedIn() {
        return isStayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        isStayLoggedIn = stayLoggedIn;
    }

    public void updateProgress() {

    }
}