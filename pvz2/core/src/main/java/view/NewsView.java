package view;

import controllers.menus.secondarymenus.News;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsView extends View {
    public NewsView() { menu = new News(); }

    @Override
    public void input() {
        System.out.println("=== News Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher unreadMatcher = Pattern.compile(RegexHelper.NEWS_SHOW_UNREAD).matcher(input);
        Matcher allMatcher = Pattern.compile(RegexHelper.NEWS_SHOW_ALL).matcher(input);

        if (unreadMatcher.matches()) System.out.println(((News) menu).ShowNews());
        else if (allMatcher.matches()) System.out.println(((News) menu).ShowAllNews());
        else System.out.println("Invalid command!");
    }
}