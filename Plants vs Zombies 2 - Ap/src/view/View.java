package view;

import controllers.menus.Menu;
import models.App;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View implements MenuView {
    protected Menu menu;
    public static Scanner scanner = App.getInput();
    protected String input;

    @Override
    public void input() {
        input = scanner.nextLine().trim();
    }

    protected boolean handleGlobalCommands(String command) {
        Matcher enterMatcher = Pattern.compile("(?i)^menu\\s+enter\\s+(?<menuName>.+)$").matcher(command);

        if (command.matches("(?i)^menu\\s+show\\s+current$")) {
            System.out.println(menu.ShowCurrentMenu());
            return true;
        } else if (command.matches("(?i)^menu\\s+exit$")) {
            System.out.println(menu.exitMenu());
            return true;
        } else if (enterMatcher.matches()) {
            String targetMenu = enterMatcher.group("menuName").trim();
            targetMenu = targetMenu.substring(0, 1).toUpperCase() + targetMenu.substring(1).toLowerCase();
            if (!targetMenu.endsWith(" menu")) {
                targetMenu += " menu";
            }
            System.out.println(menu.ChangeMenu(targetMenu));
            return true;
        }
        return false;
    }
}