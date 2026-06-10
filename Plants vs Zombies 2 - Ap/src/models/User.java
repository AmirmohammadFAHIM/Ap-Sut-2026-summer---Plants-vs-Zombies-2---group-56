package models;

import models.gamePanes.Chapter;
import models.gamePanes.Level;
import models.npc.Plant;
import models.npc.Zombie;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String password;
    private Chapter chapter;
    private Level level;
    private ArrayList<Zombie> zombies;
    private ArrayList<Plant>  plants;
    private int coins = 0;
    private int diamonds = 0;
    public User(String name, String email, String password, Chapter chapter) {
        this.name = name;
        this.email = email;
    }
}
