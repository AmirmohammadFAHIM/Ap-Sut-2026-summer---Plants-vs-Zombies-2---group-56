package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;

public class Settings implements Menu {
    @Override
    public String ChangeMenu(String menuName) { return "Invalid menu transition from this menu."; }

    @Override
    public String exitMenu() {
        App.setScreen(new view.HomeView());
        return "Returned to Home Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- Settings Menu ---"; }

    public String ChangeHardness(int difficultyLevel) {
        if (difficultyLevel >= 1 && difficultyLevel <= 5) {
            User currentUser = Data.getCurrentUser();
            if (currentUser != null) {
                currentUser.setDifficultyLevel(difficultyLevel);
                Data.saveUser();
                return "Difficulty level successfully changed to: " + difficultyLevel;
            }
            return "Error: User not found.";
        }
        return "Error: difficulty level must be between 1 and 5.";
    }
}