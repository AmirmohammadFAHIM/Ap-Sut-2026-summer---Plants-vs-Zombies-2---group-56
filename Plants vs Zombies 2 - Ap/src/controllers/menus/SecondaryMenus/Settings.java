package controllers.menus.SecondaryMenus;

import controllers.dataController.Data;
import controllers.menus.Menu;
import models.User;

public class Settings implements Menu {

    @Override
    public void ChangeMenu() {
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