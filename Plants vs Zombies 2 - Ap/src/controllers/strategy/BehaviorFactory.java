package controllers.strategy;

import models.npc.Zombie;

public class BehaviorFactory {

    public static BehaviorStrategy create(Zombie zombie) {
        String objClass = zombie.getObjClass();

        if ("ZombieDarkKingProps".equals(objClass)) {
            return new KingBehavior();
        }
        if ("ZombieIceAgeHunterProps".equals(objClass)) {
            return new HunterBehavior();
        }
        if ("ZombieTombRaiserProps".equals(objClass)) {
            return new TombRaiserBehavior();
        }
        if ("ZombieBeachFishermanProps".equals(objClass)) {
            return new FishermanBehavior();
        }
        if ("ZombieBeachOctopusProps".equals(objClass)) {
            return new OctopusBehavior();
        }
        if ("ZombiePianoProps".equals(objClass)) {
            return new PianoBehavior();
        }
        if ("ZombieRaProps".equals(objClass)) {
            return new RaBehavior();
        }
        if ("ZombieDarkWizardProps".equals(objClass)) {
            return new WizardBehavior();
        }
        if ("ZombieCamelDefault".equals(objClass) || "ZombieTurquoiseProps".equals(objClass)) {
            return new TurquoiseBehavior();
        }
        if ("ZombieModernAllStarProps".equals(objClass)) {
            return new SpeedChangeBehavior(0.5f);
        }
        if ("ZombieModernNewspaperProps".equals(objClass)) {
            return new SpeedChangeBehavior(4.0f);
        }

        return null;
    }
}