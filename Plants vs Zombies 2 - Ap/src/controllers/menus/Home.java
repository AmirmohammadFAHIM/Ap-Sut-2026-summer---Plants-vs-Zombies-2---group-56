package controllers.menus;

import controllers.dataController.Data;

public class Home implements Menu {

    @Override
    public void ChangeMenu() {
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Home Menu ---");
    }

    public void LogOut() {
        Data.setCurrentUser(null);
        System.out.println("Logged out successfully.");
    }
}