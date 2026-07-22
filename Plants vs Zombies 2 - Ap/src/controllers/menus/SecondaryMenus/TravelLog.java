package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.Quest;
import models.User;

public class TravelLog implements Menu {

    private String currentPage = "Adventure";

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
        User user = Data.getCurrentUser();

        if (user != null && currentPage.equalsIgnoreCase("Adventure")) {
            if (user.getActiveQuests() == null || user.getActiveQuests().isEmpty()) {
                System.out.println("No active quests for this category yet.");
                return;
            }

            for (Quest quest : user.getActiveQuests()) {
                if (quest.isDone()) {
                    System.out.println("- [DONE] " + quest.getQuestName() + " (Rewards: " + quest.getRewardAmount() + " " + quest.getRewardType() + ")");
                } else {
                    System.out.println("- " + quest.getQuestName() + " (" + (int)quest.getProgress() + "/" + (int)quest.getTarget() + ") (Rewards: " + quest.getRewardAmount() + " " + quest.getRewardType() + ")");
                }
            }
        } else {
            System.out.println("No active quests for this category yet.");
        }
    }
}