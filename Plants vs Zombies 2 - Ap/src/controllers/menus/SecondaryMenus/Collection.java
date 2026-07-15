package controllers.menus.SecondaryMenus;

import controllers.dataController.Data;
import controllers.menus.Menu;
import models.User;

public class Collection implements Menu {

    @Override
    public void ChangeMenu() {
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Collection Menu ---");
    }

    public void showunlockedPlant() {
        System.out.println("Showing unlocked plants...");
    }

    public void showunlockedZombie() {
        System.out.println("Showing unlocked zombies...");
    }

    public void showAllPlants() {
        System.out.println("Showing all plants in the game...");
    }

    public void showAllZombies() {
        System.out.println("Showing all zombies in the game...");
    }

    public void showZombie(String zombieName) {
        System.out.println("Showing details for zombie: " + zombieName);
    }

    public void showPlant(String plantName) {
        System.out.println("Showing details for plant: " + plantName);
    }

    public void upgradePlant(String plantName) {
        System.out.println("Upgrading plant: " + plantName);
    }

    public void buyPlant(String plantName) {
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getCoins() >= 2000) {
                currentUser.addCoins(-2000);
                Data.saveUser();
                System.out.println("Plant " + plantName + " purchased successfully.");
            } else {
                System.out.println("Error: not enough coins to purchase this plant. 2000 coins required.");
            }
        }
    }
}