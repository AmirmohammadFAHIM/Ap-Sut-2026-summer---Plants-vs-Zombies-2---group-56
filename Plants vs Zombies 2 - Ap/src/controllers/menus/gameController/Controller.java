package controllers.menus.gameController;

public interface Controller {
    public void updateGame();

    public boolean isFinished();

    public void gameEnded();
    public boolean GameStart(String input);
    public default void startWave(){}
    public default void endWave(){}
    public default void Cheat(){}
}
