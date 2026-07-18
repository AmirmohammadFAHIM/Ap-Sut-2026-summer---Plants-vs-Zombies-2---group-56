package controllers.menus.gameController;

public interface Controller {
    public void playGame(float delta);
    public String GameStart(String input);
    public default void Cheat(){}
}
