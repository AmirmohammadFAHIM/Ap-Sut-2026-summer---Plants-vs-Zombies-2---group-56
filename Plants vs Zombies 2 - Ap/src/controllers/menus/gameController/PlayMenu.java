package controllers.menus.gameController;

import controllers.menus.Menu;
import models.App;
import view.*;

public class PlayMenu implements Menu {


    public void startGame() {
    }

    @Override
    public String ChangeMenu(String menuName) {
        switch (menuName) {
            case "Collection menu":
                App.setScreen(new CollectionView());
                return "Changed successfully to Collection menu";
            case "Greenhouse menu":
                App.setScreen(new GreenHouseView());
                return "Changed successfully to Greenhouse menu";
            case "Quests menu":
                App.setScreen(new QuestsView());
                return "Changed successfully to Quests menu";
            case "Leaderboard menu":
                App.setScreen(new LeaderBoardView());
                return "Changed successfully to Leaderboard menu";
            case "Wallet menu":
                App.setScreen(new WalletView());
                return  "Changed successfully to Wallet menu";
            default:
                return "Invalid menu name";
        }

    }

    @Override
    public void ShowCurrentMenu() {
        Menu.super.ShowCurrentMenu();
    }

    @Override
    public void exitMenu() {
        Menu.super.exitMenu();
    }
}
