package view;

import controllers.menus.SecondaryMenus.LeaderBoard;
import models.utils.RegexHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeaderBoardView extends View {

    public LeaderBoardView() {
        menu = new LeaderBoard();
    }

    @Override
    public void input() {
        System.out.println("=== LeaderBoard Menu ===");
        super.input();

        if (handleGlobalCommands(input)) {
            return;
        }

        if (input.matches("(?i)^menu\\s+exit$")) {
            menu.exitMenu();
            return;
        }

        Matcher showMatcher = Pattern.compile(RegexHelper.LEADERBOARD_SHOW).matcher(input);
        Matcher sortMatcher = Pattern.compile(RegexHelper.LEADERBOARD_SORT).matcher(input);

        LeaderBoard leaderBoardMenu = (LeaderBoard) menu;

        if (showMatcher.matches()) {
            leaderBoardMenu.showLeaderBoard();
        }
        else if (sortMatcher.matches()) {
            String criteria = sortMatcher.group("criteria");
            leaderBoardMenu.sortLeaderBoard(criteria);
        }
        else {
            System.out.println("Invalid command!");
        }
    }
}