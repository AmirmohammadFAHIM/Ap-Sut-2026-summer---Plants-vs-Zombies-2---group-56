package models;

import models.entity.Entity;

public class Constants {
    public final static float gravity = 300;
    public final static float LobberBulletVelocityX = 100;
    public final static float BulletVelocityX = 120;
    public final static float MagicalBulletVelocity = 120;
    public final static float Tall_WallNut_Height = 250;
    public final static int DeadLine_TileIndex = 3;
    public final static int LYP_Count = 5;
    public final static int Plants_count_in_a_game = 8;
    public final static int PlantWhatYouGet_StartingSunCount = 800;
    public final static int DisasterZombiesBaseCount = 3;
    public final static float TornadoVelocity = 200f;
    public final static float EndurianArmorDamage = 80f;
    public final static float WaterSurfaceChangeTime = 30f;
    public final static float SunDroppingVelocity = 70f;
    public final static float ChillTime = 5f;
    public final static float poisonBaseDamage = 10f;
    public final static float HomingVelocity = 250f;
    public final static int WallnutLimitLine = 3;
    public final static float BowlingWallnutVelocity = 200f;

    public static boolean overlap(Entity a ,Entity b){
        float cx  =a.getX() + a.getWidth()/2;
        boolean x = cx > b.getX() && cx <  b.getX() + b.getWidth();
        float cy  =a.getY() + a.getHeight()/2;
        boolean y = cy > b.getY() && cy <  b.getY() + b.getHeight();
        return x && y;
    }

}
