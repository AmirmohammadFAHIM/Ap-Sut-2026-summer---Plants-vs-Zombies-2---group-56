package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;
import java.util.ArrayList;

public class News implements Menu {
    @Override
    public String ChangeMenu(String menuName) { return "Invalid menu transition from this menu."; }

    @Override
    public String exitMenu() {
        App.setScreen(new view.HomeView());
        return "Returned to Home Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- News Menu ---"; }

    public static void pushNewsToUser(User user, String message) {
        if (user != null) {
            user.getUnreadNews().add(message);
            Data.saveUser();
        }
    }

    public String ShowNews() {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: Please log in.";

        ArrayList<String> unread = user.getUnreadNews();
        if (unread.isEmpty()) return "No new unread news.";

        StringBuilder sb = new StringBuilder("--- Unread News ---\n");
        for (String news : unread) {
            sb.append("- ").append(news).append("\n");
            user.getReadNews().add(news);
        }
        user.getUnreadNews().clear();
        Data.saveUser();
        return sb.toString().trim();
    }

    public String ShowAllNews() {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: Please log in.";

        StringBuilder sb = new StringBuilder("--- All News ---\n");
        for (String news : user.getReadNews()) sb.append("- ").append(news).append("\n");
        for (String news : user.getUnreadNews()) sb.append("- [NEW] ").append(news).append("\n");
        return sb.toString().trim();
    }
}