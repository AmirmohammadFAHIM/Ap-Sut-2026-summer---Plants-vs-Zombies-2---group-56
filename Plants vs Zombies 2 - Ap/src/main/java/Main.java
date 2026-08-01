import controllers.datacontroller.Data;
import models.App;
import models.GameAdventure.Chapters;


public class Main{

    public static void main(String[] args) {
        Data.deserializeUser();
        Data.setUp();
        Data.loadPlantsFromJson();
        Data.loadLevelsFromJson("levels.json");
        while (App.getScreen() != null) {
            App.getScreen().input();
        }
    }
}
