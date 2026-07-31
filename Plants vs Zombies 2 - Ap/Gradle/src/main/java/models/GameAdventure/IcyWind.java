package models.GameAdventure;

import models.entity.Plant;
import models.games.BaseGame;

import java.util.Random;

public class IcyWind implements ChapterSpecialEvent{
    public IcyWind(BaseGame game) {

    }
    @Override
    public void run(BaseGame game, float delta) {
        Random rand = new Random();
        int row =  rand.nextInt(5);
        for (Plant x : game.getPlants_inField()) {
            if(x.getLine() == row) x.setFreezeLevel(x.getFreezeLevel() + 1);
        }
        dispose(game);
    }


}
