package view;

import controllers.menus.LogIn;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInView extends View {
    public LogInView() {
        menu = new LogIn();
    }

    @Override
    public void input() {
        System.out.println("Welcome to Log In View");
        super.input();

        if (handleGlobalCommands(input)) {
            return;
        }

        Matcher loginMatcher = Pattern.compile(RegexHelper.LOGIN_COMMAND_PATTERN).matcher(input);
        Matcher forgetMatcher = Pattern.compile(RegexHelper.FORGET_PASSWORD_COMMAND).matcher(input);

        if (loginMatcher.matches()) {
            String username = loginMatcher.group("username");
            String password = loginMatcher.group("password");
            boolean stayLoggedIn = loginMatcher.group("stayLoggedIn") != null;

            ((LogIn) menu).login(username, password, stayLoggedIn);
        }
        else if (forgetMatcher.matches()) {
            String username = forgetMatcher.group("username");
            String email = forgetMatcher.group("email");

            System.out.println("Please answer your security question using format: answer -a <answer>");
            String answerInput = scanner.nextLine().trim();
            Matcher answerMatcher = Pattern.compile(RegexHelper.ANSWER_COMMAND).matcher(answerInput);

            if (answerMatcher.matches()) {
                String answer = answerMatcher.group("answer");
                System.out.println("Please enter your new password:");
                String newPassword = scanner.nextLine().trim();
                ((LogIn) menu).resetPassword(username, answer, newPassword);
            } else {
                System.out.println("Error: Invalid answer format. Returning to start of Log In menu.");
            }
        }
        else {
            System.out.println("Invalid command!");
        }
    }
}