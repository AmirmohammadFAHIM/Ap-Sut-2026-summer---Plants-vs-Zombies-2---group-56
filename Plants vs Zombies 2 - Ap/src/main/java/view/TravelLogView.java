package view;

import controllers.menus.secondarymenus.TravelLog;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TravelLogView extends View {
    public TravelLogView() { menu = new TravelLog(); }

    @Override
    public void input() {
        System.out.println("=== Travel Log Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher changePageMatcher = Pattern.compile(RegexHelper.QUESTS_CHANGE_PAGE).matcher(input);
        Matcher showQuestsMatcher = Pattern.compile(RegexHelper.QUESTS_SHOW).matcher(input);

        if (changePageMatcher.matches()) {
            System.out.println(((TravelLog) menu).changePage(changePageMatcher.group("pageName")));
        } else if (showQuestsMatcher.matches()) {
            System.out.println(((TravelLog) menu).showQuests());
        } else {
            System.out.println("Invalid command!");
        }
    }
}