package view;

import controllers.menus.SignUp;

public class SignUpView extends View{
    public SignUpView(){
        menu = new SignUp();
    }

    @Override
    public void input() {
        System.out.println("Warm Welcome to Plants vs Zombies 2! Please sign up to start the game.");
        System.out.println("User Name : ");
        super.input();
        System.out.println("Password : ");
        super.input();
    }
}

