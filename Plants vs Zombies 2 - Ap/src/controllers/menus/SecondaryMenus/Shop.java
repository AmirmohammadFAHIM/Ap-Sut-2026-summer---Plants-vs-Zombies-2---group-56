package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;
import models.factory.builder.PlantType;

import java.time.LocalDate;
import java.util.Random;

public class Shop implements Menu {

    private static String currentDailyPlant = "PEASHOOTER";
    private static String lastUpdatedDate = "";

    @Override
    public String ChangeMenu(String menuName) { return "Invalid menu transition from Shop menu."; }

    @Override
    public String exitMenu() {
        App.setScreen(new view.GreenHouseView());
        return "Returned to GreenHouse Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- Shop Menu---"; }

    public String purchase(String itemName, int count) {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: Please log in first.";

        switch (itemName.toLowerCase()) {
            case "pot":
                int potCost = 2000 * count;
                if (user.getUnlockedPots() + count > 20) return "Error: Maximum of 20 pots reached.";
                if (canPurchase(potCost, "coin")) {
                    user.addCoins(-potCost);
                    user.addUnlockedPots(count);
                    Data.saveUser();
                    return count + " Pot(s) unlocked successfully.";
                }
                return "Error: Not enough coins.";

            case "plantfood":
                int foodCost = 3 * count;
                if (user.getPlantFoods() + count > 3) return "Error: Maximum of 3 Plant Foods can be stored.";
                if (canPurchase(foodCost, "diamond")) {
                    user.addDiamonds(-foodCost);
                    user.addPlantFoods(count);
                    Data.saveUser();
                    return count + " Plant Food(s) purchased successfully.";
                }
                return "Error: Not enough diamonds.";

            case "exchange":
                int exchangeCost = 5 * count;
                if (canPurchase(exchangeCost, "diamond")) {
                    user.addDiamonds(-exchangeCost);
                    user.addCoins(500 * count);
                    Data.saveUser();
                    return "Currency exchanged successfully. Gained " + (500 * count) + " coins.";
                }
                return "Error: Not enough diamonds.";

            case "daily":
                String today = LocalDate.now().toString();
                if (today.equals(user.getLastDailyPurchaseDate())) return "Error: You have already purchased today's daily offer.";
                int dailyCost = 1600;
                if (canPurchase(dailyCost, "coin")) {
                    user.addCoins(-dailyCost);
                    user.addSpecificSeed(currentDailyPlant, 10);
                    user.setLastDailyPurchaseDate(today);
                    Data.saveUser();
                    return "Successfully bought the Daily Offer! 10x " + currentDailyPlant + " Seeds added.";
                }
                return "Error: Not enough coins (1600 required).";

            default: return "Error: Invalid item.";
        }
    }

    public boolean canPurchase(int cost, String currency) {
        User user = Data.getCurrentUser();
        if (user == null) return false;
        if (currency.equalsIgnoreCase("coin")) return user.getCoins() >= cost;
        if (currency.equalsIgnoreCase("diamond")) return user.getDiamonds() >= cost;
        return false;
    }

    public String setDailyOffer() {
        String today = LocalDate.now().toString();
        if (!today.equals(lastUpdatedDate)) {
            PlantType[] allPlants = PlantType.values();
            currentDailyPlant = allPlants[new Random().nextInt(allPlants.length)].name();
            lastUpdatedDate = today;
        }
        return "Daily offer updated!\nOffer: 10x " + currentDailyPlant + " Seed Packets for 1600 Coins (20% OFF).";
    }

    public String normalPurchase(String plantType) {
        User user = Data.getCurrentUser();
        if (user != null && canPurchase(5, "diamond")) {
            user.addDiamonds(-5);
            user.addSpecificSeed(plantType, 10);
            Data.saveUser();
            return "Bought 10 " + plantType + " Seeds for 5 Diamonds.";
        }
        return "Error: Not enough diamonds (5 required).";
    }

    public String randomPurchase() {
        User user = Data.getCurrentUser();
        if (user != null && canPurchase(1000, "coin")) {
            user.addCoins(-1000);
            user.addRandomSeeds(5);
            Data.saveUser();
            return "Bought 5 Random Seeds for 1000 Coins.";
        }
        return "Error: Not enough coins (1000 required).";
    }
}