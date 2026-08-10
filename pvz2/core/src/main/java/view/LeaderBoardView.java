package view;

import controllers.menus.secondarymenus.LeaderBoard;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeaderBoardView extends View {
    public LeaderBoardView() { menu = new LeaderBoard(); }

    @Override
    public void input() {
        System.out.println("=== LeaderBoard Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher showMatcher = Pattern.compile(RegexHelper.LEADERBOARD_SHOW).matcher(input);
        Matcher sortMatcher = Pattern.compile(RegexHelper.LEADERBOARD_SORT).matcher(input);

        if (showMatcher.matches()) {
            System.out.println(((LeaderBoard) menu).showLeaderBoard());
        } else if (sortMatcher.matches()) {
            System.out.println(((LeaderBoard) menu).sortLeaderBoard(sortMatcher.group("criteria")));
        } else {
            System.out.println("Invalid command!");
        }
    }
}