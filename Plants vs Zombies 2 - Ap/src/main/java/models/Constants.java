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
    public final static float MoanerSpeed = 300f;

    public static boolean overlap(Entity entity1, Entity entity2) {
        if (entity1 == null || entity2 == null) {
            return false;
        }

        return (entity1.getX() < entity2.getX() + entity2.getWidth()) &&
                (entity1.getX() + entity1.getWidth() > entity2.getX()) &&
                (entity1.getY() < entity2.getY() + entity2.getHeight()) &&
                (entity1.getY() + entity1.getHeight() > entity2.getY());
    }

}
