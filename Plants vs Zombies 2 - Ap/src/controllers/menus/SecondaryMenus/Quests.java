package controllers.menus.SecondaryMenus;

import controllers.menus.Menu;
import models.App;

public class Quests implements Menu {

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
        showQuests();
    }

    private String currentPage = "Adventure";

    public void changePage(String pageName) {
        if (pageName.equalsIgnoreCase("Adventure") ||
                pageName.equalsIgnoreCase("Special") ||
                pageName.equalsIgnoreCase("Minigame") ||
                pageName.equalsIgnoreCase("Community") ||
                pageName.equalsIgnoreCase("Challenge") ||
                pageName.equalsIgnoreCase("Mystery")) {

            this.currentPage = pageName;
            System.out.println("Switched to Travel Log page: " + this.currentPage);
        } else {
            System.out.println("Error: Invalid page name. Available pages: Adventure, Special, Minigame, Community, Challenge, Mystery.");
        }
    }

    public void showQuests() {
        System.out.println("--- Travel Log : " + currentPage + " ---");
        if (currentPage.equalsIgnoreCase("Adventure")) {
            System.out.println("- Finish Dark Ages Pt.2 (Rewards: 15 Gems)");
            System.out.println("- Adventure Extra: Daytime Dark Ages (Rewards: 4000 Coins)");
        } else {
            System.out.println("No active quests for this category yet.");
        }
    }
}