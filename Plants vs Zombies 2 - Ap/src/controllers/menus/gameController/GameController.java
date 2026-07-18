package controllers.menus.gameController;

import models.entity.Zombie;
import models.games.BaseGame;

public class GameController implements Controller{/// Main Brain of the game
    private BaseGame game;



    public void gameEnded(){}


    @Override
    public String GameStart(String input) {
        boolean start = game.startGame(input);
        if(start){
            game.setState(BaseGame.GameState.PLAYING);
            return "Ay Yoooooo ma homie , Game on baby!!!! Vamooosss!";
        }
        return "Game Starting ...";
    }

    @Override
    public void playGame(float delta) {
            game.playGame(delta);
            boolean end = game.check_endGame();
            if(end){
                game.setState(BaseGame.GameState.END);
                game.endGame();
            }
    }

    public String showSunAmount(){
        return "-->> Suns : " + game.getSunCount();
    }

    public String cheatSunAmount(int amount){
        game.setSunCount(game.getSunCount()+amount);
        return "==== >> Suns added by Cheat code : "  + amount;
    }

    public String cheatZombieKiller(){
        for (Zombie z : game.getZombies()) {
            z.setHp(0);
        }
        return "What You Said goddamn niggaZombie???";
    }



    public BaseGame getGame() {
        return game;
    }
}

