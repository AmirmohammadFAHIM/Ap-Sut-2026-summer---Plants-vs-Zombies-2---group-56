package controllers.menus;

public interface Menu{
    public String ChangeMenu(String menuName);

    public default void ShowCurrentMenu(){

    };

    public default void exitMenu(){

    };
}
