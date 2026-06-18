package models.factory.plantSkills;

import models.games.BaseGame;
import models.npc.Plant;
import models.npc.Zombie;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Random;

public class Homing implements Skill{
    private boolean random;
    @Override
    public void baseskill(Plant plant, BaseGame game) {

    }

    @Override
    public void plantFoodSkill(Plant plant, BaseGame game) {

    }


    private Zombie randomZombie(BaseGame  game) {
        Random rand = new Random();
        int randomIdx = rand.nextInt(game.getCurrentWave().getZombies().size());
        return game.getCurrentWave().getZombies().get(randomIdx);
    }

    private Zombie closestZombie(Plant palnt , BaseGame game) {
        Iterator iterator =  game.getCurrentWave().getZombies().iterator();
        Zombie curr = game.getCurrentWave().getZombies().getFirst();
        float distance = distance(palnt.getX() , palnt.getY() , curr.getX() , curr.getY());
        while (iterator.hasNext()) {
            Zombie zombie = (Zombie) iterator.next();
            if(distance(palnt.getX(),  palnt.getY(), zombie.getX(), zombie.getY()) < distance){
                curr = zombie;
              //  distance = distance()
            }
        }

        return curr;
    }


    private float distance(float x , float y , float x1 , float y1) {
        return (float) Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
    }
}
