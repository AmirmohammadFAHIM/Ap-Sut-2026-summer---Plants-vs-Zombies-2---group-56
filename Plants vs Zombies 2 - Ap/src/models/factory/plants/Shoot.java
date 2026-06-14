package models.factory.plants;

import models.factory.plants.skillDatas.ShootingData;
import models.factory.plants.skillDatas.ShootingMood;
import models.games.BaseGame;
import models.npc.Plant;

public class Shoot implements Skill {
    ShootingData normalData;
    ShootingData PlantFoodData;

    @Override
    public void baseskill() {

    }

    public void OneLineShoot(Plant shooter, ShootingData data , BaseGame game) {
        int x = shooter.getX() + shooter.getGraphic().getWidth();
        int y = (int) (shooter.getY() + shooter.getGraphic().getHeight() * 0.8);
        normalData
    }
}
