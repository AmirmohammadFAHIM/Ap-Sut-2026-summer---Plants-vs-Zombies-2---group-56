package models.utils;

public class RegexHelper {
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9\\-]+$";
    public static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()+=\\{\\}\\[\\]|<>?]).{8,}$";
    public static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9](?!.*\\.\\.)[a-zA-Z0-9._\\-]*[a-zA-Z0-9]@[a-zA-Z0-9\\-]+(\\.[a-zA-Z0-9\\-]+)*\\.[a-zA-Z]{2,}$";

    public static final String REGISTER_COMMAND_PATTERN =
            "^\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)" +
                    "\\s+(?<passwordConfirm>\\S+)\\s+-n\\s+(?<nickname>.+?)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>Male|Female|male|female)\\s*$";
    public static final String PICK_QUESTION_COMMAND =
            "^\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>\\d+)" +
                    "\\s+-a\\s+(?<answer>.+?)\\s+-c\\s+(?<answerConfirm>.+?)\\s*$";
    public static final String LOGIN_COMMAND_PATTERN =
            "^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p" +
                    "\\s+(?<password>\\S+)(?:\\s+(?<stayLoggedIn>-stay-logged-in))?\\s*$";
    public static final String FORGET_PASSWORD_COMMAND =
            "^\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s+-e\\s+(?<email>\\S+)\\s*$";
    public static final String ANSWER_COMMAND =
            "^\\s*answer\\s+-a\\s+(?<answer>.+?)\\s*$";

    public static final String PROFILE_SHOW_INFO = "(?i)^menu\\s+profile\\s+show-info$";
    public static final String PROFILE_CHANGE_USERNAME =
            "(?i)^menu\\s+profile\\s+change-username\\s+-u\\s+(?<username>\\S+)$";
    public static final String PROFILE_CHANGE_NICKNAME =
            "(?i)^menu\\s+profile\\s+change-nickname\\s+-(n|u)\\s+(?<nickname>.+)$";
    public static final String PROFILE_CHANGE_EMAIL = "(?i)^menu\\s+profile\\s+change-email\\s+-e\\s+(?<email>\\S+)$";
    public static final String PROFILE_CHANGE_PASSWORD =
            "(?i)^menu\\s+profile\\s+change-password\\s+-p\\s+(?<newPassword>\\S+)\\s+-o\\s+(?<oldPassword>\\S+)$";

    public static final String PLAY_ENTER_CHAPTER = "(?i)^enter\\s+chapter\\s+-c\\s+(?<chaptername>[a-zA-Z]+)$";
    public static final String PLAY_SHORTCUTS =
            "(?i)^menu\\s+(?<shortcut>greenhouse|travel-log|leaderboard|coin-wallet|gem-wallet)$";
    public static final String PLAY_CHEAT =
            "(?i)^menu\\s+cheat\\s+add\\s+(?<amount>\\d+)\\s+(?<type>coin|coins|diamond|diamonds|sun|suns)$";
    public static final String PLAY_LEVEL = "(?i)^play\\s+(?<level>\\d+)$";

    public static final String COLLECTION_SHOW_UNLOCKED_PLANTS = "(?i)^menu\\s+collection\\s+show-plants$";
    public static final String COLLECTION_SHOW_ALL_PLANTS = "(?i)^menu\\s+collection\\s+show-all-plants$";
    public static final String COLLECTION_SHOW_UNLOCKED_ZOMBIES = "(?i)^menu\\s+collection\\s+show-zombies$";
    public static final String COLLECTION_SHOW_ALL_ZOMBIES = "(?i)^menu\\s+collection\\s+show-all-zombies$";
    public static final String COLLECTION_SHOW_PLANT =
            "(?i)^menu\\s+collection\\s+show-plant\\s+-p\\s+(?<name>[a-zA-Z_]+)$";
    public static final String COLLECTION_SHOW_ZOMBIE =
            "(?i)^menu\\s+collection\\s+show-zombie\\s+-z\\s+(?<name>[a-zA-Z_]+)$";
    public static final String COLLECTION_UPGRADE_PLANT =
            "(?i)^menu\\s+collection\\s+upgrade-plant\\s+-p\\s+(?<name>[a-zA-Z_]+)$";
    public static final String COLLECTION_BUY_PLANT =
            "(?i)^menu\\s+collection\\s+purchase-plant\\s+-p\\s+(?<name>[a-zA-Z_]+)$";

    public static final String SETTINGS_CHANGE_DIFFICULTY =
            "(?i)^menu\\s+settings\\s+change-difficulty\\s+-l\\s+(?<level>\\d+)$";
    public static final String NEWS_SHOW_UNREAD = "(?i)^menu\\s+news\\s+show-unread$";
    public static final String NEWS_SHOW_ALL = "(?i)^menu\\s+news\\s+show-all$";

    public static final String GREENHOUSE_SHOW = "(?i)^show\\s+greenhouse$";
    public static final String GREENHOUSE_PLANT = "(?i)^plant\\s+pot\\s+at\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GREENHOUSE_COLLECT = "(?i)^collect\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GREENHOUSE_GROW = "(?i)^grow\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GREENHOUSE_ENTER_SHOP = "(?i)^enter\\s+shop$";

    public static final String SHOP_LIST = "(?i)^shop\\s+list$";
    public static final String SHOP_DAILY = "(?i)^shop\\s+daily$";
    public static final String SHOP_BUY =
            "(?i)^shop\\s+buy\\s+-i\\s+(?<itemId>\\d+)\\s+-n\\s+(?<count>\\d+)(?:\\s+-t\\s+(?<plantType>[a-zA-Z_]+))?$";

    public static final String QUESTS_CHANGE_PAGE = "(?i)^travel\\s+log\\s+page\\s+(?<pageName>[a-zA-Z]+)$";
    public static final String QUESTS_SHOW = "(?i)^show\\s+quests$";

    public static final String NETWORK_CONNECT = "(?i)^connect\\s+(?<ip>[a-zA-Z0-9\\.]+)\\s+(?<port>\\d+)$";
    public static final String NETWORK_HOST = "(?i)^host\\s+(?<port>\\d+)$";
    public static final String WALLET_SHOW_COIN = "(?i)^show\\s+coin\\s+wallet$";
    public static final String WALLET_SHOW_GEM = "(?i)^show\\s+gem\\s+wallet$";
    public static final String WALLET_CHEAT =
            "(?i)^cheat\\s+add\\s+(?<amount>\\d+)\\s+(?<type>coin|coins|diamond|diamonds)$";
    public static final String LEADERBOARD_SHOW = "(?i)^show\\s+leaderboard$";
    public static final String LEADERBOARD_SORT = "(?i)^sort\\s+(?<criteria>score|level)$";

    public static final String GAME_SHOW_ALL_PLANTS = "(?i)^show\\s+all\\s+plants$";
    public static final String GAME_SHOW_AVAILABLE_PLANTS = "(?i)^show\\s+available\\s+plants$";
    public static final String GAME_ADD_PLANT = "(?i)^add\\s+plant\\s+-t\\s+(?<type>[a-zA-Z_]+)$";
    public static final String GAME_REMOVE_PLANT = "(?i)^remove\\s+plant\\s+-t\\s+(?<type>[a-zA-Z_]+)$";
    public static final String GAME_BOOST_PLANT = "(?i)^boost\\s+plant\\s+-t\\s+(?<type>[a-zA-Z_]+)$";
    public static final String GAME_START_GAME = "(?i)^start\\s+game$";

    public static final String GAME_ADVANCE_TIME = "(?i)^advance\\s+time\\s+-t\\s+(?<count>\\d+)\\s+ticks$";
    public static final String GAME_COLLECT_SUN = "(?i)^collect\\s+sun\\s+-l\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GAME_SHOW_SUN_AMOUNT = "(?i)^show\\s+sun\\s+amount$";
    public static final String GAME_CHEAT_ADD_SUN = "(?i)^cheat\\s+add\\s+-n\\s+(?<count>\\d+)\\s+suns$";
    public static final String GAME_RELEASE_NUKE = "(?i)^release\\s+the\\s+nuke$";
    public static final String GAME_PLANT_PLANT =
            "(?i)^plant\\s+plant\\s+-t\\s+(?<type>[a-zA-Z_]+)\\s+-l\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GAME_PLUCK_PLANT = "(?i)^pluck\\s+plant\\s+-l\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GAME_FEED_PLANT = "(?i)^feed\\s+plant\\s+-l\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GAME_CHEAT_REMOVE_COOLDOWN = "(?i)^cheat\\s+remove-cooldown$";
    public static final String GAME_CHEAT_ADD_PLANT_FOOD = "(?i)^cheat\\s+add-plant-food$";
    public static final String GAME_SHOW_MAP = "(?i)^show\\s+map$";
    public static final String GAME_SHOW_PLANTS_STATUS = "(?i)^show\\s+plants\\s+status$";
    public static final String GAME_SHOW_TILE_STATUS =
            "(?i)^show\\s+tile\\s+status\\s+-l\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GAME_ZOMBIES_INFO = "(?i)^zombies\\s+info$";
    public static final String GAME_CHEAT_SPAWN_ZOMBIE =
            "(?i)^cheat\\s+spawn-zombie\\s+-t\\s+(?<type>[a-zA-Z_]+)\\s+-l\\s+\\((?<x>\\d+),\\s*(?<y>\\d+)\\)$";
    public static final String GAME_START_ZOMBIE_WAVES = "(?i)^start\\s+zombie\\s+waves$";
}