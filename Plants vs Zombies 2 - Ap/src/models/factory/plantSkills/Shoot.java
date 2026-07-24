package models.factory.plantSkills;

import models.App;
import models.Constants;
import models.entity.*;
import models.factory.plantSkills.skillDatas.ShootingData;
import models.factory.plantSkills.skillDatas.ShootingMood;
import models.gamePanes.Tile;
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
            System.out.println(e.getMessage());
        }
    }




    public void shoot(Plant shooter , ShootingData data , BaseGame game) throws CloneNotSupportedException {
        switch (data.getMood()){
            case OneLine -> OneLineShoot(shooter , data , game);
            case ThreeLine -> ThreeLineShoot(shooter , data , game);
            case Front_Back -> front_back_shoot(shooter , data , game);
            case Star -> star_shoot( shooter , data , game);
            case Diagonal -> diagonal(shooter , data , game);
            case LOBBER -> lobber(shooter , game);
            case Random -> random(shooter , game , data.getRandomCount());
        }
    }

    float onionChange;
    public void OneLineShoot(Plant shooter, ShootingData data , BaseGame game) throws CloneNotSupportedException {
        onionChange += 2;
        int level = App.getCurrentuser().getLevels().get(shooter.getType());
        if(data.getBullet() == BulletType.ONION_1 || data.getBullet() == BulletType.ONION_3){
            if(onionChange >= (level >= 2 ? 9 : 10)) {
                data.setBullet(BulletType.ONION_2);
                onionChange = 0;
            }
        }
        else if(data.getBullet() == BulletType.ONION_2){
            if (onionChange >= (level >= 2 ? 4 : 5)){
                Random rand = new Random();
                boolean one =  rand.nextBoolean();
                if(one){
                    data.setBullet(BulletType.ONION_1);
                }
                else{
                    data.setBullet(BulletType.ONION_3);
                }
                onionChange = 0;
            }
        }
        float x = shooter.getX() + shooter.getWidth();
        float y =  (shooter.getY() + shooter.getHeight() * 0.8f);
        Bullet bullet = new Bullet(x , y , data.getBullet());
        bullet.setVelocityX(Constants.BulletVelocityX);
        for (int i = 0; i < data.getBulletNumber(); i++) {
            Bullet bullet1 = (Bullet) bullet.clone();
            bullet1.setX(bullet1.getX() + i * 10);
            game.getBullets().add(bullet1);
        }


        if(data.getBulletNumber() >= 50){
            Bullet bullet1 = new Bullet(x , y , Constants.BulletVelocityX , BulletType.GIANT_PEA
                    , shooter.getDamage());
            game.getBullets().add(bullet1);
        }
    }

    public void ThreeLineShoot(Plant shooter, ShootingData data , BaseGame game) throws CloneNotSupportedException {
        Bullet bullet = new Bullet(shooter.getX() + shooter.getWidth(),
                shooter.getY() + shooter.getHeight() * 0.8f ,Constants.BulletVelocityX, data.getBullet() , shooter.getDamage());
        Bullet bulletup = (Bullet) bullet.clone();
        Bullet bulletdown = (Bullet) bulletup.clone();
        bulletup.setY(bulletup.getY() + Tile.getHeight());
        bulletdown.setY(bulletdown.getY() - Tile.getHeight());
       if(shooter.getLine() != 1) game.getBullets().add(bulletup);
       if(shooter.getLine() != 5) game.getBullets().add(bulletdown);
    }

    private void front_back_shoot(Plant shooter , ShootingData data , BaseGame game) throws CloneNotSupportedException {
        ShootingData front = new ShootingData(data.getBullet() , data.getMood() ,
                data.getBulletNumber() / 2);
        OneLineShoot(shooter , front , game);
        Bullet bulletBack = new Bullet(shooter.getX() , shooter.getY() + shooter.getHeight() * 0.8f,Constants.BulletVelocityX * -1
                , data.getBullet(), shooter.getDamage());
        for (int i = 0; i < data.getBulletNumber() / 2; i++) {
            Bullet b = (Bullet) bulletBack.clone();
            b.setX(b.getX() - i * 4);
            game.getBullets().add(b);
        }
    }

    private void star_shoot(Plant shooter , ShootingData data ,  BaseGame game) throws CloneNotSupportedException {
        OneLineShoot(shooter, new ShootingData(data.getBullet() , data.getMood()
                , data.getBulletNumber() / 5), game);///right
        Bullet bulletStar2 = new Bullet(shooter.getX() , shooter.getY() + shooter.getHeight() * 0.8f ,
               Constants.BulletVelocityX * -1 , data.getBullet() , shooter.getDamage() );
        Bullet bulletStar3 = (Bullet) bulletStar2.clone();
        bulletStar3.setVelocityY(bulletStar3.getVelocityX());
        bulletStar3.setY(shooter.getY());
        Bullet bulletStar4 = (Bullet) bulletStar3.clone();
        bulletStar4.setVelocityX(bulletStar4.getVelocityX() * -1);
        bulletStar4.setX(shooter.getX() +  shooter.getWidth());
        Bullet  bulletStar5 = (Bullet) bulletStar4.clone();
        bulletStar5.setVelocityY(bulletStar5.getVelocityY() * -1);
        bulletStar5.setVelocityX(0);
        bulletStar5.setPosition(shooter.getX() + shooter.getWidth() / 2 , shooter.getY() + shooter.getHeight());
        for (int i = 0; i < data.getBulletNumber() / 5; i++) {
            game.getBullets().add((Bullet) bulletStar5.clone());/// up
            game.getBullets().add((Bullet) bulletStar2.clone());/// left
            game.getBullets().add((Bullet) bulletStar3.clone());/// bottom left
            game.getBullets().add((Bullet) bulletStar4.clone());/// bottom right
        }

    }


    private void diagonal(Plant shooter , ShootingData  data , BaseGame game) throws CloneNotSupportedException {
        Bullet bullet1 = new Bullet(shooter.getX() + shooter.getWidth() , shooter.getY() + shooter.getHeight() * 0.9f ,
                Constants.BulletVelocityX, data.getBullet() , shooter.getDamage());
        bullet1.setVelocityY(bullet1.getVelocityX());
        Bullet bullet2 = (Bullet) bullet1.clone();
        bullet2.setVelocityX(bullet2.getVelocityX() * -1);
        bullet2.setX(shooter.getX());
        Bullet bullet3 = (Bullet) bullet2.clone();
        bullet3.setVelocityY(bullet3.getVelocityY() * -1);
        bullet3.setY(shooter.getY());
        Bullet bullet4 = (Bullet) bullet3.clone();
        bullet4.setX(shooter.getX() + shooter.getWidth());
        bullet4.setVelocityX(Constants.BulletVelocityX);
        for (int i = 0; i < data.getBulletNumber() / 4; i++) {
            game.getBullets().add((Bullet) bullet4.clone());
            game.getBullets().add((Bullet) bullet3.clone());
            game.getBullets().add((Bullet) bullet2.clone());
            game.getBullets().add((Bullet) bullet1.clone());
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
                            Bullet bullet = new Bullet(shooter.getX() +  shooter.getWidth() ,
                                    shooter.getY() + shooter.getHeight() * 0.8f
                                    , Constants.BulletVelocityX , 0);
                            bullet.setType(data.getBullet());
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

       if(target != null) game.getBullets().add(lobber_shoot(shooter , target));
    }
    private Bullet lobber_shoot(Plant shooter , Zombie target){
        Bullet bullet = new Bullet(shooter.getX() - 30, shooter.getY() + shooter.getHeight() / 2 , data.getBullet());
        if(data.getBullet() == BulletType.CORN){
            Random rand = new Random();
            boolean changeIncrease = App.getCurrentuser().getLevels().get(shooter.getType()) >= 2;
            int a = rand.nextInt(100); // probability = 20%
            if((a >= 1 && a <= 40) || (changeIncrease && a >= 41 && a <= 45)) bullet.setType(BulletType.BUTTER);
        }
        bullet.setVelocityX(Constants.LobberBulletVelocityX);
        float t = (target.getX() - shooter.getX()) / (bullet.getVelocityX() + target.getVelocityX());
        float dy =  target.getY() - shooter.getY();
        float Vy = dy / t + Constants.gravity * t / 2;
        bullet.setVelocityY(Vy);
        return  bullet;
    }



    @Override
    public ArrayList<Zombie> random(Plant plant, BaseGame game, int numbers) {
        ArrayList<Zombie> targets = Skill.super.random(plant, game, data.getRandomCount());
        if(data.getMood() == ShootingMood.LOBBER){
            for (Zombie z : targets) {
                game.getBullets().add(lobber_shoot(plant, z));
            }
        }
        return targets;
    }

    @Override
    public void all(Plant plant, BaseGame game) {
        if(data.getMood() == ShootingMood.LOBBER){
            for (Zombie z : game.getZombies()) {
                game.getBullets().add(lobber_shoot(plant, z));
            }

        }
        else if(data.getMood() == ShootingMood.AllLines){
            for (int i = 0; i < data.getBulletNumber() / 5; i++) {
                for (int j = 1; j <= 5; j++) {
                    Bullet bullet = new Bullet(plant.getX() ,
                            plant.getY() + i * Tile.getHeight() - Tile.getHeight() / 2, data.getBullet());
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
