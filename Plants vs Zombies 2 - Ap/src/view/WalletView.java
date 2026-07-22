package view;

import controllers.menus.SecondaryMenus.Wallet;
import models.utils.RegexHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalletView extends View {

    public WalletView() {
        menu = new Wallet(); // اتصال به کنترلر Wallet
    }

    @Override
    public void input() {
        System.out.println("=== Wallet Menu ===");
        super.input();

        if (handleGlobalCommands(input)) {
            return;
        }

        if (input.matches("(?i)^menu\\s+exit$")) {
            menu.exitMenu();
            return;
        }

        Matcher coinMatcher = Pattern.compile(RegexHelper.WALLET_SHOW_COIN).matcher(input);
        Matcher gemMatcher = Pattern.compile(RegexHelper.WALLET_SHOW_GEM).matcher(input);
        Matcher cheatMatcher = Pattern.compile(RegexHelper.WALLET_CHEAT).matcher(input);

        Wallet walletMenu = (Wallet) menu;

        if (coinMatcher.matches()) {
            walletMenu.showCoinWallet();
        }
        else if (gemMatcher.matches()) {
            walletMenu.showGemWallet();
        }
        else if (cheatMatcher.matches()) {
            int amount = Integer.parseInt(cheatMatcher.group("amount"));
            String type = cheatMatcher.group("type");
            walletMenu.cheatAdd(amount, type);
        }
        else {
            System.out.println("Invalid command!");
        }
    }
}