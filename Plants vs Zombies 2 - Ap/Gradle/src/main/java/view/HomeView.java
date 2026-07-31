package view;

import controllers.menus.Home;
import models.App;

public class HomeView extends View {
    public HomeView() { menu = new Home(); }

    @Override
    public void input() {
        System.out.println("=== Home Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        if (input.matches("(?i)^menu\\s+logout$")) {
            System.out.println(((Home) menu).LogOut());
            App.setScreen(new SignUpView());
        } else {
            System.out.println("Invalid command!");
        }
    }
}