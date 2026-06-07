package controllers.menus;

public interface Menu{
    public void ChangeMenu();

    public default void ShowCurrentMenu(){

    };

    public default void exitMenu(){

    };
}
