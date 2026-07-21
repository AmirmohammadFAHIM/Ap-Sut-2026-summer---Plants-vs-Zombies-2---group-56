package commands;

public enum PlayCommands {

    PLAY(""),
    CHANGE_CHAPTER("");
    String regex;
    PlayCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
