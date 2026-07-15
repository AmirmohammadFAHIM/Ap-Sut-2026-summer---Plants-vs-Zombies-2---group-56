package models.factory.plantSkills.skillDatas;

import models.factory.plantSkills.Explosive;

public class ExplosionData {

    public enum ExplosionType{
        AOE,
        LINE,
        ALL,
        RANDOM,
        NEXT_TO,
        TOUCH,
    }
    public ExplosionType type;
    public int width;
    public int height;
    public int randomCount;

    public ExplosionData(int width, int height){
        type = ExplosionType.AOE;
        this.width = width;
        this.height = height;
    }

    public ExplosionData(ExplosionType type){
        this.type = type;
    }

    public ExplosionData(int randomCount){
        this.randomCount = randomCount;
    }


}
