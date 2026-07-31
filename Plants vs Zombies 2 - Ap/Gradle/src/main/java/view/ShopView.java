// file: Plants vs Zombies 2 - Ap/src/view/ShopView.java
package view;

import controllers.menus.SecondaryMenus.Shop;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView extends View {
    public ShopView() { menu = new Shop(); }

    @Override
    public void input() {
        System.out.println("=== Shop Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher listMatcher = Pattern.compile(RegexHelper.SHOP_LIST).matcher(input);
        Matcher dailyMatcher = Pattern.compile(RegexHelper.SHOP_DAILY).matcher(input);
        Matcher buyMatcher = Pattern.compile(RegexHelper.SHOP_BUY).matcher(input);

        Shop shopMenu = (Shop) menu;

        if (listMatcher.matches()) {
            System.out.println("1: Pot (2000 Coins) | 2: Plant Food (3 Diamonds) | 3: Random Seed (1000 Coins) | 4: Specific Seed (5 Diamonds) | 5: Exchange (5 Diamonds -> 500 Coins)");
        } else if (dailyMatcher.matches()) {
            System.out.println(shopMenu.setDailyOffer());
        } else if (buyMatcher.matches()) {
            int itemId = Integer.parseInt(buyMatcher.group("itemId"));
            int count = Integer.parseInt(buyMatcher.group("count"));
            String plantType = buyMatcher.group("plantType");

            switch (itemId) {
                case 1: System.out.println(shopMenu.purchase("pot", count)); break;
                case 2: System.out.println(shopMenu.purchase("plantfood", count)); break;
                case 3: for(int i=0; i<count; i++) System.out.println(shopMenu.randomPurchase()); break;
                case 4:
                    if(plantType == null) System.out.println("Error: -t <plant_type> is required for specific seeds.");
                    else for(int i=0; i<count; i++) System.out.println(shopMenu.normalPurchase(plantType.toUpperCase()));
                    break;
                case 5: System.out.println(shopMenu.purchase("exchange", count)); break;
                default: System.out.println("Error: Invalid item ID.");
            }
        } else {
            System.out.println("Invalid command!");
        }
    }
}