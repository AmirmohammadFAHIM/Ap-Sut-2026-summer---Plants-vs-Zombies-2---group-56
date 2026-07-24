package view;

import controllers.menus.SecondaryMenus.Profile;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileView extends View {
    public ProfileView() { menu = new Profile(); }

    @Override
    public void input() {
        System.out.println("=== Profile Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher infoMatcher = Pattern.compile(RegexHelper.PROFILE_SHOW_INFO).matcher(input);
        Matcher usernameMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_USERNAME).matcher(input);
        Matcher nicknameMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_NICKNAME).matcher(input);
        Matcher emailMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_EMAIL).matcher(input);
        Matcher passwordMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_PASSWORD).matcher(input);

        if (infoMatcher.matches()) {
            System.out.println(((Profile) menu).showProfile());
        } else if (usernameMatcher.matches()) {
            System.out.println(((Profile) menu).ChangeUserName(usernameMatcher.group("username")));
        } else if (nicknameMatcher.matches()) {
            System.out.println(((Profile) menu).ChangeNickName(nicknameMatcher.group("nickname")));
        } else if (emailMatcher.matches()) {
            System.out.println(((Profile) menu).ChangeEmail(emailMatcher.group("email")));
        } else if (passwordMatcher.matches()) {
            System.out.println(((Profile) menu).ChangePassword(passwordMatcher.group("oldPassword"), passwordMatcher.group("newPassword")));
        } else {
            System.out.println("Invalid command!");
        }
    }
}