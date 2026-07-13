package models.factory.plantSkills;

import models.games.BaseGame;
import models.npc.Plant;
import models.npc.Zombie;

public class Freeze implements Skill{
    enum Type{LINE,ALL,TOUCH}
    @Override
    public void do_skill(Plant plant, BaseGame game) {

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
