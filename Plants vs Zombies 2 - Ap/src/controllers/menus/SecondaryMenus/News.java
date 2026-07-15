package controllers.menus.SecondaryMenus;

import controllers.menus.Menu;

public class News implements Menu {

    @Override
    public void ChangeMenu() {
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- News Menu ---");
    }

    public void updateNews() {
    }

    public void unlockPlants(String plantName) {
        System.out.println("News: New plant unlocked - " + plantName);
    }

    public void unlockZombies(String zombieName) {
        System.out.println("News: New zombie discovered - " + zombieName);
    }

    public void unlockMiniGames(String miniGameName) {
        System.out.println("News: New mini-game unlocked - " + miniGameName);
    }

    public void ShowNews() {
        System.out.println("Showing unread news...");
    }

    public void ShowAllNews() {
        System.out.println("Showing all news...");
    }
}