package commands;

public enum GameCommands {

    CHEAT("$cheat\\s+(?<content>.+)^"),

    SHOW_TILE("$show\\s+tile\\s+status\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)"),

    SHOW_PLANTS_STATUS("$show\\s+plants\\s+status^"),

    PLANT("$plant\\s+(?<plant>.+?)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)^"),

    PLUCK(""),

    START_GAME("$start\\s+zombies\\s+waves^"),

    ADD_PLANT("");

    String regex;

    GameCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
