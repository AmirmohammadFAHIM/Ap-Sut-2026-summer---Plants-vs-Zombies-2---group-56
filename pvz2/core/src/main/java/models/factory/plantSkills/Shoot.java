package models.factory.plantSkills;

import models.App;
import models.Constants;
import models.entity.*;
import models.factory.plantSkills.skillDatas.ShootingData;
import models.factory.plantSkills.skillDatas.ShootingMood;
import models.gamepanes.Tile;
import models.games.BaseGame;

import java.util.ArrayList;
import java.util.Random;


public class Shoot implements Skill {
    ShootingData data;
    boolean random = false;
    boolean all = false;

    public Shoot(ShootingData data){
        this.data = data;
    }
    @Override
    public void do_skill(Plant shooter , BaseGame game) {

        try {
            shoot(shooter , data, game);
        }catch (Exception e){
            System.out.println("man kharam");
        }
    }




    public void shoot(Plant shooter , ShootingData data , BaseGame game) throws CloneNotSupportedException {
        System.out.println("bangg bangg bangg .. " + shooter.getType()  + " is shooting ..");
        switch (data.getMood()){
            case OneLine -> oneLineShoot(shooter , data , game);
            case ThreeLine -> threeLineShoot(shooter , data , game);
            case Front_Back -> frontBackShoot(shooter , data , game);
            case Star -> starShoot( shooter , data , game);
            case Diagonal -> diagonal(shooter , data , game);
            case LOBBER -> lobber(shooter , game);
            case Random -> random(shooter , game , data.getRandomCount());
        }
        if(data.range > 0) midRange(shooter , game);
        else if(random) random(shooter , game, data.getRandomCount());
        else if(all) all(shooter , game);
    }

    float onionChange;
    public void oneLineShoot(Plant shooter, ShootingData data , BaseGame game) throws CloneNotSupportedException {
        onionChange += 2;
        int level = App.getCurrentuser().getLevels().get(shooter.getType());
        if(data.getBullet() == ProjectileType.ONION_1 || data.getBullet() == ProjectileType.ONION_3){
            if(onionChange >= (level >= 2 ? 9 : 10)) {
                data.setBullet(ProjectileType.ONION_2);
                onionChange = 0;
            }
        }
        else if(data.getBullet() == ProjectileType.ONION_2){
            if (onionChange >= (level >= 2 ? 4 : 5)){
                Random rand = new Random();
                boolean one =  rand.nextBoolean();
                if(one){
                    data.setBullet(ProjectileType.ONION_1);
                }
                else{
                    data.setBullet(ProjectileType.ONION_3);
                }
                onionChange = 0;
            }
        }
        float x = shooter.getX() + shooter.getWidth();
        float y =  (shooter.getY() + shooter.getHeight() * 0.8f);
        Projectile projectile = new Projectile(x , y , data.getBullet(),shooter.getLine());
        projectile.setVelocityX(Constants.BULLET_VELOCITY_X);
        for (int i = 0; i < data.getBulletNumber(); i++) {
            Projectile projectile1 = (Projectile) projectile.clone();
            projectile1.setX(projectile1.getX() + i * 10);
            game.getBullets().add(projectile1);
        }


        if(data.getBulletNumber() >= 50){
            Projectile projectile1 = new Projectile(x , y , Constants.BULLET_VELOCITY_X, ProjectileType.GIANT_PEA
                    , shooter.getDamage(),shooter.getLine());
            game.getBullets().add(projectile1);
        }
    }

    public void threeLineShoot(Plant shooter, ShootingData data , BaseGame game) throws CloneNotSupportedException {
        Projectile projectile = new Projectile(shooter.getX() + shooter.getWidth(),
                shooter.getY() + shooter.getHeight() * 0.8f
                ,Constants.BULLET_VELOCITY_X, data.getBullet() , shooter.getDamage()
        , shooter.getLine());
        Projectile bulletup = (Projectile) projectile.clone();
        Projectile bulletdown = (Projectile) bulletup.clone();
        bulletup.setY(bulletup.getY() + Tile.getHeight());
        bulletdown.setY(bulletdown.getY() - Tile.getHeight());
       if(shooter.getLine() != 1) game.getBullets().add(bulletup);
       if(shooter.getLine() != 5) game.getBullets().add(bulletdown);
    }

    private void frontBackShoot(Plant shooter , ShootingData data , BaseGame game) throws CloneNotSupportedException {
        ShootingData front = new ShootingData(data.getBullet() , data.getMood() ,
                data.getBulletNumber() / 2);
        oneLineShoot(shooter , front , game);
        Projectile projectileBack = new Projectile(shooter.getX() , shooter.getY() + shooter.getHeight() * 0.8f
                ,Constants.BULLET_VELOCITY_X * -1
                , data.getBullet(), shooter.getDamage() ,  shooter.getLine());
        for (int i = 0; i < data.getBulletNumber() / 2; i++) {
            Projectile b = (Projectile) projectileBack.clone();
            b.setX(b.getX() - i * 4);
            game.getBullets().add(b);
        }
    }

    private void starShoot(Plant shooter , ShootingData data , BaseGame game) throws CloneNotSupportedException {
        oneLineShoot(shooter, new ShootingData(data.getBullet() , data.getMood()
                , data.getBulletNumber() / 5), game);///right
        Projectile projectileStar2 = new Projectile(shooter.getX() , shooter.getY() + shooter.getHeight() * 0.8f ,
               Constants.BULLET_VELOCITY_X * -1 , data.getBullet() , shooter.getDamage()
        ,  shooter.getLine());
        Projectile projectileStar3 = (Projectile) projectileStar2.clone();
        projectileStar3.setVelocityY(projectileStar3.getVelocityX());
        projectileStar3.setY(shooter.getY());
        Projectile projectileStar4 = (Projectile) projectileStar3.clone();
        projectileStar4.setVelocityX(projectileStar4.getVelocityX() * -1);
        projectileStar4.setX(shooter.getX() +  shooter.getWidth());
        Projectile projectileStar5 = (Projectile) projectileStar4.clone();
        projectileStar5.setVelocityY(projectileStar5.getVelocityY() * -1);
        projectileStar5.setVelocityX(0);
        projectileStar5.setPosition(shooter.getX() + shooter.getWidth() / 2 , shooter.getY() + shooter.getHeight());
        for (int i = 0; i < data.getBulletNumber() / 5; i++) {
            game.getBullets().add((Projectile) projectileStar5.clone());/// up
            game.getBullets().add((Projectile) projectileStar2.clone());/// left
            game.getBullets().add((Projectile) projectileStar3.clone());/// bottom left
            game.getBullets().add((Projectile) projectileStar4.clone());/// bottom right
        }

    }


    private void diagonal(Plant shooter , ShootingData  data , BaseGame game) throws CloneNotSupportedException {
        Projectile projectile1 = new Projectile(shooter.getX() + shooter.getWidth() , shooter.getY() + shooter.getHeight() * 0.9f ,
                Constants.BULLET_VELOCITY_X, data.getBullet() , shooter.getDamage()
        ,   shooter.getLine());
        projectile1.setVelocityY(projectile1.getVelocityX());
        Projectile projectile2 = (Projectile) projectile1.clone();
        projectile2.setVelocityX(projectile2.getVelocityX() * -1);
        projectile2.setX(shooter.getX());
        Projectile projectile3 = (Projectile) projectile2.clone();
        projectile3.setVelocityY(projectile3.getVelocityY() * -1);
        projectile3.setY(shooter.getY());
        Projectile projectile4 = (Projectile) projectile3.clone();
        projectile4.setX(shooter.getX() + shooter.getWidth());
        projectile4.setVelocityX(Constants.BULLET_VELOCITY_X);
        for (int i = 0; i < data.getBulletNumber() / 4; i++) {
            game.getBullets().add((Projectile) projectile4.clone());
            game.getBullets().add((Projectile) projectile3.clone());
            game.getBullets().add((Projectile) projectile2.clone());
            game.getBullets().add((Projectile) projectile1.clone());
        }
    }


    private void midRange(Plant shooter , BaseGame game){
                if(shooter.getCategory() == PlantCategory.StrikeThrough){
                    for (Zombie zombie: game.getZombies()){
                        if(zombie.getLine() == shooter.getLine() &&
                                zombie.getTileIndex() - shooter.getTileIndex() <= data.range) {
                            zombie.setHp(zombie.getHp() - shooter.getDamage());
                        }
                    }
                }
                else{
                    for (Zombie zombie: game.getZombies()){
                        if(zombie.getLine() == shooter.getLine() &&
                                zombie.getTileIndex() - shooter.getTileIndex() <= data.range) {
                            Projectile projectile = new Projectile(shooter.getX() +  shooter.getWidth() ,
                                    shooter.getY() + shooter.getHeight() * 0.8f
                                    , Constants.BULLET_VELOCITY_X, 0,
                                    shooter.getLine());
                            projectile.setType(data.getBullet());
                        }
                    }
                }
    }

    private void lobber(Plant shooter , BaseGame game){
        Zombie target = null;
        for (Zombie z : game.getZombies()) {
            if(z.getLine() == shooter.getLine()){
                if(target == null){
                    target = z;
                }
                else if(target.getTileIndex() > z.getTileIndex()){
                    target = z;
                }
            }
        }

       if(target != null) game.getBullets().add(lobberShoot(shooter , target));
    }
    private Projectile lobberShoot(Plant shooter , Zombie target){
        Projectile projectile = new Projectile(shooter.getX() - 30, shooter.getY() + shooter.getHeight() / 2
                , data.getBullet(),shooter.getLine());
        if(data.getBullet() == ProjectileType.CORN){
            Random rand = new Random();
            boolean changeIncrease = App.getCurrentuser().getLevels().get(shooter.getType()) >= 2;
            int a = rand.nextInt(100); // probability = 20%
            if((a >= 1 && a <= 40) || (changeIncrease && a >= 41 && a <= 45)) projectile.setType(ProjectileType.BUTTER);
        }
        projectile.setVelocityX(Constants.LOBBER_BULLET_VELOCITY_X);
        float t = (target.getX() - shooter.getX()) / (projectile.getVelocityX() + target.getVelocityX());
        float dy =  target.getY() - shooter.getY();
        float vy = dy / t + Constants.GRAVITY * t / 2;
        projectile.setVelocityY(vy);
        projectile.setGrounded(true);
        return projectile;
    }



    @Override
    public ArrayList<Zombie> random(Plant plant, BaseGame game, int numbers) {
        ArrayList<Zombie> targets = Skill.super.random(plant, game, data.getRandomCount());
        if(data.getMood() == ShootingMood.LOBBER){
            for (Zombie z : targets) {
                game.getBullets().add(lobberShoot(plant, z));
            }
        }
        return targets;
    }

    @Override
    public void all(Plant plant, BaseGame game) {
        if(data.getMood() == ShootingMood.LOBBER){
            for (Zombie z : game.getZombies()) {
                game.getBullets().add(lobberShoot(plant, z));
            }

        }
        else if(data.getMood() == ShootingMood.AllLines){
            for (int i = 0; i < data.getBulletNumber() / 5; i++) {
                for (int j = 1; j <= 5; j++) {
                    Projectile projectile = new Projectile(plant.getX() ,
                            plant.getY() + i * Tile.getHeight() - Tile.getHeight() / 2
                            , data.getBullet(),plant.getLine());
                    game.getBullets().add(projectile);
                }
            }
        }

    }

    @Override
    public void setRandom(boolean random) {
        this.random = random;
    }

    @Override
    public void setAll(boolean all) {
            this.all = all;
    }

}
