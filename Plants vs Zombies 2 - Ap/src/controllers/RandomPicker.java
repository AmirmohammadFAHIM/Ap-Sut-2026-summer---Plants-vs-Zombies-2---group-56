package controllers;

import models.npc.Zombie;
import models.npc.Plant;
import java.util.*;

public class RandomPicker {

    private Random random = new Random();
    private GameController game;

    public RandomPicker(GameController game) {
        this.game = game;
    }

    // ====== پیدا کردن یک خانه خالی رندوم ======
    public int[] pickEmptyCell(int row, int radius, int maxAttempts) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int dr = random.nextInt(radius * 2 + 1) - radius;
            int dc = random.nextInt(radius * 2 + 1) - radius;
            int newRow = row + dr;
            int newCol = game.getCol() + dc; // باید col رو از زامبی بگیریم

            if (game.isCellEmpty(newRow, newCol)) {
                return new int[]{newRow, newCol};
            }
        }
        return null; // هیچ خونه خالی پیدا نشد
    }

    // ====== پیدا کردن یک زامبی رندوم در شعاع ======
    public Zombie pickZombieInRange(Zombie center, float range, int maxAttempts) {
        List<Zombie> candidates = new ArrayList<>();
        for (Zombie z : game.getZombies()) {
            if (z == center) continue;
            float dx = z.getX() - center.getX();
            if (Math.abs(dx) <= range * 80 && z.getRow() == center.getRow()) {
                candidates.add(z);
            }
        }
        if (candidates.isEmpty()) return null;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            Zombie selected = candidates.get(random.nextInt(candidates.size()));
            if (!selected.isDead()) {
                return selected;
            }
        }
        return null;
    }

    // ====== پیدا کردن یک گیاه رندوم در شعاع ======
    public Plant pickPlantInRange(Zombie zombie, float range, int maxAttempts) {
        List<Plant> candidates = new ArrayList<>();
        for (Plant plant : game.getPlants()) {
            float dx = plant.getX() - zombie.getX();
            if (Math.abs(dx) <= range * 80 && plant.getRow() == zombie.getRow()) {
                candidates.add(plant);
            }
        }
        if (candidates.isEmpty()) return null;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            Plant selected = candidates.get(random.nextInt(candidates.size()));
            if (!selected.isDead()) {
                return selected;
            }
        }
        return null;
    }

    // ====== پیدا کردن یک گیاه رندوم در همان ردیف با محدوده min-max ======
    public Plant pickPlantInSameRow(Zombie zombie, float minRange, float maxRange, int maxAttempts) {
        List<Plant> candidates = new ArrayList<>();
        for (Plant plant : game.getPlants()) {
            float dx = plant.getX() - zombie.getX();
            if (dx >= minRange * 80 && dx <= maxRange * 80 && plant.getRow() == zombie.getRow()) {
                candidates.add(plant);
            }
        }
        if (candidates.isEmpty()) return null;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            Plant selected = candidates.get(random.nextInt(candidates.size()));
            if (!selected.isDead()) {
                return selected;
            }
        }
        return null;
    }

    // ====== پیدا کردن یک زامبی رندوم در کل نقشه ======
    public Zombie pickAnyZombie(int maxAttempts) {
        List<Zombie> candidates = game.getZombies();
        if (candidates.isEmpty()) return null;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            Zombie selected = candidates.get(random.nextInt(candidates.size()));
            if (!selected.isDead()) {
                return selected;
            }
        }
        return null;
    }
}