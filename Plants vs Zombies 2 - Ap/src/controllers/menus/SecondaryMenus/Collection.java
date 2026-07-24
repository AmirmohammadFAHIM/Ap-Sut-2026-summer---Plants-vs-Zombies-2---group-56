package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;
import models.factory.builder.PlantType;
import models.entity.*;


import java.util.*;

public class Collection implements Menu {
    @Override
    public String ChangeMenu(String menuName) {
        return "Invalid menu transition from Collection menu.";
    }

    @Override
    public String ShowCurrentMenu() {
        return "--- Collection Menu ---";
    }

    @Override
    public String exitMenu() {
        App.setScreen(new view.PlayView());
        return "Returned to Play Menu.";
    }

    public String showunlockedPlant() {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not found.";
        StringBuilder sb = new StringBuilder("--- Unlocked Plants ---\n");
        for (PlantType type : user.getUnlockedPlants()) sb.append("--").append(type.name()).append("\n");
        return sb.toString().trim();
    }

    public String showunlockedZombie() {
        return "Showing unlocked zombies... (To be integrated with Adventure mode progression)";
    }

    public String showAllPlants() {
        StringBuilder sb = new StringBuilder("--- All Plants in the Game ---\n");
        for (PlantType plant : PlantType.values()) sb.append("- ").append(plant.name()).append("\n");
        return sb.toString().trim();
    }

    public String showAllZombies() {
        User user = Data.getCurrentUser();
        if (user == null) return "Error: User not found.";

        ZombieRegistry registry = user.getZombieRegistry();
        List<ZombieRegistry.ZombieType> unlocked = registry.getUnlockedZombies();

        System.out.println("--- Zombie Collection (" + unlocked.size() + "/" + registry.getTotalCount() + ") ---");
        for (ZombieRegistry.ZombieType type : unlocked) {
            System.out.println("- " + type.name());
        }
        return "chenin bood list" ;
    }

//    private String formatZombieInfo(Zombie zombie) {
//        StringBuilder sb = new StringBuilder();
//
//        // 1. Name
//        sb.append(zombie.getType()).append(": ");
//
//        // 2. Position (tile position)
//        int col = (int) ((zombie.getX() - 100) / 80);
//        int row = zombie.getRow();
//        sb.append("position: (").append(row).append(", ").append(col).append(")  ");
//
//        // 3. Health
//        sb.append("health: ").append(zombie.getHp());
//
//        // 4. Armors
//        List<Armor> armors = zombie.getArmors();
//        if (!armors.isEmpty()) {
//            sb.append("  armors: ");
//            for (int i = 0; i < armors.size(); i++) {
//                Armor armor = armors.get(i);
//                sb.append(armor.getType()).append(": ").append(armor.getHealth());
//                if (i < armors.size() - 1) {
//                    sb.append(", ");
//                }
//            }
//        }
//
//        // 5. Effects
//        List<Effect> effects = zombie.getEffects();
//        if (!effects.isEmpty()) {
//            sb.append("  effects: ");
//            for (int i = 0; i < effects.size(); i++) {
//                Effect effect = effects.get(i);
//                float remaining = effect.getRemainingTime();
//                sb.append(effect.getType().name().toLowerCase());
//                if (remaining > 0) {
//                    sb.append(": ").append(String.format("%.1f", remaining)).append("s");
//                }
//                if (i < effects.size() - 1) {
//                    sb.append(", ");
//                }
//            }
//        }
//
//        return sb.toString();
//    }

    public String showZombie(String zombieName) {
        return "Showing details for zombie: " + zombieName;
    }

    public String showPlant(String plantName) {
        return "Showing details for plant: " + plantName;
    }

    public String upgradePlant(PlantType plantType) {
        int newLevel = App.getCurrentuser().getLevels().get(plantType) + 1;
        if(newLevel >= 5) return "What the fuck you think you're doing ?";
        App.getCurrentuser().getLevels().replace(plantType, newLevel);
        String message = "Damn! You just upgraded ma man " + plantType.name() + " to level " + newLevel;
        App.getCurrentuser().getUnreadNews().add(message);
        Data.saveUser();
        return message;
    }

    public String buyPlant(String plantName) {
        User currentUser = Data.getCurrentUser();
        if (currentUser == null) return "Error: Please log in.";
        String upperName = plantName.toUpperCase();

        if (currentUser.getUnlockedPlantsNames().contains(upperName)) {
            return "Error: You already own this plant.";
        }
        if (currentUser.getCoins() >= 2000) {
            currentUser.addCoins(-2000);
            currentUser.getUnlockedPlantsNames().add(upperName);
            News.pushNewsToUser(currentUser, "New plant unlocked: " + upperName);
            Data.saveUser();
            return "Plant " + upperName + " purchased successfully.";
        }
        return "Error: Not enough coins to purchase this plant. 2000 coins required.";
    }
}