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
    public void exitMenu() {
        System.out.println("Exiting program...");
        System.exit(0);
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Sign Up Menu ---");
    }

    public void register(String username, String password, String passwordConfirm,
                         String nickname, String email, String gender) {

        if (!isValidUsername(username)) {
            System.out.println("Error: username format is invalid.");
            return;
        }


        if (Data.isUsernameExists(username)) {
            System.out.println("Error: username is already taken.");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            System.out.println("Error: passwords does not match.");
            return;
        }

        if (!isValidPassword(password)) {
            System.out.println("Error: weak password. A strong password must be at least 8 characters long" +
                    " and contain uppercase letters, lowercase letters, numbers, and special symbols.");
            return;
        }

        System.out.println("password passed");

        if (!isValidNickname(nickname)) {
            System.out.println("Error: nickname length is invalid. It must be between 3 and 30 characters.");
            return;
        }

        System.out.println("nickname passed");
        if (!isValidEmail(email)) {
            System.out.println("Error: email format is invalid.");
            return;
        }
        System.out.println("email passed");

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword, nickname, email, gender);
        Data.setTempUser(newUser);
    }

    public void pickQuestion(int questionNumber, String answer, String answerConfirm) {
        if (!answer.equals(answerConfirm)) {
            System.out.println("Error: security answers do not match.");
            return;
        }

        User tempUser = Data.getTempUser();
        if (tempUser != null) {
            tempUser.setSecurityQuestion(questionNumber, answer);
            Data.addUser(tempUser);
            Data.setTempUser(null);

            System.out.println("User created successfully.");
        } else {
            System.out.println("Error: you must register first before picking a security question.");
        }
    }

    private boolean isValidUsername(String username) {
        return Pattern.matches(RegexHelper.USERNAME_PATTERN, username);
    }

    private boolean isValidPassword(String password) {
        return Pattern.matches(RegexHelper.PASSWORD_PATTERN, password);
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.length() - email.replace("@", "").length() != 1) {
            return false;
        }
        return Pattern.matches(RegexHelper.EMAIL_PATTERN, email);
    }

    private boolean isValidNickname(String nickname) {
        return nickname != null && nickname.length() >= 3 && nickname.length() <= 30;
    }

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