package view;

import controllers.menus.secondarymenus.GreenHouseController;
import models.App;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GreenHouseView extends View {
    public GreenHouseView() { menu = new GreenHouseController(); }

    @Override
    public void input() {
        System.out.println("=== GreenHouse Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher showMatcher = Pattern.compile(RegexHelper.GREENHOUSE_SHOW).matcher(input);
        Matcher plantMatcher = Pattern.compile(RegexHelper.GREENHOUSE_PLANT).matcher(input);
        Matcher growMatcher = Pattern.compile(RegexHelper.GREENHOUSE_GROW).matcher(input);
        Matcher collectMatcher = Pattern.compile(RegexHelper.GREENHOUSE_COLLECT).matcher(input);
        Matcher enterShopMatcher = Pattern.compile(RegexHelper.GREENHOUSE_ENTER_SHOP).matcher(input);

        GreenHouseController ghController = (GreenHouseController) menu;

        if (showMatcher.matches()) System.out.println(ghController.showgreenhouse());
        else if (plantMatcher.matches()) System.out.println(ghController.plant(Integer.parseInt(plantMatcher.group("x")), Integer.parseInt(plantMatcher.group("y"))));
        else if (growMatcher.matches()) System.out.println(ghController.forceGrow(Integer.parseInt(growMatcher.group("x")), Integer.parseInt(growMatcher.group("y")), 0));
        else if (collectMatcher.matches()) System.out.println(ghController.collect(Integer.parseInt(collectMatcher.group("x")), Integer.parseInt(collectMatcher.group("y")), false));
        else if (enterShopMatcher.matches()) App.setScreen(new ShopView());
        else System.out.println("Invalid command!");
    }
}