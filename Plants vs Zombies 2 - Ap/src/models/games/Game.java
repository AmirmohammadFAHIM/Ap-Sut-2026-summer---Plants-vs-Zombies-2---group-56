package models.games;

import models.GameAdventure.Chapters;
import models.utils.Result;

public interface Game {

    public void initGame(Chapters chapter , int level);
    public boolean startGame(String input);
    public void playGame(float delta);
    public void updatePlants(float delta);
    public void updateZombies(float delta);
    public void updateScene(float delta);
    public String plant(String plantName , int x , int y);
    public String pluck(int x , int y);
    public Result check_endGame();
    public void endGame();

}
