package controllers.menus.gameController;

import controllers.menus.Menu;
import models.App;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import view.*;
import view.gameView.GameView;

public class PlayMenu implements Menu {
    private Chapters currentChapter;
    public PlayMenu() {
        currentChapter = App.getCurrentuser().getChapter();
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
                App.setScreen(new TravelLogView());
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


    public String changeChapter(Chapters chapter) {
        currentChapter = chapter;
        StringBuilder output = new StringBuilder();
        int base = switch (App.getCurrentuser().getChapter()){
            case DarkAge -> 12;
            case BigWaveBeach -> 4;
            case FrozenCaves -> 8;
            default -> 0; // Ancient Egypt
        };
        for (int i = 1; i <= 4; i++) {
            boolean unlocked = App.getCurrentuser().getLevelsPassed() - base > i;
            output.append("══════════════ LEVEL " + i +" : " + (unlocked ? "Unlocked" : "Locked"));
            if(i == App.getCurrentuser().getLevelId() &&
            chapter == App.getCurrentuser().getChapter()) {
                output.append(" (You are here now)");
            }
            output.append( " ══════════════" + "\n");
        }
        return "Welcome to " + chapter.name() + "\n" + output.toString();
    }


    public String play(int level){
        Level toPlay = null;
        App.setScreen(new GameView(currentChapter , toPlay));
        return "Game on , baby.";
    }




}
