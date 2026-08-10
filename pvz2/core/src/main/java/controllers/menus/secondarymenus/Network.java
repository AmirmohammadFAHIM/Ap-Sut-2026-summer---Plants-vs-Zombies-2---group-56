package controllers.menus.secondarymenus;

import controllers.menus.Menu;
import models.App;

public class Network implements Menu {
    @Override
    public String ChangeMenu(String menuName) { return "Invalid menu transition from this menu."; }

    @Override
    public String exitMenu() {
        App.setScreen(new view.HomeView());
        return "Returned to Home Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- Network Menu ---\nNetwork features will be fully available in the upcoming phases."; }

    public String connectToServer(String ip, int port) {
        return "Attempting to connect to server at " + ip + ":" + port + "...\nStatus: Waiting for Phase 2 implementation.";
    }

    public String hostServer(int port) {
        return "Starting server on port " + port + "...\nStatus: Waiting for Phase 2 implementation.";
    }
}