package models.factory.plantSkills;

import models.Constants;
import models.games.BaseGame;
import models.entity.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Homing implements Skill{
    public enum Type{RANDOM,CLOSEST}
    Type type = Type.CLOSEST;
    private BulletType bullet;
    public int targetCount = 1;
    public Homing(BulletType bullet , Type type ){
        this.bullet = bullet;
    }

    @Override
    public void do_skill(Plant plant, BaseGame game) {
        System.out.println("3 .. 2 .. 1 ... locked in on target" );
            switch (type){
                case RANDOM -> random(plant , game , targetCount);
                case CLOSEST -> closestZombie(plant, game);
            }

    }

    @Override
    public void all(Plant plant, BaseGame game) {

    }

    @Override
    public void setRandom(boolean random) {
            type = random ? Type.RANDOM : Type.CLOSEST;
    }

    @Override
    public void setAll(boolean all) {

    }



    @Override
    public ArrayList<Zombie> random(Plant plant, BaseGame game, int numbers) {
        java.util.ArrayList<Zombie> targets =  Skill.super.random(plant, game, targetCount);
        for (Zombie x : targets){
            Bullet bullet = new Bullet(plant.getX() + plant.getWidth() ,
                plant.getY() + plant.getHeight() * 0.8f , this.bullet,plant.getLine());
        bullet.setToLockIn(x);
        float dx = x.getX() - plant.getX();
        float dy = x.getY() - plant.getY();
        bullet.setVelocityX(Constants.MAGICAL_BULLET_VELOCITY);
        bullet.setVelocityY(Constants.MAGICAL_BULLET_VELOCITY * dy / dx);
        bullet.getTags().add(Bullet.Tag.HOMING);
        bullet.getTags().add(Bullet.Tag.MAGICAL);
        game.getBullets().add(bullet);
        }
        return null;

    }

    private void closestZombie(Plant plant , BaseGame game) {
        Iterator iterator =  game.getCurrentWave().getZombies().iterator();
        Zombie curr = game.getCurrentWave().getZombies().getFirst();
        float distance = distance(plant.getX() , plant.getY() , curr.getX() , curr.getY());
        while (iterator.hasNext()) {
            Zombie zombie = (Zombie) iterator.next();
            if(distance(plant.getX(),  plant.getY(), zombie.getX(), zombie.getY()) < distance){
                curr = zombie;
            }
        }

        Bullet bullet = new Bullet(plant.getX() + plant.getWidth() ,
                plant.getY() + plant.getHeight() * 0.8f , this.bullet,plant.getLine());
        bullet.setToLockIn(curr);
        float dx = curr.getX() - plant.getX();
        float dy = curr.getY() - plant.getY();
        bullet.setVelocityX(Constants.MAGICAL_BULLET_VELOCITY);
        bullet.setVelocityY(Constants.MAGICAL_BULLET_VELOCITY * dy / dx);
        bullet.getTags().add(Bullet.Tag.HOMING);
        bullet.getTags().add(Bullet.Tag.MAGICAL);
        game.getBullets().add(bullet);
    }


    private float distance(float x , float y , float x1 , float y1) {
        return (float) Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
    }
}
