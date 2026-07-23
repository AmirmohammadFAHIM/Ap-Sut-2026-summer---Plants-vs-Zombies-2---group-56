package view.gameView;

import commands.GameCommands;
import controllers.menus.gameController.WallnutController;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WallnutBowlingView extends View {
    WallnutController wallnutController;

    @Override
    public void input() {
        super.input();
        if(input.matches(GameCommands.PLANT.getRegex())){
            Matcher m = Pattern.compile(GameCommands.PLANT.getRegex()).matcher(input);
            if(m.find()){
                int x =  Integer.parseInt(m.group("x"));
                int y = Integer.parseInt(m.group("y"));
                String plantName = m.group("plant");
                System.out.println(wallnutController.plant(plantName , x , y));
            }
        }
        else if(input.matches(GameCommands.PLAY.getRegex())){
            Matcher m = Pattern.compile(GameCommands.PLAY.getRegex()).matcher(input);
            if(m.find()){
                float tick =  Float.parseFloat(m.group("delta"));
                System.out.println(wallnutController.playGame(tick * 0.1f));
            }
        }
        else System.out.println("The input is invalid.");
    }
}
