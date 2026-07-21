package controllers.observer;

import models.npc.Zombie;
import models.npc.Plant;

import java.util.*;

public class WizardObserver {

    // هر جادوگر → لیست گربه‌های خودش
    private final Map<Zombie, List<Plant>> wizardCats = new HashMap<>();

    public void addCat(Zombie wizard, Plant plant) {
        wizardCats.computeIfAbsent(wizard, k -> new ArrayList<>()).add(plant);
    }

    public void releaseCats(Zombie wizard) {
        List<Plant> cats = wizardCats.remove(wizard); // فقط گربه‌های این جادوگر
        if (cats != null) {
            for (Plant plant : cats) {
                plant.setCat(false);
            }
        }
    }

    public void checkAndReleaseDeadWizards() {
        List<Zombie> deadWizards = new ArrayList<>();
        for (Zombie wizard : wizardCats.keySet()) {
            if (wizard.isDead()) {
                deadWizards.add(wizard);
            }
        }
        for (Zombie wizard : deadWizards) {
            releaseCats(wizard); // فقط گربه‌های این جادوگر آزاد می‌شوند
        }
    }
}