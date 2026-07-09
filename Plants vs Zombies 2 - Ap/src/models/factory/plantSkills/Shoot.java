package models.factory.plantSkills;

import models.factory.plantSkills.skillDatas.ShootingData;
import models.gamePanes.Tile;
import models.games.BaseGame;
import models.npc.Bullet;
import models.npc.Plant;


public class Shoot implements Skill {
    ShootingData normalData;
    ShootingData PlantFoodData;

    @Override
    public void baseSkill(Plant shooter , BaseGame game) {

        try {
            shoot(shooter , normalData , game);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void plantFoodSkill(Plant shooter , BaseGame game) {
             try {
                 shoot(shooter , PlantFoodData , game);
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
        }
    }

    public void OneLineShoot(Plant shooter, ShootingData data , BaseGame game) throws CloneNotSupportedException {
        float x = shooter.getX() + shooter.getGraphic().getWidth();
        float y =  (shooter.getY() + shooter.getGraphic().getHeight() * 0.8f);
        data.getBullet().setX(x);
        data.getBullet().setY(y);
        for (int i = 0; i < data.getBulletNumber(); i++) {
            game.getBullets().add((Bullet) data.getBullet().clone());
        }// released the bullet
    }

    public void ThreeLineShoot(Plant shooter, ShootingData data , BaseGame game) throws CloneNotSupportedException {
        OneLineShoot(shooter, data, game);
        Bullet bulletup = (Bullet) data.getBullet().clone();
        Bullet bulletdown = (Bullet) data.getBullet().clone();
        bulletup.setY(bulletup.getY() + Tile.getHeight());
        bulletdown.setY(bulletdown.getY() - Tile.getHeight());
        game.getBullets().add(bulletup);
        game.getBullets().add(bulletdown);
    }

    private void front_back_shoot(Plant shooter , ShootingData data , BaseGame game) throws CloneNotSupportedException {
        OneLineShoot(shooter, data, game);
        Bullet bulletBack = (Bullet) data.getBullet().clone();
        bulletBack.setVelocityX(bulletBack.getVelocityX() * -1);
        Bullet bulletBack2 = (Bullet) bulletBack.clone();
        game.getBullets().add(bulletBack2);
        game.getBullets().add(bulletBack);
    }

    private void star_shoot(Plant shooter , ShootingData data ,  BaseGame game) throws CloneNotSupportedException {
        OneLineShoot(shooter, data, game);
        Bullet bulletStar2 = (Bullet) data.getBullet().clone();
        bulletStar2.setVelocityX(bulletStar2.getVelocityX() * -1);
        game.getBullets().add(bulletStar2);
        Bullet bulletStar3 = (Bullet) bulletStar2.clone();
        bulletStar3.setVelocityY(bulletStar3.getVelocityX());
        Bullet bulletStar4 = (Bullet) bulletStar3.clone();
        bulletStar4.setVelocityX(bulletStar4.getVelocityX() * -1);
        Bullet  bulletStar5 = (Bullet) bulletStar4.clone();
        bulletStar5.setVelocityY(bulletStar5.getVelocityY() * -1);
        bulletStar5.setVelocityX(0);
        game.getBullets().add(bulletStar5);
        game.getBullets().add(bulletStar2);
        game.getBullets().add(bulletStar3);
        game.getBullets().add(bulletStar4);
    }


    private void diagonal(Plant shooter , ShootingData  data , BaseGame game) throws CloneNotSupportedException {
        // we've given tho N/W bullet velocity
        Bullet bullet2 = (Bullet) data.getBullet().clone();
        Bullet bullet3 = (Bullet) data.getBullet().clone();
        Bullet bullet4 = (Bullet) data.getBullet().clone();
        bullet2.setVelocityX(bullet2.getVelocityX() * -1);
        bullet3.setVelocityY(bullet3.getVelocityY() * -1);
        bullet3.setVelocityX(bullet3.getVelocityX() * -1);
        bullet4.setVelocityY(bullet4.getVelocityY() * -1);
        game.getBullets().add(data.getBullet());
        game.getBullets().add(bullet2);
        game.getBullets().add(bullet3);
        game.getBullets().add(bullet4);
    }


}
