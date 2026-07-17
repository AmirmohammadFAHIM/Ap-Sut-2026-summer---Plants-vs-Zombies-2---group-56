package controllers.menus.SecondaryMenus;

import controllers.dataController.Data;
import controllers.menus.Menu;
import models.User;

import java.util.ArrayList;

public class News implements Menu {

    @Override
    public void ChangeMenu() {
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- News Menu ---");
    }

    public static void pushNewsToUser(User user, String message) {
        if (user != null) {
            user.getUnreadNews().add(message);
            Data.saveUser();
        }
    }

    public void ShowNews() {
        User user = Data.getCurrentUser();
        if (user == null) return;

        ArrayList<String> unread = user.getUnreadNews();
        if (unread.isEmpty()) {
            System.out.println("No new unread news.");
            return;
        }

        System.out.println("--- Unread News ---");
        for (String news : unread) {
            System.out.println("- " + news);
            user.getReadNews().add(news);
        }

        user.getUnreadNews().clear();
        Data.saveUser();
    }

    public void ShowAllNews() {
        User user = Data.getCurrentUser();
        if (user == null) return;

        System.out.println("--- All News ---");
        for (String news : user.getReadNews()) {
            System.out.println("- " + news);
        }
        for (String news : user.getUnreadNews()) {
            System.out.println("- [NEW] " + news);
        }
    }
}