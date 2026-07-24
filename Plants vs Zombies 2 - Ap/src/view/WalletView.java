package view;

import controllers.menus.SecondaryMenus.Wallet;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalletView extends View {
    public WalletView() { menu = new Wallet(); }

    @Override
    public void input() {
        System.out.println("=== Wallet Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher coinMatcher = Pattern.compile(RegexHelper.WALLET_SHOW_COIN).matcher(input);
        Matcher gemMatcher = Pattern.compile(RegexHelper.WALLET_SHOW_GEM).matcher(input);
        Matcher cheatMatcher = Pattern.compile(RegexHelper.WALLET_CHEAT).matcher(input);

        if (coinMatcher.matches()) System.out.println(((Wallet) menu).showCoinWallet());
        else if (gemMatcher.matches()) System.out.println(((Wallet) menu).showGemWallet());
        else if (cheatMatcher.matches()) {
            System.out.println(((Wallet) menu).cheatAdd(Integer.parseInt(cheatMatcher.group("amount")), cheatMatcher.group("type")));
        } else {
            System.out.println("Invalid command!");
        }
    }
}