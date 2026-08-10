package models;

import java.io.Serializable;
import java.util.ArrayList;

public class GreenHouse implements Serializable {
    private static final int MAX_POTS = 20;
    private User owner;
    private ArrayList<Pot> pots;

    public GreenHouse(User owner) {
        this.owner = owner;
        this.pots = new ArrayList<>();
        for (int i = 0; i < MAX_POTS; i++) {
            int x = (i % 5) + 1;
            int y = (i / 5) + 1;
            pots.add(new Pot(owner, x, y));
        }
    }

    public String showAll() {
        StringBuilder sb = new StringBuilder("--- GreenHouse Status for " + owner.getName() + " ---\n");
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 5; col++) {
                Pot pot = getPotByPosition(col, row);
                if (pot == null) continue;

                if (!pot.isUnlocked()) {
                    sb.append("[ Locked ]\t");
                } else if (pot.getSeedling() == null) {
                    sb.append("[ Empty  ]\t");
                } else {
                    int remaining = pot.getRemainingHours();
                    if (remaining <= 0) {
                        sb.append("[").append(pot.getSeedling()).append(" (Ready)]\t");
                    } else {
                        sb.append("[").append(pot.getSeedling()).append(" (").append(remaining).append("h)]\t");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public String growNow(int x, int y) {
        Pot pot = getPotByPosition(x, y);
        return pot != null ? pot.growNow() : "Error: Invalid coordinates.";
    }

    public String plantPot(int x, int y) {
        Pot pot = getPotByPosition(x, y);
        return pot != null ? pot.plant() : "Error: Invalid coordinates.";
    }

    public String collectPot(int x, int y) {
        Pot pot = getPotByPosition(x, y);
        return pot != null ? pot.collect() : "Error: Invalid coordinates.";
    }

    public Pot getPotByPosition(int x, int y) {
        for (Pot pot : pots) {
            if (pot.getX() == x && pot.getY() == y) return pot;
        }
        return null;
    }
}