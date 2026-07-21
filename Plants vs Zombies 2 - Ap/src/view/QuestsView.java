package view;

import controllers.menus.SecondaryMenus.Quests;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestsView extends View {
    public QuestsView() {
        menu = new Quests();
    }

    @Override
    public void input() {
        System.out.println("=== Travel Log Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher changePageMatcher = Pattern.compile(RegexHelper.QUESTS_CHANGE_PAGE).matcher(input);
        Matcher showQuestsMatcher = Pattern.compile(RegexHelper.QUESTS_SHOW).matcher(input);

        Quests questsMenu = (Quests) menu;

        if (changePageMatcher.matches()) {
            questsMenu.changePage(changePageMatcher.group("pageName"));
        } else if (showQuestsMatcher.matches()) {
            questsMenu.showQuests();
        } else {
            System.out.println("Invalid command!");
        }
    }
}