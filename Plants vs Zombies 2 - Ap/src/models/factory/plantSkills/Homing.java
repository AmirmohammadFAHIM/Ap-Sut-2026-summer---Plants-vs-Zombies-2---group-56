package models.factory.plantSkills;

import models.games.BaseGame;
import models.npc.Bullet;
import models.npc.Plant;
import models.npc.PlantTags;
import models.npc.Zombie;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Random;

public class Homing implements Skill{
    private boolean random;
    private Bullet bullet;

    @Override
    public void baseSkill(Plant plant, BaseGame game) {
        if (random) {
            bullet.setToLockIn(randomZombie(game));
        } else {
            bullet.setToLockIn(closestZombie(
                    plant, game
            ));
        }

        bullet = new Bullet();
        bullet.setX(plant.getX());
        bullet.setY(plant.getY());
        bullet.setHoming(true); /// when a bullet is homing , It knows where to go because It has the
        /// zombie to lock in.(We don't need to set the velocity here , in the next frames the bullet
        /// does it itself).
        if(plant.getTags().contains(PlantTags.MAGICAL)) bullet.setMagical(true);
        if(plant.getTags().contains(PlantTags.POISON)) bullet.setMagical(true);
        if(plant.getTags().contains(PlantTags.ICE)) bullet.setIce(true);
        else if(plant.getTags().contains(PlantTags.FIRE)) bullet.setFire(true);

    }


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
