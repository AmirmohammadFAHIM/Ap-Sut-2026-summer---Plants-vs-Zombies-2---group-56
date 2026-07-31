package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.Quest;
import models.User;

public class TravelLog implements Menu {
    private String currentPage = "Adventure";

    @Override
    public String ChangeMenu(String menuName) { return "Invalid menu transition from this menu."; }

    @Override
    public String exitMenu() {
        App.setScreen(new view.PlayView());
        return "Returned to Play Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- Travel Log Menu ---"; }

    public String changePage(String pageName) {
        if (pageName.equalsIgnoreCase("Adventure") || pageName.equalsIgnoreCase("Special") ||
                pageName.equalsIgnoreCase("Minigame") || pageName.equalsIgnoreCase("Community") ||
                pageName.equalsIgnoreCase("Challenge") || pageName.equalsIgnoreCase("Mystery")) {
            this.currentPage = pageName;
            return "Switched to Travel Log page: " + this.currentPage;
        }
        return "Error: Invalid page name. Available pages: Adventure, Special, Minigame, Community, Challenge, Mystery.";
    }

    public String showQuests() {
        StringBuilder sb = new StringBuilder("--- Travel Log : ").append(currentPage).append(" ---\n");
        User user = Data.getCurrentUser();

        if (user != null && currentPage.equalsIgnoreCase("Adventure")) {
            if (user.getActiveQuests() == null || user.getActiveQuests().isEmpty()) {
                return sb.append("No active quests for this category yet.").toString();
            }
            for (Quest quest : user.getActiveQuests()) {
                if (quest.isDone()) {
                    sb.append("- [DONE] ").append(quest.getQuestName()).append(" (Rewards: ").append(quest.getRewardAmount()).append(" ").append(quest.getRewardType()).append(")\n");
                } else {
                    sb.append("- ").append(quest.getQuestName()).append(" (").append((int)quest.getProgress()).append("/").append((int)quest.getTarget()).append(") (Rewards: ").append(quest.getRewardAmount()).append(" ").append(quest.getRewardType()).append(")\n");
                }
            }
        } else {
            sb.append("No active quests for this category yet.");
        }
        return sb.toString().trim();
    }
}