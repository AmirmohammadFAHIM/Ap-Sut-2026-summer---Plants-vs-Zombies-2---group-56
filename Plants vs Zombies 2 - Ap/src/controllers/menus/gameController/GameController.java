package controllers.menus.gameController;

import models.games.BaseGame;

public class GameController implements Controller{/// Main Brain of the game
    private BaseGame game;

    public void updateGame(){}

    public boolean isFinished(){return false;}

    public void gameEnded(){}

    @Override
    public boolean GameStart(String input) {
        boolean start = game.startGame(input);
        if(start){
            game.setState(BaseGame.GameState.PLAYING);
        }
        return false;
    }


    public void startWave(){}
    public void endWave(){}

    public void Cheat(){}


    public BaseGame getGame() {
        return game;
    }
}

