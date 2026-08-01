package models.factory.plantSkills.skillDatas;

import models.entity.BulletType;

public class ShootingData {
    private BulletType bullet; /// type of your bullet
    private ShootingMood mood; /// how you wanna shoot
    private int bulletNumber; /// how many bullets you wanna shoot each time
    private int randomCount; /// random zombies to shoot
    public int range; /// range for mid - ranged plants

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
