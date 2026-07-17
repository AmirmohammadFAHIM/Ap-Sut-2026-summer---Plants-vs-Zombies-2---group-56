package models.utils;

public class RegexHelper {
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9\\-]+$";

    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()+=\\{\\}\\[\\]|<>?]).{8,}$";

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9](?!.*\\.\\.)[a-zA-Z0-9._\\-]*[a-zA-Z0-9]@[a-zA-Z0-9\\-]+(\\.[a-zA-Z0-9\\-]+)*\\.[a-zA-Z]{2,}$";

    public static final String REGISTER_COMMAND_PATTERN =
            "^\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<passwordConfirm>\\S+)\\s+-n\\s+(?<nickname>.+?)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>Male|Female|male|female)\\s*$";

    public static final String PICK_QUESTION_COMMAND =
            "^\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>\\d+)\\s+-a\\s+(?<answer>.+?)\\s+-c\\s+(?<answerConfirm>.+?)\\s*$";
}