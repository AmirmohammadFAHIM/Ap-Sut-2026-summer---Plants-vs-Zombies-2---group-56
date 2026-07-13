package models.factory.plantSkills.skillDatas;

import models.npc.BulletType;

public class ShootingData {
    private BulletType bullet;
    private ShootingMood mood;
    private int bulletNumber;
    private int randomCount;

    public ShootingData(BulletType type , ShootingMood mood ,  int bulletNumber) {
        this.bulletNumber = bulletNumber;
        this.mood = mood;
        bullet = type;
    }

    public ShootingData(){

    }
    public BulletType getBullet() {
        return bullet;
    }

    public void setBullet(BulletType bullet) {
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

    public int getRandomCount() {
        return randomCount;
    }

    public void setRandomCount(int randomCount) {
        this.randomCount = randomCount;
    }
}
