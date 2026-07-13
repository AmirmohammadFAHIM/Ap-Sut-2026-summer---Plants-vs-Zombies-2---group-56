package models.factory.builder;

import models.factory.plantSkills.Shoot;
import models.factory.plantSkills.skillDatas.ShootingData;
import models.factory.plantSkills.skillDatas.ShootingMood;
import models.npc.BulletType;
import models.npc.Plant;

public enum PlantsSkillAllocator {
    /// ---------SHOOTERS----------
    PEASHOOTER{
        @Override
        Plant allocateSkill(Plant plant) {
            ShootingData data = new ShootingData(BulletType.PEA , ShootingMood.OneLine , 1);
            plant.setBaseSkill(new Shoot(data));
            return super.allocateSkill(plant);
        }
    },
    REPEATER{
        @Override
        Plant allocateSkill(Plant plant) {
            ShootingData data = new ShootingData(BulletType.PEA , ShootingMood.OneLine , 2);
            plant.setBaseSkill(new Shoot(data));
            return super.allocateSkill(plant);
        }
    },
    THREEPEATER{
        @Override
        Plant allocateSkill(Plant plant) {
            ShootingData data = new ShootingData(BulletType.PEA , ShootingMood.ThreeLine , 3);
            plant.setBaseSkill(new Shoot(data));
            return super.allocateSkill(plant);
        }
    },
    SNOW_PEA{
        @Override
        Plant allocateSkill(Plant plant) {
            ShootingData data = new ShootingData(BulletType.PEA , ShootingMood.OneLine , 1);
            plant.setBaseSkill(new Shoot(data));
            return super.allocateSkill(plant);
        }
    },
    ROTOBAGA{
        @Override
        Plant allocateSkill(Plant plant) {
            ShootingData data = new ShootingData(BulletType.PEA , ShootingMood.Diagonal , 4);
            plant.setBaseSkill(new Shoot(data));
            return super.allocateSkill(plant);
        }
    },
    PEA_POD{

    },
    SPLIT_PEA,
    CITRON{
        @Override
        Plant allocateSkill(Plant plant) {
            ShootingData data = new ShootingData(BulletType.PLASMA , ShootingMood.OneLine , 2);
            plant.setBaseSkill(new Shoot(data));
            return super.allocateSkill(plant);
        }
    },
    BOWLING_BULB,
    STARFRUIT{
        @Override
        Plant allocateSkill(Plant plant) {
            ShootingData data = new ShootingData(BulletType.PEA , ShootingMood.Star , 5);
            plant.setBaseSkill(new Shoot(data));
            return super.allocateSkill(plant);
        }
    },
    GOO_PEASHOOTER,
    MEGA_GATLING_PEA,
    SEA_SHROOM,
    PUFF_SHROOM,
    /// -----------EXPLOSIVES--------------
    POTATO_MINE,
    PRIMAL_POTATO_MINE,
    CHERRY_BOMB,
    SQUASH,
    GRAPESHOT,
    JALAPENO,
    DOOM_SHROOM,
    TANGLE_KELP,
    ICEBERG_LETTUCE,
    HOT_POTATO,
    GRAVE_BUSTER,
    /// ---------LOBBERS------------------
    CABBAGE_PULT,
    KERNEL_PULT,
    MELON_PULT,
    WINTER_MELON,
    PEPPER_PULT,
    /// -----------STRIKE_THROUGH--------
    CACTUS,
    FUM_SHROOM,
    /// -----------MELEE---------------
    BONK_CHOY,
    PHAT_BEET,
    CHOMPER,
    WASSABI_WHIP,
    KIWIBEAST,
    /// ----------WALL_NUTS------------
    WALL_NUT,
    TALL_NUT,
    ENDURIAN,
    GARLIC,
    SWEET_POTATO,
    EXPLODE_O_NUT,
    PUMPKIN,
    SUN_BEAN,
    /// --------MODIFIERS-----------
    TORCHWOOD,
    HYPNO_SHROOM,
    IMITATER,
    LILY_PAD,
    /// --------HOMING--------------
    CAULIPOWER,
    ELECTRIC_BLUEBERRY,
    MAGNET_SHROOM;
    ///


    Plant allocateSkill(Plant plant){
        return plant;
    }


}
