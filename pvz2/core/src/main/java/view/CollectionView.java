package view;

import controllers.menus.secondarymenus.Collection;
import models.factory.builder.PlantType;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionView extends View {
    public CollectionView() { menu = new Collection(); }

    @Override
    public void input() {
        System.out.println("=== Collection Menu ===");
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher unlockedPlantsMatcher = Pattern.compile(RegexHelper.COLLECTION_SHOW_UNLOCKED_PLANTS).matcher(input);
        Matcher unlockedZombiesMatcher = Pattern.compile(RegexHelper.COLLECTION_SHOW_UNLOCKED_ZOMBIES).matcher(input);
        Matcher allPlantsMatcher = Pattern.compile(RegexHelper.COLLECTION_SHOW_ALL_PLANTS).matcher(input);
        Matcher allZombiesMatcher = Pattern.compile(RegexHelper.COLLECTION_SHOW_ALL_ZOMBIES).matcher(input);
        Matcher showPlantMatcher = Pattern.compile(RegexHelper.COLLECTION_SHOW_PLANT).matcher(input);
        Matcher showZombieMatcher = Pattern.compile(RegexHelper.COLLECTION_SHOW_ZOMBIE).matcher(input);
        Matcher buyPlantMatcher = Pattern.compile(RegexHelper.COLLECTION_BUY_PLANT).matcher(input);
        Matcher upgradePlantMatcher = Pattern.compile(RegexHelper.COLLECTION_UPGRADE_PLANT).matcher(input);

        Collection collectionMenu = (Collection) menu;

        if (unlockedPlantsMatcher.matches()) System.out.println(collectionMenu.showunlockedPlant());
        else if (unlockedZombiesMatcher.matches()) System.out.println(collectionMenu.showunlockedZombie());
        else if (allPlantsMatcher.matches()) System.out.println(collectionMenu.showAllPlants());
        else if (allZombiesMatcher.matches()) System.out.println(collectionMenu.showAllZombies());
        else if (showPlantMatcher.matches()) System.out.println(collectionMenu.showPlant(showPlantMatcher.group("name")));
        else if (showZombieMatcher.matches()) System.out.println(collectionMenu.showZombie(showZombieMatcher.group("name")));
        else if (buyPlantMatcher.matches()) System.out.println(collectionMenu.buyPlant(buyPlantMatcher.group("name")));
        else if (upgradePlantMatcher.matches()) {
            String plantName = upgradePlantMatcher.group("name");
            try {
                System.out.println(collectionMenu.upgradePlant(PlantType.valueOf(plantName.toUpperCase())));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Plant '" + plantName + "' not found.");
            }
        } else {
            System.out.println("Invalid command!");
        }
    }
}