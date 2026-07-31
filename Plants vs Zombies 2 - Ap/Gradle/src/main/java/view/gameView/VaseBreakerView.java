package view.gameView;

import commands.GameCommands;
import controllers.menus.gameController.VaseBreakerController;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VaseBreakerView extends View {
    VaseBreakerController  controller;

    @Override
    public void input() {
        super.input();
        if(input.matches(GameCommands.PLAY.getRegex())){
            Matcher m = Pattern.compile(GameCommands.PLAY.getRegex()).matcher(input);
            if(m.find()){
                float tick = Float.parseFloat(m.group("delta"));
                System.out.println(controller.playGame(tick * 0.1f));
            }
        }
        else if(input.matches(GameCommands.PLANT.getRegex())){
            Matcher m = Pattern.compile(GameCommands.PLANT.getRegex()).matcher(input);
            if(m.find()){
                int x = Integer.parseInt(m.group("x"));
                int y = Integer.parseInt(m.group("y"));
                String name =  m.group("plant");
                System.out.println(controller.plant(name,x,y));
            }
        }
        else if(input.matches("show\\s+vases")){
            System.out.println(controller.showVases());
        }
        else System.out.println("Invalid input. Valid inputs:\n" +
                    "--> Play\n--> Plant \n Show vases");
    }
}
