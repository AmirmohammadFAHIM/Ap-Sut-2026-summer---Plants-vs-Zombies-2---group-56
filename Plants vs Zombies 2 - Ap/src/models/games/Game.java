package models.games;

public interface Game {

    public void initGame();
    public boolean startGame(String input);
    public void playGame(float delta);
    public void updatePlants(float delta);
    public void updateZombies(float delta);
    public void updateScene(float delta);
    public String plant(String plantName , int x , int y);
    public void dePlant(int x , int y);
    public boolean check_endGame();
    public void endGame();

}
