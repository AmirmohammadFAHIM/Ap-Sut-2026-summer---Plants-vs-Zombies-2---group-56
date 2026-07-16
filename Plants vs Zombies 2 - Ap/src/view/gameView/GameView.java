package view.gameView;

import controllers.menus.gameController.GameController;
import models.games.BaseGame;
import models.games.Game;
import view.GetInput;

public class GameView implements GetInput {
    GameController controller;
    @Override
    public void input() {

    }

    private String passer(String input){
        BaseGame game = controller.getGame();
      if(game.getState() == BaseGame.GameState.STARTING) {
          if (input.matches(game.getStartGameCommand().getRegex())) {
                /// start game method
          }
      }
      return "Ana Gharadoo , Ana Gharadoo";
    }
}
