package view;

import controllers.menus.Menu;

import java.util.Scanner;

public class View implements MenuView {
    protected Menu menu;
    public static Scanner scanner = new Scanner(System.in);
    protected String input;
    @Override
    public void input() {
        input = scanner.nextLine();
    }


}
