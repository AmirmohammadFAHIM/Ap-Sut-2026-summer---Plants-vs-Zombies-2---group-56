package models;

import controllers.dataController.Data;
import controllers.menus.Menu;
import view.MenuView;

import java.util.Scanner;

public class App {
    public static Scanner input = new Scanner(System.in);
    private static Menu currentmenu;
    private static User currentuser;
    private static MenuView screen;
    public static void setScreen(MenuView screen) {
        App.screen = screen;
    }


    public static Scanner getInput() {
        return input;
    }

    public static void setInput(Scanner input) {
        App.input = input;
    }

    public static Menu getCurrentmenu() {
        return currentmenu;
    }

    public static void setCurrentmenu(Menu currentmenu) {
        App.currentmenu = currentmenu;
    }

    public static User getCurrentuser() {
        return Data.getCurrentUser() ;
    }
}
