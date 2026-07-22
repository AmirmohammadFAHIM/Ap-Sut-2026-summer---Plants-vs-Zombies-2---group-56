package view;

import controllers.menus.SecondaryMenus.TravelLog;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TravelLogView extends View {
    public TravelLogView() {
        menu = new TravelLog();
    }

    @Override
    public void input() {
        System.out.println("=== Travel Log Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher changePageMatcher = Pattern.compile(RegexHelper.QUESTS_CHANGE_PAGE).matcher(input);
        Matcher showQuestsMatcher = Pattern.compile(RegexHelper.QUESTS_SHOW).matcher(input);

        TravelLog travelLogMenu = (TravelLog) menu;

        if (changePageMatcher.matches()) {
            travelLogMenu.changePage(changePageMatcher.group("pageName"));
        } else if (showQuestsMatcher.matches()) {
            travelLogMenu.showQuests();
        } else {
            System.out.println("Invalid command!");
        }
    }
}