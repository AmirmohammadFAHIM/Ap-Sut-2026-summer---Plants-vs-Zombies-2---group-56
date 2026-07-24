package view;

import controllers.datacontroller.Data;
import controllers.menus.gameController.PlayMenu;
import models.GameAdventure.Chapters;
import models.User;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayView extends View {
    public PlayView() { menu = new PlayMenu(); }

    @Override
    public void input() {
        System.out.println("=== Play Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher chapterMatcher = Pattern.compile(RegexHelper.PLAY_ENTER_CHAPTER).matcher(input);
        Matcher shortcutMatcher = Pattern.compile(RegexHelper.PLAY_SHORTCUTS).matcher(input);
        Matcher cheatMatcher = Pattern.compile(RegexHelper.PLAY_CHEAT).matcher(input);
        Matcher playMatcher = Pattern.compile(RegexHelper.PLAY_LEVEL).matcher(input);

        if (chapterMatcher.matches()) {
            try {
                Chapters selectedChapter = Chapters.valueOf(chapterMatcher.group("chaptername"));
                System.out.println(((PlayMenu) menu).changeChapter(selectedChapter));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Chapter not found! Available chapters: AncientEgypt, FrozenCaves, BigWaveBeach, DarkAge");
            }
        } else if (shortcutMatcher.matches()) {
            String shortcut = shortcutMatcher.group("shortcut").toLowerCase();
            String targetMenu = switch (shortcut) {
                case "greenhouse" -> "Greenhouse menu";
                case "travel-log" -> "Quests menu";
                case "leaderboard" -> "Leaderboard menu";
                case "coin-wallet", "gem-wallet" -> "Wallet menu";
                default -> "";
            };
            System.out.println(menu.ChangeMenu(targetMenu));
        } else if (playMatcher.matches()) {
            System.out.println(((PlayMenu) menu).play(Integer.parseInt(playMatcher.group("level"))));
        } else if (cheatMatcher.matches()) {
            int amount = Integer.parseInt(cheatMatcher.group("amount"));
            String type = cheatMatcher.group("type").toLowerCase();
            User currentUser = Data.getCurrentUser();
            if (currentUser != null) {
                if (type.startsWith("coin")) {
                    currentUser.addCoins(amount);
                    System.out.println("Cheat activated: Added " + amount + " coins!");
                } else if (type.startsWith("diamond")) {
                    currentUser.addDiamonds(amount);
                    System.out.println("Cheat activated: Added " + amount + " diamonds!");
                }
                Data.saveUser();
            }
        } else {
            System.out.println("Invalid command!");
        }
    }
}