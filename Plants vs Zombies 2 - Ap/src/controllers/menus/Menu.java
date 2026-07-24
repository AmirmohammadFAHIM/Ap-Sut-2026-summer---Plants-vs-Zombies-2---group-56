package controllers.menus;

public interface Menu {
    String ChangeMenu(String menuName);

    default String ShowCurrentMenu() {
        return "";
    }

    default String exitMenu() {
        return "";
    }
}