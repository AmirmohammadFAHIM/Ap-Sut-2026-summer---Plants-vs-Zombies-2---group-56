package controllers.menus.SecondaryMenus;

import controllers.datacontroller.Data;
import controllers.menus.Menu;
import models.App;
import models.User;
import models.factory.builder.PlantType;

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
        return "Showing all zombies in the game... (Pending Enum creation)";
    }

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