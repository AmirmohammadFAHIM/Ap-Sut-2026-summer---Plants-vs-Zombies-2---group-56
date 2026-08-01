package models.factory.plantSkills;

import models.games.BaseGame;
import models.entity.Plant;
import models.entity.Zombie;

public class Freeze implements Skill{
   public enum Type{LINE,ALL,TOUCH , RANDOM}
    Type type;
   public Freeze(Type type){
       this.type = type;
   }
    @Override
    public void do_skill(Plant plant, BaseGame game) {
        switch (type){
            case LINE -> line(plant, game);
            case  TOUCH -> touch(plant, game);
            case  RANDOM -> random(plant , game , 3);
        }
    }



    @Override
    public void all(Plant plant, BaseGame game) {

    }

    @Override
    public void setRandom(boolean random) {
        type = random ? Type.RANDOM : type;
    }

    @Override
    public void setAll(boolean all) {
        type = all ? Type.ALL : type;
    }

    private void touch(Plant plant , BaseGame game){
        for (Zombie z : game.getZombies()) {
            float dx = Math.abs(plant.getX() - z.getX());
            float dy = Math.abs(plant.getY() - z.getY());
            if(dx <= 20 && dy <= 20){
                z.setFrozen(true);
            }
            return;
        }
    }

    private void line(Plant plant ,  BaseGame game){
       for (Zombie z : game.getZombies()) {
           if(z.getLine() == plant.getLine()) z.setFrozen(true);
       }
    }



}
