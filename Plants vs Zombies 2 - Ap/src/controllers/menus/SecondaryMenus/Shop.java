package controllers.menus.SecondaryMenus;

import controllers.dataController.Data;
import controllers.menus.Menu;
import models.App;
import models.Pot;
import models.User;
import models.entity.Seed;
import models.factory.builder.PlantType;
import models.factory.builder.PlantType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Shop implements Menu {
    private Seed DailyOffer;
    private ArrayList<Seed> seeds;
    private Pot pot;

    private String currentDailyPlant = "PEASHOOTER";

    @Override
    public String ChangeMenu(String menuName) {
        return "Invalid menu transition from Shop menu.";
    }

    @Override
    public void exitMenu() {
        App.setScreen(new view.GreenHouseView());
        System.out.println("Returned to GreenHouse Menu.");
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Shop Menu---");
    }

    public void purchase(String itemName, int count) {
        User user = Data.getCurrentUser();
        if (user == null) {
            System.out.println("Error: Please log in first.");
            return;
        }

        switch (itemName.toLowerCase()) {
            case "pot":
                int potCost = 2000 * count;
                if (user.getUnlockedPots() + count > 20) {
                    System.out.println("Error: Maximum of 20 pots reached.");
                    break;
                }
                if (canPurchase(potCost, "coin")) {
                    user.addCoins(-potCost);
                    user.addUnlockedPots(count);
                    Data.saveUser();
                    System.out.println(count + " Pot(s) unlocked successfully.");
                } else {
                    System.out.println("Error: Not enough coins.");
                }
                break;

            case "plantfood":
                int foodCost = 3 * count;
                if (user.getPlantFoods() + count > 3) {
                    System.out.println("Error: Maximum of 3 Plant Foods can be stored.");
                    break;
                }
                if (canPurchase(foodCost, "diamond")) {
                    user.addDiamonds(-foodCost);
                    user.addPlantFoods(count);
                    Data.saveUser();
                    System.out.println(count + " Plant Food(s) purchased successfully.");
                } else {
                    System.out.println("Error: Not enough diamonds.");
                }
                break;

            case "exchange":
                int exchangeCost = 5 * count;
                if (canPurchase(exchangeCost, "diamond")) {
                    user.addDiamonds(-exchangeCost);
                    user.addCoins(500 * count);
                    Data.saveUser();
                    System.out.println("Currency exchanged successfully. Gained " + (500 * count) + " coins.");
                } else {
                    System.out.println("Error: Not enough diamonds.");
                }
                break;

            case "daily":
                String today = LocalDate.now().toString(); // خواندن تاریخ سیستم
                if (today.equals(user.getLastDailyPurchaseDate())) {
                    System.out.println("Error: You have already purchased today's daily offer.");
                    break;
                }

                int dailyCost = 1600; // 20% تخفیف از 2000
                if (canPurchase(dailyCost, "coin")) {
                    user.addCoins(-dailyCost);
                    user.addSpecificSeed(currentDailyPlant, 10);
                    user.setLastDailyPurchaseDate(today); // ثبت تاریخ خرید برای جلوگیری از خرید مجدد در همین روز
                    Data.saveUser();
                    System.out.println("Successfully bought the Daily Offer! 10x " + currentDailyPlant + " Seeds added.");
                } else {
                    System.out.println("Error: Not enough coins (1600 required).");
                }
                break;

            default:
                System.out.println("Error: Invalid item.");
        }
    }

    public boolean canPurchase(int cost, String currency) {
        User user = Data.getCurrentUser();
        if (user == null) return false;

        if (currency.equalsIgnoreCase("coin")) {
            return user.getCoins() >= cost;
        } else if (currency.equalsIgnoreCase("diamond")) {
            return user.getDiamonds() >= cost;
        }
        return false;
    }

    public void setDailyOffer() {
        // دریافت تمام گیاهان تعریف شده در بازی به صورت خودکار از طریق Enum
        PlantType[] allPlants = PlantType.values();

        // انتخاب یک گیاه کاملاً تصادفی از بین تمام گیاهان
        currentDailyPlant = allPlants[new Random().nextInt(allPlants.length)].name();

        System.out.println("Daily offer updated!");
        System.out.println("Offer: 10x " + currentDailyPlant + " Seed Packets for 1600 Coins (20% OFF).");
    }

    public void normalPurchase(String plantType) {
        User user = Data.getCurrentUser();
        if (user != null && canPurchase(5, "diamond")) {
            user.addDiamonds(-5);
            user.addSpecificSeed(plantType, 10); // ثبت 10 عدد بذر برای این گیاه
            Data.saveUser();
            System.out.println("Bought 10 " + plantType + " Seeds for 5 Diamonds.");
        } else {
            System.out.println("Error: Not enough diamonds (5 required).");
        }
    }

    public void randomPurchase() {
        User user = Data.getCurrentUser();
        if (user != null && canPurchase(1000, "coin")) {
            user.addCoins(-1000);
            user.addRandomSeeds(5); // ثبت 5 عدد بذر تصادفی
            Data.saveUser();
            System.out.println("Bought 5 Random Seeds for 1000 Coins.");
        } else {
            System.out.println("Error: Not enough coins (1000 required).");
        }
    }

    public ArrayList<Seed> getSeeds() { return seeds; }
}