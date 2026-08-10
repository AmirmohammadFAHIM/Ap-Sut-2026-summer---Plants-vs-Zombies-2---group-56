package controllers.menus.secondarymenus;

import controllers.datacontroller.Data;
import models.App;
import models.User;
import controllers.menus.Menu;

public class GreenHouseController implements Menu {
    @Override
    public String ChangeMenu(String menuName) {
        if (menuName.equalsIgnoreCase("Shop menu")) {
            App.setScreen(new view.ShopView());
            return "Changed menu successfully to Shop menu";
        }
        return "Invalid menu transition from GreenHouse menu.";
    }

    @Override
    public String exitMenu() {
        App.setScreen(new view.PlayView());
        return "Returned to Play Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- GreenHouse Menu ---"; }

    public String showgreenhouse() {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";
        return user.getGreenHouse().showAll();
    }

    public String plant(int x, int y) {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";

        String result = user.getGreenHouse().plantPot(x, y);
        Data.saveUser();
        return result;
    }

    public String forceGrow(int x, int y, int remainingHours) {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";

        String result = user.getGreenHouse().growNow(x, y);
        Data.saveUser();
        return result;
    }

    public String collect(int x, int y, boolean isMarigold) {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not logged in.";

        String result = user.getGreenHouse().collectPot(x, y);
        Data.saveUser();
        return result;
    }
}