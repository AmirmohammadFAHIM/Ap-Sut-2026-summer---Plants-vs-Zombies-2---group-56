package models;

import models.utils.CountDown;
import models.factory.builder.PlantType;

import java.io.Serializable;
import java.util.ArrayList;

public class Pot implements Serializable {
    private boolean unlocked;
    private PlantType seedling;
    private int x;
    private int y;
    private CountDown timer;
    private User user;

    public Pot(User user, int x, int y) {
        this.x = x;
        this.y = y;
        this.user = user;
        this.seedling = null;
        // باز بودن گلدان‌های اولیه
        this.unlocked = (y == 1);
    }

    public String plant() {
        if (!this.unlocked) return "Error: This pot is locked!";
        if (this.seedling != null) return "Error: Pot already has a plant.";

        int random = (int)(Math.random() * 10) % 2;
        if (random == 0) {
            this.seedling = PlantType.MARIGOLD;
            timer = new CountDown(2); // زمان رشد گل همیشه بهار
        } else {
            ArrayList<String> names = user.getUnlockedPlantsNames();
            if(names.isEmpty()) return "Error: You have no unlocked plants to grow!";
            int plantRand = (int)(Math.random() * names.size());
            String chosen = names.get(plantRand);
            this.seedling = PlantType.valueOf(chosen.toUpperCase());
            timer = new CountDown(8); // زمان رشد گیاهان تصادفی
        }
        return "Planted " + this.seedling.name() + " at (" + x + ", " + y + ").";
    }

    public String collect() {
        if (!this.unlocked) return "Error: This pot is locked!";
        if (this.seedling == null) return "Error: There is no plant here to collect!";
        if (this.timer.getRemainingHours() > 0) return "Error: The plant is not fully grown yet. Remaining: " + timer.getRemainingHours() + " hours.";

        String result;
        if (this.seedling.equals(PlantType.MARIGOLD)) {
            user.addCoins(500);
            result = "Collected a Marigold! Earned 500 coins.";
        } else {
            user.addToBoostList(seedling);
            result = "Collected " + seedling.name() + "! Boost saved.";
        }
        this.seedling = null;
        return result;
    }

    public String growNow() {
        if (!this.unlocked) return "Error: This pot is locked!";
        if (this.seedling == null) return "Error: Pot is empty.";

        int remaining = this.timer.getRemainingHours();
        if (remaining <= 0) return "Error: Plant is already fully grown!";

        if (user.getDiamonds() < remaining) {
            return "Error: Not enough diamonds. You need " + remaining + " diamonds.";
        }

        user.addDiamonds(-1 * remaining);
        timer.setCountingHours(0);
        return "Used " + remaining + " diamonds to force grow. The plant is ready!";
    }

    public void unlock(boolean isOpen) { this.unlocked = isOpen; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getSeedling() { return seedling == null ? null : seedling.toString(); }
    public int getRemainingHours() { return timer == null ? 0 : timer.getRemainingHours(); }
    public boolean isUnlocked() { return unlocked; }
}