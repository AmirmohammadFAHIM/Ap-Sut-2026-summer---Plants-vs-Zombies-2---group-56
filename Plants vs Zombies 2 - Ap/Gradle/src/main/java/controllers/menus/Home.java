package controllers.menus;

import controllers.datacontroller.Data;
import models.App;
import view.NewsView;
import view.PlayView;
import view.ProfileView;
import view.SettingsView;

public class Home implements Menu {
    @Override
    public String ChangeMenu(String menuName) {
        return switch (menuName) {
            case "Play menu" -> { App.setScreen(new PlayView()); yield "Changed menu successfully to Play menu"; }
            case "Setting menu" -> { App.setScreen(new SettingsView()); yield "Changed menu successfully to Settings menu"; }
            case "News menu" -> { App.setScreen(new NewsView()); yield "Changed menu successfully to News menu"; }
            case "Profile menu" -> { App.setScreen(new ProfileView()); yield "Changed menu successfully to Profile menu"; }
            default -> "The Menu you have chosen is not available from Home menu";
        };
    }

    @Override
    public String exitMenu() {
        return "Error: Use 'menu logout' to exit Home menu.";
    }

    @Override
    public String ShowCurrentMenu() {
        return "--- Home Menu ---";
    }

    public String LogOut() {
        Data.setCurrentUser(null);
        return "Logged out successfully.";
    }
}