package models.games;

public interface Game {

    public void initGame();
    public void playGame();
    public void updatePlants();
    public void updateZombies();
    public void updateScene();
    public void updateGame();
    public void plant();
    public void dePlant();
    public boolean check_endGame();
    public void endGame();
}
