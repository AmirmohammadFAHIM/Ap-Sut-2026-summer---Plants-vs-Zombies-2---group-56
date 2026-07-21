package controllers.observer;

import models.npc.Zombie;

public class AllStarObserver {

    private boolean isSlowed = false;

    public void onPlantKilled(Zombie zombie) {
        if (zombie == null || zombie.isDead()) return;
        if (isSlowed) return;

        isSlowed = true;
        float currentSpeed = zombie.getSpeed();
        zombie.setSpeed(currentSpeed * 0.5f);
    }

    public boolean isSlowed() {
        return isSlowed;
    }

    public void reset() {
        isSlowed = false;
    }
}