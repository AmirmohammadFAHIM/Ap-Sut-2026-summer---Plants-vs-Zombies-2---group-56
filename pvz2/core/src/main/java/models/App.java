package models;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.Main;
import controllers.datacontroller.Data;
import controllers.menus.Menu;
import pvz.skin.PvzSkin;
import view.MenuView;

import java.util.Scanner;

public class App {
    public App(Main main) {
        App.main = main;
    }
    public static Scanner input = new Scanner(System.in);
    private static Menu currentmenu;
    private static User currentuser;
    private static MenuView screen;
    private static Main main;
    public static Skin skin;
    public static void setScreen(MenuView screen) {
        App.screen = screen;
        skin = PvzSkin.get();
    }
    public static void setScreen(Screen screen){
        main.setScreen(screen);
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

    public static void setCurrentuser(User currentuser) {
        App.currentuser = currentuser;
    }

    public static User getCurrentuser() {
        return Data.getCurrentUser() ;
    }

    public static Screen getScreen() {
        return main.getScreen();
    }
}
