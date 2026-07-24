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

    public String play(int level) {
        App.setScreen(new GameView());
        return "Let's play baby , game on!";
    }

    @Override
    public String ChangeMenu(String menuName) {
        switch (menuName) {
            case "Collection menu": App.setScreen(new CollectionView()); return "Changed successfully to Collection menu";
            case "Greenhouse menu": App.setScreen(new GreenHouseView()); return "Changed successfully to Greenhouse menu";
            case "Quests menu": App.setScreen(new TravelLogView()); return "Changed successfully to Quests menu";
            case "Leaderboard menu": App.setScreen(new LeaderBoardView()); return "Changed successfully to Leaderboard menu";
            case "Wallet menu": App.setScreen(new WalletView()); return  "Changed successfully to Wallet menu";
            default: return "Invalid menu name";
        }
    }

    public String changeChapter(Chapters chapter) {
        currentChapter = chapter;
        StringBuilder output = new StringBuilder();
        int base = switch (App.getCurrentuser().getChapter()){
            case DarkAge -> 12;
            case BigWaveBeach -> 4;
            case FrozenCaves -> 8;
            default -> 0;
        };
        for (int i = 1; i <= 4; i++) {
            boolean unlocked = App.getCurrentuser().getLevelId() > i;
            output.append("══════════════ LEVEL ").append(i).append(" : ").append(unlocked ? "Unlocked" : "Locked");
            if(i == App.getCurrentuser().getLevelId() && chapter == App.getCurrentuser().getChapter()) {
                output.append(" (You are here now)");
            }
            output.append(" ══════════════\n");
        }
        return "Welcome to " + chapter.name() + "\n" + output.toString();
    }

    @Override
    public String ShowCurrentMenu() {
        return "--- Play Menu ---";
    }

    @Override
    public String exitMenu() {
        App.setScreen(new view.HomeView());
        return "Returned to Home Menu.";
    }
}