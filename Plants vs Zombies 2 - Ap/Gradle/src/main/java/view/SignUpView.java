package view;

import controllers.menus.SignUp;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpView extends View {
    public SignUpView() { menu = new SignUp(); }

    @Override
    public void input() {
        System.out.println("Warm Welcome to Plants vs Zombies 2! Please sign up to start the game.");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher registerMatcher = Pattern.compile(RegexHelper.REGISTER_COMMAND_PATTERN).matcher(input);
        Matcher questionMatcher = Pattern.compile(RegexHelper.PICK_QUESTION_COMMAND).matcher(input);

        if (registerMatcher.matches()) {
            String username = registerMatcher.group("username");
            String password = registerMatcher.group("password");
            String passwordConfirm = registerMatcher.group("passwordConfirm");
            String nickname = registerMatcher.group("nickname");
            String email = registerMatcher.group("email");
            String gender = registerMatcher.group("gender");

            System.out.println(((SignUp) menu).register(username, password, passwordConfirm, nickname, email, gender));
        } else if (questionMatcher.matches()) {
            int questionNumber = Integer.parseInt(questionMatcher.group("questionNumber"));
            String answer = questionMatcher.group("answer");
            String answerConfirm = questionMatcher.group("answerConfirm");

            System.out.println(((SignUp) menu).pickQuestion(questionNumber, answer, answerConfirm));
        } else {
            System.out.println("Invalid command!");
        }
    }
}