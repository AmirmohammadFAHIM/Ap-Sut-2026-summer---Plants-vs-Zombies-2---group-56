package controllers.menus.SecondaryMenus;

import controllers.menus.Menu;
import models.App;

public class Network implements Menu {

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
        System.out.println("--- Network Menu ---");
        System.out.println("Network features will be fully available in the upcoming phases.");
    }

    public void connectToServer(String ip, int port) {
        System.out.println("Attempting to connect to server at " + ip + ":" + port + "...");
        System.out.println("Status: Waiting for Phase 2 implementation.");
    }

    public void hostServer(int port) {
        System.out.println("Starting server on port " + port + "...");
        System.out.println("Status: Waiting for Phase 2 implementation.");
    }
}