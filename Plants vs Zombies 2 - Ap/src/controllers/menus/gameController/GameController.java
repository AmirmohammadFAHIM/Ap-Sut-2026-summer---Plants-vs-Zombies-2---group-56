package controllers.menus.gameController;

import models.games.BaseGame;
import models.games.Game;

public class GameController implements Controller{/// Main Brain of the game
    private Game game;

    public void updateGame(){}

    public boolean isFinished(){return false;}

    public void gameEnded(){}

    @Override
    public boolean GameStart() {
        return false;
    }


    public void startWave(){}
    public void endWave(){}

    public void Cheat(){}


}

