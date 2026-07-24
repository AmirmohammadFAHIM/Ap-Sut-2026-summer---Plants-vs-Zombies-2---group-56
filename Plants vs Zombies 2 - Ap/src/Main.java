import controllers.datacontroller.Data;
import models.App;

public class Main{

    public static void main(String[] args) {
        Data.deserializeUser();
        Data.setUp();
        while (App.getScreen() != null) {
            App.getScreen().input();
        }
    }
}
