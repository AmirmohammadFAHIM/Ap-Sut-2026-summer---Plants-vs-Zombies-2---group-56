package controllers.observer;

import models.entity.Zombie;
import models.entity.Plant;

import java.util.*;

public class WizardObserver {


    private final Map<Zombie, List<Plant>> wizardCats = new HashMap<>();

    public void addCat(Zombie wizard, Plant plant) {
        wizardCats.computeIfAbsent(wizard, k -> new ArrayList<>()).add(plant);
    }

    public void releaseCats(Zombie wizard) {
        List<Plant> cats = wizardCats.remove(wizard);
        if (cats != null) {
            for (Plant plant : cats) {
                plant.setCat(false);
            }
        }
    }
//
//    public void checkAndReleaseDeadWizards() {
//        List<Zombie> deadWizards = new ArrayList<>();
//        for (Zombie wizard : wizardCats.keySet()) {
//            if (wizard.isDead()) {
//                deadWizards.add(wizard);
//            }
//        }
//        for (Zombie wizard : deadWizards) {
//            releaseCats(wizard);
//        }
//    }
}