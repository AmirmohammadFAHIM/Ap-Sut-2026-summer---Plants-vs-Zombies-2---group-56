package controllers.menus.secondarymenus;

import controllers.datacontroller.Data;
import models.App;
import models.User;
import controllers.menus.Menu;
import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoard implements Menu {
    @Override
    public String ChangeMenu(String menuName) { return "Invalid menu transition from this menu."; }

    @Override
    public String exitMenu() {
        App.setScreen(new view.PlayView());
        return "Returned to Play Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- LeaderBoard Menu ---"; }

    public String showLeaderBoard() {
        ArrayList<User> users = Data.getAllUsers();
        if (users == null || users.isEmpty()) return "No users available.";

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s | %-10s | %-10s | %-10s\n", "Username", "MeowPoint", "Levels", "Games"));
        sb.append("------------------------------------------------------------\n");

        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingInt(User::getHighestScore).reversed());

        for (User user : sortedUsers) {
            sb.append(String.format("%-15s | %-10d | %-10d | %-10d\n", user.getName(), user.getHighestScore(), user.getLevelsPassed(), user.getGamesPlayed()));
        }
        return sb.toString().trim();
    }

    public String sortLeaderBoard(String criteria) {
        ArrayList<User> users = Data.getAllUsers();
        if (users == null || users.isEmpty()) return "No users available.";

        ArrayList<User> sortedUsers = new ArrayList<>(users);
        StringBuilder sb = new StringBuilder();

        if (criteria.equalsIgnoreCase("score")) {
            sb.append("Sorting by MeowPoint...\n");
            sortedUsers.sort(Comparator.comparingInt(User::getHighestScore).reversed());
        } else if (criteria.equalsIgnoreCase("level")) {
            sb.append("Sorting by Levels Passed...\n");
            sortedUsers.sort(Comparator.comparingInt(User::getLevelsPassed).reversed());
        }

        for (User user : sortedUsers) {
            sb.append(user.getName()).append(" - ").append(criteria).append(": ")
                    .append(criteria.equalsIgnoreCase("score") ? user.getHighestScore() : user.getLevelsPassed()).append("\n");
        }
        return sb.toString().trim();
    }
}