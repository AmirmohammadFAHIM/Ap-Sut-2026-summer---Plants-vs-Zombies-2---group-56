package view;

import controllers.menus.SecondaryMenus.News;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsView extends View {
    public NewsView() {
        menu = new News();
    }

    @Override
    public void input() {
        System.out.println("=== News Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher unreadMatcher = Pattern.compile(RegexHelper.NEWS_SHOW_UNREAD).matcher(input);
        Matcher allMatcher = Pattern.compile(RegexHelper.NEWS_SHOW_ALL).matcher(input);
        News newsMenu = (News) menu;

        if (unreadMatcher.matches()) {
            newsMenu.ShowNews();
        } else if (allMatcher.matches()) {
            newsMenu.ShowAllNews();
        } else {
            System.out.println("Invalid command!");
        }
    }
}