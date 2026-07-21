package controllers.menus;

import controllers.datacontroller.Data;
import models.App;
import models.User;

import java.security.MessageDigest;

public class LogIn implements Menu {

    @Override
    public String ChangeMenu(String menuName) {
        return "Invalid menu transition from Log In menu.";
    }

    @Override
    public void exitMenu() {
        App.setScreen(new view.SignUpView());
        System.out.println("Returned to Sign Up Menu.");
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Log In Menu ---");
    }

    public void login(String username, String password, boolean stayLoggedIn) {
        User user = Data.getUserByUsername(username);

        if (user == null) {
            System.out.println("Error: username does not exist.");
            return;
        }

        String hashedInput = hashPassword(password);
        if (!user.getPasswordHash().equals(hashedInput)) {
            System.out.println("Error: incorrect password.");
            return;
        }

        user.setStayLoggedIn(stayLoggedIn);
        Data.setCurrentUser(user);
        Data.saveUser();
        System.out.println("Logged in successfully.");
    }

    public void resetPassword(String username, String answer, String newPassword) {
        User user = Data.getUserByUsername(username);

        if (user == null) {
            System.out.println("Error: username does not exist.");
            return;
        }

        if (!user.checkSecurityAnswer(answer)) {
            System.out.println("Error: incorrect security answer.");
            return;
        }

        String hashedNewPassword = hashPassword(newPassword);
        user.setPasswordHash(hashedNewPassword);
        Data.saveUser();

        System.out.println("Password reset successfully. You can now log in.");
    }

    public void stayLoggedIn() {
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