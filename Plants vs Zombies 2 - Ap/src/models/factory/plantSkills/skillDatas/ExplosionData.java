package models.factory.plantSkills.skillDatas;

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



}
