package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;

public class Wallet implements Menu {

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
        System.out.println("--- Wallet Menu ---");
        showCoinWallet();
        showGemWallet();
    }

    public void showCoinWallet() {
        User user = Data.getCurrentUser();
        if (user != null) {
            System.out.println("Coin Wallet: " + user.getCoins() + " Coins");
        } else {
            System.out.println("Error: Please log in to view your wallet.");
        }
    }

    public void showGemWallet() {
        User user = Data.getCurrentUser();
        if (user != null) {
            System.out.println("Gem Wallet: " + user.getDiamonds() + " Diamonds");
        } else {
            System.out.println("Error: Please log in to view your wallet.");
        }
    }

    public void cheatAdd(int amount, String type) {
        User user = Data.getCurrentUser();
        if (user != null) {
            if (type.equalsIgnoreCase("coin") || type.equalsIgnoreCase("coins")) {
                user.addCoins(amount);
                Data.saveUser();
                System.out.println("Cheat activated: Added " + amount + " coins successfully.");
            } else if (type.equalsIgnoreCase("diamond") || type.equalsIgnoreCase("diamonds")) {
                user.addDiamonds(amount);
                Data.saveUser();
                System.out.println("Cheat activated: Added " + amount + " diamonds successfully.");
            } else {
                System.out.println("Error: Invalid cheat type. Use 'coin' or 'diamond'.");
            }
        }
    }
}