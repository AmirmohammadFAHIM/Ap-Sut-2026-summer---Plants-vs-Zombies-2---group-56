package controllers.observer;

import models.entity.Zombie;

public class NewspaperObserver {

    private boolean isEnraged = false;

    public void onArmorBroken(Zombie zombie) {
        if (zombie == null || zombie.isDead()) return;
        if (isEnraged) return;

        isEnraged = true;
        float currentSpeed = zombie.getSpeed();
        zombie.setSpeed(currentSpeed * 4.0f);
    }

    public boolean isEnraged() {
        return isEnraged;
    }

    public void reset() {
        isEnraged = false;
    }
}