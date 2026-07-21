package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;

public class Settings implements Menu {

    @Override
    public String ChangeMenu(String menuName) {
        return "Invalid menu transition from this menu.";
    }

    @Override
    public void exitMenu() {
        App.setScreen(new view.HomeView());
        System.out.println("Returned to Home Menu.");
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Settings Menu ---");
    }

    public void ChangeHardness(int difficultyLevel) {
        if (difficultyLevel >= 1 && difficultyLevel <= 5) {
            User currentUser = Data.getCurrentUser();
            if (currentUser != null) {
                currentUser.setDifficultyLevel(difficultyLevel);
                Data.saveUser();
                System.out.println("Difficulty level successfully changed to: " + difficultyLevel);
            }
        } else {
            System.out.println("Error: difficulty level must be between 1 and 5.");
        }
    }
}