package view;

import controllers.menus.SecondaryMenus.Profile;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileView extends View {

    public ProfileView() {
        menu = new Profile();
    }

    @Override
    public void input() {
        System.out.println("=== Profile Menu ===");
        super.input();

        if (handleGlobalCommands(input)) {
            return;
        }

        Matcher infoMatcher = Pattern.compile(RegexHelper.PROFILE_SHOW_INFO).matcher(input);
        Matcher usernameMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_USERNAME).matcher(input);
        Matcher nicknameMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_NICKNAME).matcher(input);
        Matcher emailMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_EMAIL).matcher(input);
        Matcher passwordMatcher = Pattern.compile(RegexHelper.PROFILE_CHANGE_PASSWORD).matcher(input);

        if (infoMatcher.matches()) {
            ((Profile) menu).showProfile();
        }
        else if (usernameMatcher.matches()) {
            String newUsername = usernameMatcher.group("username");
            ((Profile) menu).ChangeUserName(newUsername);
        }
        else if (nicknameMatcher.matches()) {
            String newNickname = nicknameMatcher.group("nickname");
            ((Profile) menu).ChangeNickName(newNickname);
        }
        else if (emailMatcher.matches()) {
            String newEmail = emailMatcher.group("email");
            ((Profile) menu).ChangeEmail(newEmail);
        }
        else if (passwordMatcher.matches()) {
            String newPassword = passwordMatcher.group("newPassword");
            String oldPassword = passwordMatcher.group("oldPassword");
            ((Profile) menu).ChangePassword(oldPassword, newPassword);
        }
        else {
            System.out.println("Invalid command!");
        }
    }
}