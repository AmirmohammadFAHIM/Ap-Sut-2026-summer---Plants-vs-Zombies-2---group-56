package models.factory.plants.skillDatas;

import models.npc.Bullet;

public class ShootingData {
    private Bullet bullet;

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public ShootingMood getMood() {
        return mood;
    }

    public void setMood(ShootingMood mood) {
        this.mood = mood;
    }

    public int getBulletNumber() {
        return bulletNumber;
    }

    public void setBulletNumber(int bulletNumber) {
        this.bulletNumber = bulletNumber;
    }

    private ShootingMood mood;
    private int bulletNumber;

}
