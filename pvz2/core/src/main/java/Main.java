import controllers.datacontroller.*;

import models.*;
import pvz.libpvz.pam.PamPlayer;
import pvz.skin.PvzSkin;


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
