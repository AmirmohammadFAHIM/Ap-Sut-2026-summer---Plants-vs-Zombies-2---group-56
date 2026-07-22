package controllers.menus.gameController;

import models.App;
import models.games.miniGames.MinigameLevel;
import models.games.miniGames.WallnutBowling;
import models.utils.Result;
import view.TravelLogView;

public class WallnutController implements Controller{
    public WallnutController(){
        //TODO: read the level from json
    }
    MinigameLevel level;
    WallnutBowling game = new WallnutBowling(level);


    @Override
    public String playGame(float delta) {
        game.playGame(delta);
        Result end = game.check_endGame();
        if(end.success()){
           if(end.message().equals("Won")){
               App.getCurrentuser().setWallNutBowling(App.getCurrentuser().getWallNutBowling() + 1);
           }
        }
        return "My brother , My captain , My King!\n    - Boromir to Aragorn";
    }

    private void endGame(){
        App.setScreen(new TravelLogView());
    }
    public String plant(String name , int x , int y) {
        try {
            return game.plant(name ,  x , y);
        }catch (Exception e){
            return "Name of the plant is not correct.";
        }
    }

    @Override
    public String GameStart(String input) {
        return "";
    }
}
