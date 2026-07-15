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
        for (Zombie z : game.getCurrentWave().getZombies()){
            if(z.getX() - plant.getX() + plant.getWidth() <= 20){
                /// freeze this nigger
            }
        }
        for (Zombie z : game.getPreviousWave().getZombies()){
            if(z.getX() - plant.getX() + plant.getWidth() <= 20){
                /// freeze this nigger
            }
        }
    }



}
