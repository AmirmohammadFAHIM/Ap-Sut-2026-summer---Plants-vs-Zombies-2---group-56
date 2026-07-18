package controllers.strategy;

import models.npc.Zombie;

public class RaBehavior implements BehaviorStrategy {

    private int stolenSun = 0;
    private boolean hasStolen = false;

    @Override
    public void execute(Zombie zombie, float deltaTime) {
        if (zombie.isNearHouse() && !hasStolen) {
            // steal sun - delegated to controller
            stolenSun += 50;
            hasStolen = true;
        }
    }

    public int getStolenSun() {
        return stolenSun;
    }

    public void releaseStolenSun() {
        // return half of stolen sun to player - delegated to controller
        stolenSun = 0;
        hasStolen = false;
    }
