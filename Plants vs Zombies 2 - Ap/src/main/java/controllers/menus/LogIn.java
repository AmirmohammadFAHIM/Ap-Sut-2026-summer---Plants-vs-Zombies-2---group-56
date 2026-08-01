package controllers.menus;

import controllers.datacontroller.Data;
import models.App;
import models.User;
import view.HomeView;

import java.security.MessageDigest;

public class LogIn implements Menu {
    @Override
    public String ChangeMenu(String menuName) {
        return "Invalid menu transition from Log In menu.";
    }

    @Override
    public String exitMenu() {
        App.setScreen(new view.SignUpView());
        return "Returned to Sign Up Menu.";
    }

    @Override
    public String ShowCurrentMenu() {
        return "--- Log In Menu ---";
    }

    public String login(String username, String password, boolean stayLoggedIn) {
        User user = Data.getUserByUsername(username);
        if (user == null) return "Error: username does not exist.";

        String hashedInput = hashPassword(password);
        if (!user.getPasswordHash().equals(hashedInput)) return "Error: incorrect password.";

        user.setStayLoggedIn(stayLoggedIn);
        Data.setCurrentUser(user);
        Data.saveUser();
        App.setScreen(new HomeView());
        return "Logged in successfully.";
    }

    public String resetPassword(String username, String answer, String newPassword) {
        User user = Data.getUserByUsername(username);
        if (user == null) return "Error: username does not exist.";
        if (!user.checkSecurityAnswer(answer)) return "Error: incorrect security answer.";

        String hashedNewPassword = hashPassword(newPassword);
        user.setPasswordHash(hashedNewPassword);
        Data.saveUser();
        return "Password reset successfully. You can now log in.";
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