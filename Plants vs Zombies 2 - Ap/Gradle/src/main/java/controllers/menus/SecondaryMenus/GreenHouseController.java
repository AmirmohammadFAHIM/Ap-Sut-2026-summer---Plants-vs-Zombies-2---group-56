// file: Plants vs Zombies 2 - Ap/src/controllers/menus/SecondaryMenus/GreenHouseController.java
package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import models.App;
import models.User;
import controllers.menus.Menu;
import java.util.Random;

public class GreenHouseController implements Menu {
    @Override
    public String ChangeMenu(String menuName) {
        if (menuName.equalsIgnoreCase("Shop menu")) {
            App.setScreen(new view.ShopView());
            return "Changed menu successfully to Shop menu";
        }
        return "Invalid menu transition from GreenHouse menu.";
    }

    @Override
    public String exitMenu() {
        App.setScreen(new view.PlayView());
        return "Returned to Play Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- GreenHouse Menu ---"; }

    public String showgreenhouse() {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";

        StringBuilder sb = new StringBuilder("--- GreenHouse Status ---\n");
        int unlocked = user.getUnlockedPots();
        sb.append("Unlocked Pots: ").append(unlocked).append("/20\n");

        for (int y = 1; y <= 4; y++) {
            for (int x = 1; x <= 5; x++) {
                int potNumber = ((y - 1) * 5) + x;
                sb.append(potNumber > unlocked ? "[ Locked ]\t" : "[ Empty  ]\t");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public String plant(int x, int y) {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";
        if (x < 1 || x > 5 || y < 1 || y > 4) return "Error: Coordinates out of bounds. X(1-5), Y(1-4).";

        int potNumber = ((y - 1) * 5) + x;
        if (potNumber > user.getUnlockedPots()) return "Error: This pot is locked.";

        Random rand = new Random();
        return rand.nextBoolean() ? "Planted a Marigold at (" + x + ", " + y + "). Growth time: 2 hours."
                : "Planted a Random Plant at (" + x + ", " + y + "). Growth time: 8 hours.";
    }

    public String forceGrow(int x, int y, int remainingHours) {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";
        if (remainingHours <= 0) return "Error: Plant is already fully grown or pot is empty.";

        if (user.getDiamonds() >= remainingHours) {
            user.addDiamonds(-remainingHours);
            Data.saveUser();
            return "Forced growth using " + remainingHours + " diamonds. Plant is ready!";
        }
        return "Error: Not enough diamonds.";
    }

    public String collect(int x, int y, boolean isMarigold) {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";

        if (isMarigold) {
            user.addCoins(500);
            Data.saveUser();
            return "Collected a Marigold! Earned 500 coins.";
        }
        Data.saveUser();
        return "Collected a Random Plant! Boost saved.";
    }
}