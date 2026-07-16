package commands;

public enum GameCommands {

    PLANT("$plant\\s+(?<plant>.+?)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)^"),

    PLUCK,

    START_GAME("$start\\s+zombies\\s+waves^"),

    ADD_PLANT;

    String regex;

    GameCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
