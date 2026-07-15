package controllers.menus.SecondaryMenus;

import controllers.dataController.Data;
import controllers.menus.Menu;
import models.User;
import models.utils.RegexHelper;

import java.security.MessageDigest;
import java.util.regex.Pattern;

public class Profile implements Menu {

    @Override
    public void ChangeMenu() {
    }

    @Override
    public void ShowCurrentMenu() {
        System.out.println("--- Profile Menu ---");
    }

    public void showProfile() {
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            System.out.println("Username: " + currentUser.getName());
            System.out.println("Nickname: " + currentUser.getNickname());
            System.out.println("Games Played: " + currentUser.getGamesPlayed());
            System.out.println("Coins: " + currentUser.getCoins());
            System.out.println("Diamonds: " + currentUser.getDiamonds());
            System.out.println("Levels Passed: " + currentUser.getLevelsPassed());
            System.out.println("Highest MeowPoint: " + currentUser.getHighestScore());
        }
    }

    public void ChangeNickName(String newNickname) {
        if (newNickname == null || newNickname.length() < 3 || newNickname.length() > 30) {
            System.out.println("Error: nickname length is invalid.");
            return;
        }
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getNickname().equals(newNickname)) {
                System.out.println("Error: new nickname cannot be the same as the current one.");
                return;
            }
            currentUser.setNickname(newNickname);
            Data.saveUser();
            System.out.println("Nickname changed successfully.");
        }
    }

    public void ChangeEmail(String newEmail) {
        if (newEmail == null || newEmail.length() - newEmail.replace("@", "").length() != 1 || !Pattern.matches(RegexHelper.EMAIL_PATTERN, newEmail)) {
            System.out.println("Error: email format is invalid.");
            return;
        }
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getEmail().equals(newEmail)) {
                System.out.println("Error: new email cannot be the same as the current one.");
                return;
            }
            currentUser.setEmail(newEmail);
            Data.saveUser();
            System.out.println("Email changed successfully.");
        }
    }

    public void ChangePassword(String oldPassword, String newPassword) {
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            String hashedOld = hashPassword(oldPassword);
            if (!currentUser.getPasswordHash().equals(hashedOld)) {
                System.out.println("Error: incorrect old password.");
                return;
            }
            if (oldPassword.equals(newPassword)) {
                System.out.println("Error: new password cannot be the same as the old password.");
                return;
            }
            if (!Pattern.matches(RegexHelper.PASSWORD_PATTERN, newPassword)) {
                System.out.println("Error: weak password.");
                return;
            }

            String hashedNew = hashPassword(newPassword);
            currentUser.setPasswordHash(hashedNew);
            Data.saveUser();
            System.out.println("Password changed successfully.");
        }
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