import controllers.datacontroller.*;

import models.*;


public class Main{

    public static void main(String[] args) {
        Data.deserializeUser();
        Data.setUp();
        Data.loadPlantsFromJson();
        Data.loadLevelsFromJson("levels.json");
        MiniGameLevelManager.loadLevelsFromFile("minigames.json");
        MiniGameLevelManager.loadLevelsFromFile("minigames.json");
        while (App.getScreen() != null) {
            App.getScreen().input();
        }
    }
}
