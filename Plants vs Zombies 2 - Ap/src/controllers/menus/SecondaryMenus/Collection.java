package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;
import models.factory.builder.PlantType;

public class Collection implements Menu {

    @Override
    public String ChangeMenu(String menuName) {
        return "Invalid menu transition from this menu.";
    }

    @Override
    public void exitMenu() {
        App.setScreen(new view.PlayView());
        System.out.println("Returned to Play Menu.");
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Collection Menu ---");
    }

    public void showunlockedPlant() {
        User user = Data.getCurrentUser();
        if (user != null) {
            System.out.println("--- Unlocked Plants ---");
            for (String plant : user.getUnlockedPlantsNames()) {
                System.out.println("- " + plant);
            }
        }
    }

    public void showunlockedZombie() {
        System.out.println("Showing unlocked zombies... (To be integrated with Adventure mode progression)");
    }

    public void showAllPlants() {
        System.out.println("--- All Plants in the Game ---");
        for (PlantType plant : PlantType.values()) {
            System.out.println("- " + plant.name());
        }
    }

    public void showAllZombies() {
        System.out.println("Showing all zombies in the game... (Pending Enum creation)");
    }

    public void showZombie(String zombieName) {
        System.out.println("Showing details for zombie: " + zombieName);
    }

    public void showPlant(String plantName) {
        System.out.println("Showing details for plant: " + plantName);
    }

    public void upgradePlant(String plantName) {
        System.out.println("Upgrading plant: " + plantName + " (Logic connects to Seed packets later)");
    }

    public void buyPlant(String plantName) {
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            String upperName = plantName.toUpperCase();

            if (currentUser.getUnlockedPlantsNames().contains(upperName)) {
                System.out.println("Error: You already own this plant.");
                return;
            }

            if (currentUser.getCoins() >= 2000) {
                currentUser.addCoins(-2000);
                currentUser.getUnlockedPlantsNames().add(upperName);

                // ارسال یک خبر/نوتیفیکیشن به کاربر
                News.pushNewsToUser(currentUser, "New plant unlocked: " + upperName);

                Data.saveUser();
                System.out.println("Plant " + upperName + " purchased successfully.");
            } else {
                System.out.println("Error: Not enough coins to purchase this plant. 2000 coins required.");
            }
        }
    }
}