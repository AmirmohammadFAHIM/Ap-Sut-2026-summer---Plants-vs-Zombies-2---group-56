package view;

import controllers.menus.LogIn;

public class LogInView extends  View{
    public LogInView() {
        menu = new LogIn();
    }

    @Override
    public void input() {
        System.out.printf("Welcome to Log In View\n");
        super.input();
    }
}
