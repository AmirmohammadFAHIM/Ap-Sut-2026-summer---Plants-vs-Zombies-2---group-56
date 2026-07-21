package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import models.App;
import models.User;
import controllers.menus.Menu;
import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoard implements Menu{

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
        System.out.println("--- LeaderBoard Menu ---");
        showLeaderBoard();
    }

    public void showLeaderBoard() {
        ArrayList<User> users = Data.getAllUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        System.out.println(String.format("%-15s | %-10s | %-10s | %-10s",
                "Username", "MeowPoint", "Levels", "Games"));
        System.out.println("------------------------------------------------------------");

        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingInt(User::getHighestScore).reversed());

        for (User user : sortedUsers) {
            System.out.println(String.format("%-15s | %-10d | %-10d | %-10d",
                    user.getName(), user.getHighestScore(), user.getLevelsPassed(), user.getGamesPlayed()));
        }
    }

    public void sortLeaderBoard(String criteria) {
        ArrayList<User> users = Data.getAllUsers();
        ArrayList<User> sortedUsers = new ArrayList<>(users);

        if (criteria.equalsIgnoreCase("score")) {
            System.out.println("Sorting by MeowPoint...");
            sortedUsers.sort(Comparator.comparingInt(User::getHighestScore).reversed());
        } else if (criteria.equalsIgnoreCase("level")) {
            System.out.println("Sorting by Levels Passed...");
            sortedUsers.sort(Comparator.comparingInt(User::getLevelsPassed).reversed());
        }

        for (User user : sortedUsers) {
            System.out.println(user.getName() + " - " + criteria + ": " +
                    (criteria.equalsIgnoreCase("score") ? user.getHighestScore() : user.getLevelsPassed()));
        }
    }
}