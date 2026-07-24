package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;
import models.utils.RegexHelper;

import java.security.MessageDigest;
import java.util.regex.Pattern;

public class Profile implements Menu {
    @Override
    public String ChangeMenu(String menuName) { return "Invalid menu transition from this menu."; }

    @Override
    public String exitMenu() {
        App.setScreen(new view.HomeView());
        return "Returned to Home Menu.";
    }

    @Override
    public String ShowCurrentMenu() { return "--- Profile Menu ---"; }

    public String showProfile() {
        User currentUser = Data.getCurrentUser();
        if (currentUser == null) return "Error: User not found.";
        StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(currentUser.getName()).append("\n");
        sb.append("Nickname: ").append(currentUser.getNickname()).append("\n");
        sb.append("Games Played: ").append(currentUser.getGamesPlayed()).append("\n");
        sb.append("Coins: ").append(currentUser.getCoins()).append("\n");
        sb.append("Diamonds: ").append(currentUser.getDiamonds()).append("\n");
        sb.append("Levels Passed: ").append(currentUser.getLevelsPassed()).append("\n");
        sb.append("Highest MeowPoint: ").append(currentUser.getHighestScore());
        return sb.toString();
    }

    public String ChangeUserName(String newUsername) {
        User currentUser = Data.getCurrentUser();
        if (currentUser == null) return "Error: User not found.";
        if (currentUser.getName().equals(newUsername)) return "Error: new username cannot be the same as the current one.";
        if (!Pattern.matches(RegexHelper.USERNAME_PATTERN, newUsername)) return "Error: username format is invalid.";
        if (Data.isUsernameExists(newUsername)) return "Error: username is already taken.";

        currentUser.setName(newUsername);
        Data.saveUser();
        return "Username changed successfully.";
    }

    public String ChangeNickName(String newNickname) {
        if (newNickname == null || newNickname.length() < 3 || newNickname.length() > 30) return "Error: nickname length is invalid.";
        User currentUser = Data.getCurrentUser();
        if (currentUser == null) return "Error: User not found.";
        if (currentUser.getNickname().equals(newNickname)) return "Error: new nickname cannot be the same as the current one.";

        currentUser.setNickname(newNickname);
        Data.saveUser();
        return "Nickname changed successfully.";
    }

    public String ChangeEmail(String newEmail) {
        if (newEmail == null || newEmail.length() - newEmail.replace("@", "").length() != 1 || !Pattern.matches(RegexHelper.EMAIL_PATTERN, newEmail)) {
            return "Error: email format is invalid.";
        }
        User currentUser = Data.getCurrentUser();
        if (currentUser == null) return "Error: User not found.";
        if (currentUser.getEmail().equals(newEmail)) return "Error: new email cannot be the same as the current one.";

        currentUser.setEmail(newEmail);
        Data.saveUser();
        return "Email changed successfully.";
    }

    public String ChangePassword(String oldPassword, String newPassword) {
        User currentUser = Data.getCurrentUser();
        if (currentUser == null) return "Error: User not found.";
        String hashedOld = hashPassword(oldPassword);
        if (!currentUser.getPasswordHash().equals(hashedOld)) return "Error: incorrect old password.";
        if (oldPassword.equals(newPassword)) return "Error: new password cannot be the same as the old password.";
        if (!Pattern.matches(RegexHelper.PASSWORD_PATTERN, newPassword)) return "Error: weak password.";

        String hashedNew = hashPassword(newPassword);
        currentUser.setPasswordHash(hashedNew);
        Data.saveUser();
        return "Password changed successfully.";
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