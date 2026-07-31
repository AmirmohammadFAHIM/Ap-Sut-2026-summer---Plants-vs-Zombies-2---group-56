package controllers.menus;

import controllers.datacontroller.Data;
import models.App;
import models.User;
import models.utils.RegexHelper;

import java.security.MessageDigest;
import java.util.regex.Pattern;

public class SignUp implements Menu {
    @Override
    public String ChangeMenu(String menuName) {
        if (menuName.equalsIgnoreCase("Login menu")) {
            App.setScreen(new view.LogInView());
            return "Changed menu successfully to Login menu";
        }
        return "Invalid menu transition from Sign Up menu.";
    }

    @Override
    public String exitMenu() {
        System.exit(0);
        return "Exiting program...";
    }

    @Override
    public String ShowCurrentMenu() {
        return "--- Sign Up Menu ---";
    }

    public String register(String username, String password, String passwordConfirm, String nickname, String email, String gender) {
        if (!isValidUsername(username)) return "Error: username format is invalid.";
        if (Data.isUsernameExists(username)) return "Error: username is already taken.";
        if (!password.equals(passwordConfirm)) return "Error: passwords does not match.";
        if (!isValidPassword(password)) return "Error: weak password. Must be at least 8 characters with letters, numbers, and symbols.";
        if (!isValidNickname(nickname)) return "Error: nickname length is invalid. It must be between 3 and 30 characters.";
        if (!isValidEmail(email)) return "Error: email format is invalid.";

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword, nickname, email, gender);
        Data.setTempUser(newUser);
        return "Data valid. Pick a security question.";
    }

    public String pickQuestion(int questionNumber, String answer, String answerConfirm) {
        if (!answer.equals(answerConfirm)) return "Error: security answers do not match.";

        User tempUser = Data.getTempUser();
        if (tempUser != null) {
            tempUser.setSecurityQuestion(questionNumber, answer);
            Data.addUser(tempUser);
            Data.setTempUser(null);
            Data.saveUser();
            return "User created successfully.";
        }
        return "Error: you must register first before picking a security question.";
    }

    private boolean isValidUsername(String username) { return Pattern.matches(RegexHelper.USERNAME_PATTERN, username); }
    private boolean isValidPassword(String password) { return Pattern.matches(RegexHelper.PASSWORD_PATTERN, password); }
    private boolean isValidEmail(String email) {
        if (email == null || email.length() - email.replace("@", "").length() != 1) return false;
        return Pattern.matches(RegexHelper.EMAIL_PATTERN, email);
    }
    private boolean isValidNickname(String nickname) { return nickname != null && nickname.length() >= 3 && nickname.length() <= 30; }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}