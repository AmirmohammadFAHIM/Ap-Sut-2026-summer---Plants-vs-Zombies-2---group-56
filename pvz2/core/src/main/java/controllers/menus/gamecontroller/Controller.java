package controllers.menus.gamecontroller;

public interface Controller {
    public String playGame(float delta);
    public String GameStart(String input);
    public default void Cheat(){}
}
