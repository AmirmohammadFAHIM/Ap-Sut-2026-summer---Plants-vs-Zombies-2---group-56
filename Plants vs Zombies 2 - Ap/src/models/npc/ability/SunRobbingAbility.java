package models.npc.ability;

import models.npc.Zombie;
import controllers.GameController;

public class SunRobbingAbility extends Ability {

    private int stolenSun = 0;
    private final int maxStolenSun;
    private final float stealRate;
    private float timer;

    public SunRobbingAbility(int maxStolenSun, float stealRate) {
        this.maxStolenSun = maxStolenSun;
        this.stealRate = stealRate;
        this.timer = 0;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, GameController controller) {
        if (stolenSun >= maxStolenSun) return;
        if (!zombie.isNearHouse()) return;

        timer += deltaTime;
        if (timer >= 1.0f) {
            timer = 0;
            int amount = (int) stealRate;
            if (stolenSun + amount > maxStolenSun) {
                amount = maxStolenSun - stolenSun;
            }
            stolenSun += amount;
            controller.removeSun(amount);
        }
    }

    public int getStolenSun() {
        return stolenSun;
    }

    public void releaseStolenSun(GameController controller) {
        if (stolenSun > 0) {
            int released = stolenSun / 2;
            controller.addSun(released);
            stolenSun = 0;
        }
    }

    @Override
    public void onDeath(Zombie zombie, GameController controller) {
        if (stolenSun > 0) {
            int released = stolenSun / 2;
            controller.addSun(released);
            stolenSun = 0;
        }
    }
}