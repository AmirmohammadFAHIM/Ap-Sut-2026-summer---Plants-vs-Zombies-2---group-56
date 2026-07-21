package view;

import controllers.menus.SecondaryMenus.Settings;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsView extends View {
    public SettingsView() {
        menu = new Settings();
    }

    @Override
    public void input() {
        System.out.println("=== Settings Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher difficultyMatcher = Pattern.compile(RegexHelper.SETTINGS_CHANGE_DIFFICULTY).matcher(input);
        Settings settingsMenu = (Settings) menu;

        if (difficultyMatcher.matches()) {
            int level = Integer.parseInt(difficultyMatcher.group("level"));
            settingsMenu.ChangeHardness(level);
        } else {
            System.out.println("Invalid command!");
        }
    }
}