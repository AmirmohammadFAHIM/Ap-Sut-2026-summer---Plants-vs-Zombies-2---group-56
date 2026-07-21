package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import models.App;
import models.GreenHouse;
import models.User;
import controllers.menus.Menu;
import java.util.Random;

public class GreenHouseController implements Menu {
    private GreenHouse greenHouse;

    @Override
    public String ChangeMenu(String menuName) {
        if (menuName.equalsIgnoreCase("Shop menu")) {
            App.setScreen(new view.ShopView());
            return "Changed menu successfully to Shop menu";
        }
        return "Invalid menu transition from GreenHouse menu.";
    }

    @Override
    public void exitMenu() {
        App.setScreen(new view.PlayView());
        System.out.println("Returned to Play Menu.");
    }

    @Override
    public void ShowCurrentMenu() {
        showgreenhouse();
    }

    public GreenHouseController() {
        this.greenHouse = new GreenHouse();
    }

    public void showgreenhouse() {
        User user = Data.getCurrentUser();
        if (user == null) return;

        System.out.println("--- GreenHouse Status ---");
        int unlocked = user.getUnlockedPots();
        System.out.println("Unlocked Pots: " + unlocked + "/20");

        for (int y = 1; y <= 4; y++) {
            for (int x = 1; x <= 5; x++) {
                int potNumber = ((y - 1) * 5) + x;
                if (potNumber > unlocked) {
                    System.out.print("[ Locked ]\t");
                } else {
                    System.out.print("[ Empty  ]\t");
                }
            }
            System.out.println();
        }
    }

    public void plant(int x, int y) {
        User user = Data.getCurrentUser();
        if (user == null) return;

        if (x < 1 || x > 5 || y < 1 || y > 4) {
            System.out.println("Error: Coordinates out of bounds. X(1-5), Y(1-4).");
            return;
        }

        int potNumber = ((y - 1) * 5) + x;
        if (potNumber > user.getUnlockedPots()) {
            System.out.println("Error: This pot is locked.");
            return;
        }

        Random rand = new Random();
        if (rand.nextBoolean()) {
            System.out.println("Planted a Marigold at (" + x + ", " + y + "). Growth time: 2 hours.");
        } else {
            System.out.println("Planted a Random Plant at (" + x + ", " + y + "). Growth time: 8 hours.");
        }
    }

    public void grow() {
        System.out.println("Updating growth status based on system time...");
    }

    public void forceGrow(int x, int y, int remainingHours) {
        User user = Data.getCurrentUser();
        if (user == null) return;

        if (remainingHours <= 0) {
            System.out.println("Error: Plant is already fully grown or pot is empty.");
            return;
        }

        int diamondCost = remainingHours;
        if (user.getDiamonds() >= diamondCost) {
            user.addDiamonds(-diamondCost);
            Data.saveUser();
            System.out.println("Forced growth using " + diamondCost + " diamonds. Plant is ready!");
        } else {
            System.out.println("Error: Not enough diamonds.");
        }
    }

    public void collect(int x, int y, boolean isMarigold) {
        User user = Data.getCurrentUser();
        if (user == null) return;

        if (isMarigold) {
            user.addCoins(500);
            System.out.println("Collected a Marigold! Earned 500 coins.");
        } else {
            System.out.println("Collected a Random Plant! Boost saved.");
        }
        Data.saveUser();
    }
}